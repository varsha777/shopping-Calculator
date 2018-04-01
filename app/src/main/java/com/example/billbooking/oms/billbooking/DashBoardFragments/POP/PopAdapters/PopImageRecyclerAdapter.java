package com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopModel.PopModelImage;
import com.example.billbooking.oms.billbooking.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by OMS Laptop 3 on 06-01-2018.
 */

public class PopImageRecyclerAdapter extends RecyclerView.Adapter<PopImageRecyclerAdapter.RecyclerItemViewHolder> {
    private ArrayList<PopModelImage> myList;
    private int mLastPosition = 0;
    Context context;
    View view;


    public PopImageRecyclerAdapter(ArrayList<PopModelImage> myList, Context context) {
        this.myList = myList;
        this.context = context;
    }

    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_card_items_image, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        this.view = view;
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHoler ", myList.size() + "");

        holder.tvcommodity.setText(myList.get(position).getCommodity());
        holder.tvquantity.setText(String.valueOf(myList.get(position).getQuantity()));
        holder.tvprice.setText(String.valueOf(myList.get(position).getPrice()));
        holder.tvtotal.setText(String.valueOf((myList.get(position).getQuantity()) * (myList.get(position).getPrice())));

        Picasso.with(context)
                .load(myList.get(position).getImagePath())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.ivCommodity);
        mLastPosition = position;


    }

    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }

    public void notifyData(ArrayList<PopModelImage> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvcommodity, tvquantity, tvprice, tvtotal;
        private ImageView ivCommodity;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);

            ivCommodity = (ImageView) parent.findViewById(R.id.image_commodity);
            tvcommodity = (TextView) parent.findViewById(R.id.commodity_name_image1);
            tvquantity = (TextView) parent.findViewById(R.id.quantity_image);
            tvprice = (TextView) parent.findViewById(R.id.price_image);
            tvtotal = (TextView) parent.findViewById(R.id.total_image);


        }
    }


    private void hideKeypad() {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
