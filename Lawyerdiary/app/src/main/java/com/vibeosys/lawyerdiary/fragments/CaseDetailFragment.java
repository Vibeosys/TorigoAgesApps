package com.vibeosys.lawyerdiary.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.database.LawyerContract;
import com.vibeosys.lawyerdiary.utils.DateUtils;

import java.util.Date;

/**
 * Created by akshay on 13-09-2016.
 */
public class CaseDetailFragment extends BaseFragment {

    private static final String TAG = CaseDetailFragment.class.getSimpleName();
    public static final String DETAIL_URI = "URI";
    private TextView txtCaseName, txtClientName, txtOppositionName,
            txtCourtLocation, txtStatus, txtDate, txtKeyPoints, txtFiles, txtDescription;
    private Uri mUri;
    private DateUtils dateUtils = new DateUtils();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_case_details, container, false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(CaseDetailFragment.DETAIL_URI);
            txtCaseName = (TextView) rootView.findViewById(R.id.txtCaseName);
            txtClientName = (TextView) rootView.findViewById(R.id.txtClientName);
            txtOppositionName = (TextView) rootView.findViewById(R.id.txtOppositionName);
            txtCourtLocation = (TextView) rootView.findViewById(R.id.txtCourtLocation);
            txtStatus = (TextView) rootView.findViewById(R.id.txtStatus);
            txtDate = (TextView) rootView.findViewById(R.id.txtDate);
            txtKeyPoints = (TextView) rootView.findViewById(R.id.txtKeyPoints);
            txtFiles = (TextView) rootView.findViewById(R.id.txtFiles);
            txtDescription = (TextView) rootView.findViewById(R.id.txtDescription);
            loadAndDisplayData();

            return rootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

    }

    private void loadAndDisplayData() {
        String[] projection = new String[]{LawyerContract.Case.TABLE_NAME + "." + LawyerContract.Case._ID,
                LawyerContract.Case.CASE_NAME, LawyerContract.Client.NAME, LawyerContract.Case.AGAINST,
                LawyerContract.Case.COURT_LOCATION, LawyerContract.Case.STATUS,
                LawyerContract.Case.CASE_DATE, LawyerContract.Case.KEY_POINTS,
                LawyerContract.Case.DESCRIPTION, LawyerContract.Case.CASE_TIME};
        Cursor caseCursor = null;
        if (mUri != null) {
            try {
                caseCursor = getContext().getContentResolver().query(mUri,
                        projection, null, null, null);
            } catch (SQLiteException e) {
                Log.e(TAG, "error in getting case details" + e.toString());
            }
            if (caseCursor.moveToFirst()) {
                String caseName = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.CASE_NAME));
                String clientName = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Client.NAME));
                String oppositionName = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.AGAINST));
                String courtLocation = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.COURT_LOCATION));
                int status = caseCursor.getInt(caseCursor.getColumnIndex(LawyerContract.Case.STATUS));
                long date = caseCursor.getLong(caseCursor.getColumnIndex(LawyerContract.Case.CASE_DATE));
                long time = caseCursor.getLong(caseCursor.getColumnIndex(LawyerContract.Case.CASE_TIME));
                String keyPoints = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.KEY_POINTS));
                // String files = clientCursor.getString(clientCursor.getColumnIndex(LawyerContract.Case.CASE_DATE));
                String desc = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.DESCRIPTION));
                String strStatus = status == 1 ? "Active" : "Inactive";
                getActivity().setTitle(caseName + " Details");
                txtCaseName.setText(caseName);
                txtClientName.setText(clientName);
                txtOppositionName.setText(oppositionName);
                txtCourtLocation.setText(courtLocation);
                txtStatus.setText(strStatus);
                txtDate.setText(dateUtils.getLocalDateInReadableFormat(new Date(date)) + " "
                        + dateUtils.getLocalTimeInReadableFormat(new Date(time)));
                txtKeyPoints.setText(keyPoints);
                txtDescription.setText(desc);
            }
        }
    }
}
