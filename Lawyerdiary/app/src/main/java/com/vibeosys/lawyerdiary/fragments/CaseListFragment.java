package com.vibeosys.lawyerdiary.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vibeosys.lawyerdiary.Adapter.CaseAdapter;
import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.activities.CaseDetailsActivity;
import com.vibeosys.lawyerdiary.data.CaseData;
import com.vibeosys.lawyerdiary.database.LawyerContract;

import java.util.ArrayList;

/**
 * Created by akshay on 12-09-2016.
 */
public class CaseListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView listCases;
    private CaseAdapter caseAdapter;
    private int mPosition = ListView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";

    public CaseListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_case_list, container, false);
        listCases = (ListView) rootView.findViewById(R.id.listCases);
        loadCaseData();
        listCases.setOnItemClickListener(this);
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        return rootView;

    }

    private void loadCaseData() {
        ArrayList<CaseData> caseList = new ArrayList<>();
        Cursor caseCursor = getContext().getContentResolver().query(LawyerContract.Case.CONTENT_URI,
                new String[]{LawyerContract.Case._ID, LawyerContract.Case.CASE_NAME, LawyerContract.Case.DESCRIPTION
                }, null, null, null);

        if (caseCursor.getCount() > 0) {
            caseCursor.moveToFirst();
            do {
                long caseId = caseCursor.getLong(caseCursor.getColumnIndex(LawyerContract.Case._ID));
                String caseName = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.CASE_NAME));
                String caseDesc = caseCursor.getString(caseCursor.getColumnIndex(LawyerContract.Case.DESCRIPTION));
                caseList.add(new CaseData(caseId, caseName, caseDesc));
            }
            while (caseCursor.moveToNext());
        }
        caseAdapter = new CaseAdapter(caseList, getContext());
        listCases.setAdapter(caseAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CaseData caseData = (CaseData) caseAdapter.getItem(position);
        long caseId = caseData.get_Id();
        ((CallBackItem) getActivity())
                .onItemSelected(caseId);
        mPosition = position;
    }

    public interface CallBackItem {
        public void onItemSelected(long selectedCaseId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
