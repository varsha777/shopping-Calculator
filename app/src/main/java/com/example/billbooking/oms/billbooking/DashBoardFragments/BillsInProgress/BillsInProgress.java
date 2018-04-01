package com.example.billbooking.oms.billbooking.DashBoardFragments.BillsInProgress;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.billbooking.oms.billbooking.DashBoard;
import com.example.billbooking.oms.billbooking.R;
import com.example.billbooking.oms.billbooking.SingletonClass.SingletonSession;


public class BillsInProgress extends Fragment {


    private RecyclerView mRecyclerView;
    private BillsProgressRecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bills_in_progress, container, false);
        ((DashBoard) getActivity()).getSupportActionBar().setTitle("Progress Bills");
        initializeViews(view);

        return view;
    }

    private void initializeViews(View view) {

        SingletonSession.Instance().showProgress(getActivity(), "Gathering Data...");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_bills);
        mRecyclerAdapter = new BillsProgressRecyclerAdapter(getActivity(), mRecyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        SingletonSession.Instance().hideProgress();

    }

}
