package com.vibeosys.paymybill.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vibeosys.paymybill.data.ServerSync;
import com.vibeosys.paymybill.data.Sync;
import com.vibeosys.paymybill.data.SyncDataDTO;
import com.vibeosys.paymybill.data.TableDataDTO;
import com.vibeosys.paymybill.data.TableJsonCollectionDTO;
import com.vibeosys.paymybill.data.Upload;
import com.vibeosys.paymybill.data.UploadUser;
import com.vibeosys.paymybill.database.DbRepository;
import com.vibeosys.paymybill.interfaces.BackgroundTaskCallback;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by anand on 09-01-2016.
 */
public class ServerSyncManager
        implements BackgroundTaskCallback {

    private DbRepository mDbRepository;
    private SessionManager mSessionManager;
    private Context mContext;
    private boolean mIsWorkInProgress;
    private OnDownloadReceived mOnDownloadReceived;
    private OnStringResultReceived mOnStringResultReceived;
    private OnStringErrorReceived mErrorReceived;
    private String TAG = ServerSyncManager.class.getSimpleName();
    private OnNotifyUser mNotifyUser;

    public ServerSyncManager() {

    }

    public ServerSyncManager(@NonNull Context context, @NonNull SessionManager sessionManager) {
        mContext = context;
        mSessionManager = sessionManager;
        mDbRepository = new DbRepository(mContext, mSessionManager);
    }

    public void syncDataWithServer(final boolean aShowProgressDlg) {
        Log.d("BaseActivity", "IN Base");
        String downloadUrl = mSessionManager.getDownloadUrl(mSessionManager.getUserId(), mSessionManager.getUserRestaurantId());
        String uploadJson = getUploadSyncJson();
        SyncDataDTO syncData = new SyncDataDTO();
        syncData.setDownloadUrl(downloadUrl);
        syncData.setUploadUrl(mSessionManager.getUploadUrl());
        syncData.setUploadJson(uploadJson);
        mIsWorkInProgress = true;

        new BackgroundTask(aShowProgressDlg).execute(syncData);
    }

    public void uploadDataToServer(TableDataDTO... params) {
        if (params == null || params.length <= 0) {
            Log.e("UploadNoData", "No data for upload was given by the respective method");
            return;
        }

        final ProgressDialog progress = new ProgressDialog(mContext);
        if (mSessionManager.getUserId() == 0 || mSessionManager.getUserName() == null
                || mSessionManager.getUserName().isEmpty()) {
            Log.e("UserNotAuth", "User is not authenticated before upload");
            return;
        }
        String uploadJson = prepareUploadJsonFromData(params);
        String uploadURL = mSessionManager.getUploadUrl();
        // Log.i(TAG, "##" + uploadJson);
        uploadJsonToServer(uploadJson, uploadURL, progress);
    }

    /*public boolean sendOtpToUser(String emailId) {
        final ProgressDialog progress = new ProgressDialog(mContext);
        UploadUserOtp uploadUserOtp = new UploadUserOtp(mSessionManager.getUserId(), emailId, null, null);
        String uploadJson = uploadUserOtp.serializeString();
        String sendOtpUrl = mSessionManager.getSendOtpUrl();
        uploadJsonToServer(uploadJson, sendOtpUrl, progress);
        return true;
    }*/

    public boolean isDownloadInProgress() {
        return mIsWorkInProgress;
    }

    public void setOnDownloadReceived(OnDownloadReceived onDownloadReceived) {
        mOnDownloadReceived = onDownloadReceived;
    }

    public void setOnStringResultReceived(OnStringResultReceived stringResultReceived) {
        mOnStringResultReceived = stringResultReceived;
    }

    public void setOnStringErrorReceived(OnStringErrorReceived stringErrorReceived) {
        mErrorReceived = stringErrorReceived;
    }

    private String prepareUploadJsonFromData(TableDataDTO... params) {

        Upload uploadToServer = new Upload();
        /*uploadToServer.setUser(new UploadUser(
                mSessionManager.getUserId(),
                mSessionManager.getUserRestaurantId(),
                mDbRepository.getPassword(mSessionManager.getUserId()), mSessionManager.getImei(), mSessionManager.getMac()));
       */ uploadToServer.setData(Arrays.asList(params));
        String uploadJson = uploadToServer.serializeString();
        return uploadJson;
    }

    private void uploadJsonToServer(String uploadJson, String uploadUrl, final ProgressDialog progress) {
        RequestQueue vollyRequest = Volley.newRequestQueue(mContext);

        JsonObjectRequest uploadRequest = new JsonObjectRequest(Request.Method.POST,
                uploadUrl, uploadJson, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Upload Response", "" + response.toString());

                if (mOnStringResultReceived != null)
                    mOnStringResultReceived.onStingResultReceived(response);
                if (progress != null)
                    progress.dismiss();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progress != null)
                    progress.dismiss();
                if (mErrorReceived != null)
                    mErrorReceived.onStingErrorReceived(error);
                // Log.i(TAG, "##" + error.toString());
            }
        });
        uploadRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        vollyRequest.add(uploadRequest);
    }

    protected String getUploadSyncJson() {
        Upload uploadToServer = new Upload();
        /*uploadToServer.setUser(new UploadUser(
                SessionManager.Instance().getUserId(), SessionManager.Instance().getUserRestaurantId()
                , mDbRepository.getPassword(mSessionManager.getUserId()), mSessionManager.getImei(), mSessionManager.getMac()));*/
        List<Sync> syncRecordsInDb = mDbRepository.getPendingSyncRecords();
        ArrayList<TableDataDTO> tableDataList = new ArrayList<>();

        for (Sync syncEntry : syncRecordsInDb) {
            tableDataList.add(new TableDataDTO(syncEntry.getTableName(), syncEntry.getJsonSync()));
        }
        uploadToServer.setData(tableDataList);
        mDbRepository.clearSyncData();
        if (tableDataList.isEmpty()) {
            return null;
        } else {
            String uploadData = new Gson().toJson(uploadToServer);
            return uploadData;
        }
    }

    @Override
    public void onResultReceived(String downloadedJson) {
        ServerSync downloadData = null;
        try {
            downloadData = new Gson().fromJson(downloadedJson, ServerSync.class);
        } catch (JsonSyntaxException e) {
            Log.e("JsonSyntaxDwnld", "## ServerSync object could not be deserialized, nothing to download" + e.toString());
        }


        if (downloadData == null)
            return;

        if (downloadData.getTableData() == null) {
            return;
        }

        Log.d("TableDataDTO", "##" + downloadData.toString());
        Map<String, Integer> downloadResults = updateDownloadedData(downloadData);

        Hashtable<String, TableJsonCollectionDTO> theTableData = new Hashtable<>();
        for (TableDataDTO tableData : downloadData.getTableData()) {
            String theTableName = tableData.getTableName();
            String theTableValue = tableData.getTableData().replaceAll("\\\\", "");

            TableJsonCollectionDTO tableJsonCollectionDTO;
            if (!theTableData.containsKey(theTableName)) {
                tableJsonCollectionDTO = new TableJsonCollectionDTO();
                theTableData.put(theTableName, tableJsonCollectionDTO);
            }

            if (tableData.getOperation().compareToIgnoreCase("insert") == 0)
                theTableData.get(theTableName).getInsertJsonList().add(theTableValue);

            if (tableData.getOperation().compareToIgnoreCase("update") == 0)
                theTableData.get(theTableName).getUpdateJsonList().add(theTableValue);

            if (tableData.getOperation().compareToIgnoreCase("delete") == 0)
                theTableData.get(theTableName).getDeleteJsonList().add(theTableValue);
        }

        DbOperations dbOperations = new DbOperations(mDbRepository);

        /*if (theTableData.containsKey(DbTableNameConstants.BILL)) {
            TableJsonCollectionDTO tableValue = theTableData.get(DbTableNameConstants.BILL);
            ArrayList<String> jsonInsertList = tableValue.getInsertJsonList();
            downloadResults.put(DbTableNameConstants.BILL, jsonInsertList.size());
            dbOperations.addOrUpdateBills(jsonInsertList, tableValue.getUpdateJsonList());
            Log.d("TableDataDTO", "##" + DbTableNameConstants.BILL);
        }*/

        if (mOnDownloadReceived != null)
            mOnDownloadReceived.onDownloadResultReceived(downloadResults);

        mIsWorkInProgress = false;
    }


    @NonNull
    private Map<String, Integer> updateDownloadedData(ServerSync downloadData) {
        HashMap<String, Integer> downloadResults = new HashMap<>();
        Hashtable<String, TableJsonCollectionDTO> theTableData = new Hashtable<>();

        for (TableDataDTO tableData : downloadData.getTableData()) {
            String theTableName = tableData.getTableName();
            Log.d(TAG, "##" + tableData.toString());
            String theTableValue = tableData.getTableData().replaceAll("\\\\", "");

            TableJsonCollectionDTO tableJsonCollectionDTO;
            if (!theTableData.containsKey(theTableName)) {
                tableJsonCollectionDTO = new TableJsonCollectionDTO();
                theTableData.put(theTableName, tableJsonCollectionDTO);
            }

            if (tableData.getOperation().compareToIgnoreCase("insert") == 0)
                theTableData.get(theTableName).getInsertJsonList().add(theTableValue);

            if (tableData.getOperation().compareToIgnoreCase("update") == 0)
                theTableData.get(theTableName).getUpdateJsonList().add(theTableValue);
        }

        //DbOperations dbOperations = new DbOperations(mDbRepository);
        return downloadResults;
    }

    class BackgroundTask extends android.os.AsyncTask<SyncDataDTO, Void, String> {

        private boolean mShowProgressDlg;
        private ProgressDialog mProgressDialog = new ProgressDialog(mContext);

        public BackgroundTask(boolean aShowProgressDlg) {
            mShowProgressDlg = aShowProgressDlg;
        }

        @Override
        protected void onPostExecute(String downloadedJson) {
            super.onPostExecute(downloadedJson);
            if (mShowProgressDlg && mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (downloadedJson != null) {
                onResultReceived(downloadedJson);
            }

        }

        @Override
        protected String doInBackground(SyncDataDTO... params) {

            String downloadedJson = null;

            if (params == null || params.length <= 0)
                return downloadedJson;

            try {
                SyncDataDTO syncDataParam = params[0];
                String downloadUrl = syncDataParam.getDownloadUrl();
                URL url = new URL(downloadUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String dataLine = null;

                StringBuffer dataBuffer = new StringBuffer();
                while ((dataLine = br.readLine()) != null) {
                    dataBuffer.append(dataLine);
                }

                uploadJsonToServer(syncDataParam.getUploadJson(), syncDataParam.getUploadUrl(), null);
                downloadedJson = dataBuffer.toString();

            } catch (Exception e) {
                Log.e("DownloadUploadErr", "Error in background thread" + e.toString());
            }
            return downloadedJson;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*if (mShowProgressDlg) {
                mProgressDialog.show();
            }*/
        }
    }

    public interface OnDownloadReceived {
        void onDownloadResultReceived(@NonNull Map<String, Integer> results);
    }

    public interface OnStringResultReceived {
        void onStingResultReceived(@NonNull JSONObject data);
    }

    public interface OnStringErrorReceived {
        void onStingErrorReceived(@NonNull VolleyError error);
    }

    public interface OnNotifyUser {
        void onNotificationReceived(@NonNull String message);
    }

    public void setOnNotifyUser(OnNotifyUser notifyUser) {
        mNotifyUser = notifyUser;
    }
    }
