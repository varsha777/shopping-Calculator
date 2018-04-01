package com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopAdapters;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopModel.PopModelImage;
import com.example.billbooking.oms.billbooking.R;

import java.util.ArrayList;

/**
 * Created by OMS Laptop 3 on 06-01-2018.
 */

public class PopImageRecyclerAdapterShow extends RecyclerView.Adapter<PopImageRecyclerAdapterShow.RecyclerItemViewHolder> {
    private ArrayList<PopModelImage> myList;
    private int mLastPosition = 0;
    Context context;
    RecyclerView recyclerView;
    View view;
    private LinearLayout linearLayout;
    private RecyclerItemViewHolder holder;

    public PopImageRecyclerAdapterShow(ArrayList<PopModelImage> myList, Context context, RecyclerView res, LinearLayout linearLayout) {
        this.myList = myList;
        this.context = context;
        this.recyclerView = res;
        this.linearLayout = linearLayout;
    }

    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_card_items_show, parent, false);
        final RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);

        this.view = view;

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Long click to Delete Commodity", Toast.LENGTH_SHORT).show();
            }
        });


        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final int pos = recyclerView.getChildLayoutPosition(view);
                if (pos >= 0 && pos < getItemCount()) {

                    final PopModelImage deletedItem = myList.get(pos);
                    removeItem(holder.getAdapterPosition());
                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "removed from cart!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            restoreItem(deletedItem, pos);
                            notifyData(myList);
                        }
                    });

                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();

                }

                return true;
            }
        });


        return holder;
    }


    public void removeItem(int position) {
        myList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }


    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder holder, final int position) {
        Log.d("onBindViewHoler ", myList.size() + "");

        this.holder = holder;
        final PopModelImage item = myList.get(position);
        holder.tvcommodity.setText(myList.get(position).getCommodity());
        holder.tvquantity.setText(String.valueOf(myList.get(position).getQuantity()));
        holder.tvprice.setText(String.valueOf(myList.get(position).getPrice()));
        holder.tvtotal.setText(String.valueOf(myList.get(position).getTotal()));
        mLastPosition = position;

    }

    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }


    public void restoreItem(PopModelImage item, int position) {
        myList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }


    public void notifyData(ArrayList<PopModelImage> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvcommodity, tvquantity, tvprice, tvtotal;


        public RecyclerItemViewHolder(final View parent) {
            super(parent);


            tvcommodity = (TextView) parent.findViewById(R.id.commodity_text_show);
            tvquantity = (TextView) parent.findViewById(R.id.quantity_text_show);
            tvprice = (TextView) parent.findViewById(R.id.price_text_show);
            tvtotal = (TextView) parent.findViewById(R.id.total_text_show);
        }
    }


}
