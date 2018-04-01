package com.example.billbooking.oms.billbooking.DashBoardFragments.BillsInProgress;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.billbooking.oms.billbooking.Customize.SharedPrefManager;
import com.example.billbooking.oms.billbooking.DashBoard;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopModel.PopModel;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopModel.PopModelImage;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosModel.PosModel;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosModel.PosModelImage;
import com.example.billbooking.oms.billbooking.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by OMS Laptop 3 on 06-01-2018.
 */

public class BillsProgressRecyclerAdapter extends RecyclerView.Adapter<BillsProgressRecyclerAdapter.RecyclerItemViewHolder> {
    private ArrayList<PopModel> myList;
    private int mLastPosition = 0;
    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private int count = 1;
    private String[] keyValues;
    private String[] keyValuesDisp;

    public BillsProgressRecyclerAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bills_card_items, parent, false);
        final RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        this.view = view;

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final int pos = recyclerView.getChildLayoutPosition(v);
                if (pos >= 0 && pos < getItemCount()) {

                    if (holder.purchaseType.getText().toString().equals("Sales")) {

                        if (holder.purchaseFormate.getText().toString().equals("Text")) {

                            Log.e("Varsha", "Sales Text");
                            ArrayList<PosModel> mylist = SharedPrefManager.getmInstance(context).getTextPosArray(holder.sharedKey.getText().toString());
                            String dispId = SharedPrefManager.getmInstance(context).getDisplayId(holder.sharedDisplayId.getText().toString());
                            ((DashBoard) context).gotoPOSText(mylist, holder.sharedKey.getText().toString(), dispId);

                        } else if (holder.purchaseFormate.getText().toString().equals("Image")) {
                            Log.e("Varsha", "Sales Text");
                            ArrayList<PosModelImage> mylist = SharedPrefManager.getmInstance(context).getImagePosArray(holder.sharedKey.getText().toString());
                            String dispId = SharedPrefManager.getmInstance(context).getDisplayId(holder.sharedDisplayId.getText().toString());
                            ((DashBoard) context).gotoPOSImage(mylist, holder.sharedKey.getText().toString(), dispId);

                        }

                    } else if (holder.purchaseType.getText().toString().equals("Purchases")) {

                        if (holder.purchaseFormate.getText().toString().equals("Text")) {

                            ArrayList<PopModel> mylist = SharedPrefManager.getmInstance(context).getTextPopArray(holder.sharedKey.getText().toString());
                            String dispId = SharedPrefManager.getmInstance(context).getDisplayId(holder.sharedDisplayId.getText().toString());
                            ((DashBoard) context).gotoPOPText(mylist, holder.sharedKey.getText().toString(), dispId);

                        } else if (holder.purchaseFormate.getText().toString().equals("Image")) {

                            ArrayList<PopModelImage> mylist = SharedPrefManager.getmInstance(context).getImagePopArray(holder.sharedKey.getText().toString());
                            String dispId = SharedPrefManager.getmInstance(context).getDisplayId(holder.sharedDisplayId.getText().toString());
                            ((DashBoard) context).gotoPOPImage(mylist, holder.sharedKey.getText().toString(), dispId);

                        }

                    }
                }
            }
        });


        return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {

        holder.invoice.setText("Invoice" + (SharedPrefManager.getmInstance(context).getTextPosArray() - count++));
        holder.sharedKey.setText(keyValues[position]);
        holder.sharedDisplayId.setText(keyValuesDisp[position]);

        if (keyValues[position].substring(0, 3).equals("Pos")) {
            holder.purchaseType.setText("Sales");
        } else if (keyValues[position].substring(0, 3).equals("Pop")) {
            holder.purchaseType.setText("Purchases");
        }

        if (keyValues[position].substring(3, 6).equals("Txt")) {
            holder.purchaseFormate.setText("Text");
        } else if (keyValues[position].substring(3, 6).equals("Img")) {
            holder.purchaseFormate.setText("Image");
        }

        mLastPosition = position;

    }

    @Override
    public int getItemCount() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("fcmsharedprefdemo", Context.MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPreferences.getAll();
        int length = allEntries.size();
        keyValues = new String[length];
        int i = 0;

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

            keyValues[i] = entry.getKey();
            i++;
            Log.e("map values varsha", entry.getKey() + ": " + entry.getValue().toString());
        }

        SharedPreferences sharedPre = context.getSharedPreferences("sharedprefdisplayid", Context.MODE_PRIVATE);

        Map<String, ?> allEntriesDisp = sharedPre.getAll();
        int lengthDisp = allEntriesDisp.size();
        keyValuesDisp = new String[lengthDisp];
        int j = 0;

        for (Map.Entry<String, ?> entry : allEntriesDisp.entrySet()) {

            keyValuesDisp[j] = entry.getKey();
            j++;
            Log.e("map varsha Display", entry.getKey() + ": " + entry.getValue().toString());
        }


        Log.e("Invoice Size", String.valueOf(length));
        if (length == 0) {
            ((DashBoard) context).gotoNoRecordFound();
        }


        return length;
    }

    public void notifyData(ArrayList<PopModel> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView invoice, purchaseType, purchaseFormate, sharedKey, sharedDisplayId;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);

            invoice = (TextView) parent.findViewById(R.id.temp_invoice);
            purchaseType = (TextView) parent.findViewById(R.id.purchases);
            purchaseFormate = (TextView) parent.findViewById(R.id.formate2);
            sharedKey = (TextView) parent.findViewById(R.id.shared_key);
            sharedDisplayId = (TextView) parent.findViewById(R.id.shared_display_id);


        }
    }

}
