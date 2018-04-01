package com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosAdapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.billbooking.oms.billbooking.DBModel.Commodity;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosModel.PosModel;
import com.example.billbooking.oms.billbooking.DatabaseFiles.DatabaseHelper;
import com.example.billbooking.oms.billbooking.R;

import java.util.ArrayList;

/**
 * Created by OMS Laptop 3 on 06-01-2018.
 */

public class PosRecyclerAdapter extends RecyclerView.Adapter<PosRecyclerAdapter.RecyclerItemViewHolder> {
    private ArrayList<PosModel> myList;
    private int mLastPosition = 0;
    private Context context;
    private View view;
    private InputMethodManager imm;
    private String[] commodityNames;
    private Double[] commodityPrices;
    int value = -1;

    public PosRecyclerAdapter(ArrayList<PosModel> myList, Context context) {
        this.myList = myList;
        this.context = context;
        DatabaseHelper dbh = new DatabaseHelper(context);
        SQLiteDatabase db = dbh.getReadableDatabase();
        ArrayList<Commodity> commodities = dbh.getAllCommodities();
        commodityNames = new String[commodities.size()];
        commodityPrices = new Double[commodities.size()];
        if (commodities.size() != 0) {
            for (int i = 0; i < commodities.size(); i++) {
                commodityNames[i] = commodities.get(i).getName();
                commodityPrices[i] = commodities.get(i).getAmount();
                Log.e("Item" + i, commodities.get(i).getName());
            }
        }

    }

    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pos_card_items, parent, false);
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
        holder.tvtotal.setText(String.valueOf(myList.get(position).getTotal()));
        mLastPosition = position;
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);


        holder.tvcommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.switcherCommodity.showNext();

                holder.edcommodity.requestFocus();
                imm.showSoftInput(holder.edcommodity, InputMethodManager.SHOW_IMPLICIT);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item, commodityNames);

                holder.edcommodity.setThreshold(1);
                holder.edcommodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        value = i;
                    }
                });
                Log.e("value", String.valueOf(value));
                holder.edcommodity.setAdapter(arrayAdapter);

                holder.edcommodity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                            if (holder.edcommodity.getText().toString().isEmpty()) {
                                holder.edcommodity.setError("Required Field", context.getResources().getDrawable(R.drawable.error));
                            } else {
                                Log.e("value in", String.valueOf(value));
                                myList.get(position).setCommodity(holder.edcommodity.getText().toString());
                                if (value == -1) {
                                    myList.get(position).setStatus(1);
                                } else {
                                    myList.get(position).setStatus(0);
                                    myList.get(position).setPrice(getPrice(holder.edcommodity.getText().toString()));
                                    myList.get(position).setTotal((myList.get(position).getPrice()) * (myList.get(position).getQuantity()));
                                    value = -1;
                                }
                                holder.switcherCommodity.showNext();
                                boolean r = holder.edcommodity.getLinksClickable();
                                notifyData(myList);
                                hideKeypad();
                            }
                        }
                        return false;
                    }
                });
            }
        });


        holder.tvquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.switcherQuantity.showNext();

                holder.edquantity.requestFocus();
                imm.showSoftInput(holder.edquantity, InputMethodManager.SHOW_IMPLICIT);

                holder.edquantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                            if (holder.edquantity.getText().toString().isEmpty()) {
                                holder.edquantity.setError("Required Field", context.getResources().getDrawable(R.drawable.error));
                            } else {


                                myList.get(position).setQuantity(Integer.parseInt(holder.edquantity.getText().toString()));
                                myList.get(position).setTotal((myList.get(position).getPrice()) * (myList.get(position).getQuantity()));
                                holder.switcherQuantity.showNext();
                                notifyData(myList);
                                hideKeypad();
                            }
                        }
                        return false;
                    }
                });
            }
        });

        holder.tvprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.switcherPrice.showNext();

                holder.edprice.requestFocus();
                imm.showSoftInput(holder.edprice, InputMethodManager.SHOW_IMPLICIT);

                holder.edprice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                            if (holder.edprice.getText().toString().isEmpty()) {
                                holder.edprice.setError("Required Field", context.getResources().getDrawable(R.drawable.error));
                            } else {

                                myList.get(position).setPrice(Integer.parseInt(holder.edprice.getText().toString()));
                                myList.get(position).setTotal((myList.get(position).getPrice()) * (myList.get(position).getQuantity()));
                                holder.switcherPrice.showNext();
                                notifyData(myList);
                                hideKeypad();
                            }
                        }
                        return false;
                    }
                });
            }
        });


    }

    private double getPrice(String item) {
        for (int i = 0; i < commodityNames.length; i++) {

            if (commodityNames[i].equals(item))
                return commodityPrices[i];

        }
        return 0.0;
    }

    @Override
    public int getItemCount() {
        return (null != myList ? myList.size() : 0);
    }

    public void notifyData(ArrayList<PosModel> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.myList = myList;
        notifyDataSetChanged();
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvcommodity, tvquantity, tvprice, tvtotal;
        private EditText edquantity, edprice, edtotal;
        AutoCompleteTextView edcommodity;


        ViewSwitcher switcherCommodity, switcherQuantity, switcherPrice, switcherTotal;

        public RecyclerItemViewHolder(final View parent) {
            super(parent);

            switcherCommodity = (ViewSwitcher) parent.findViewById(R.id.my_switcher_commodity);
            switcherQuantity = (ViewSwitcher) parent.findViewById(R.id.my_switcher_quantity);
            switcherPrice = (ViewSwitcher) parent.findViewById(R.id.my_switcher_price);
            switcherTotal = (ViewSwitcher) parent.findViewById(R.id.my_switcher_total);

            tvcommodity = (TextView) parent.findViewById(R.id.commodity_text);
            tvquantity = (TextView) parent.findViewById(R.id.quantity_text);
            tvprice = (TextView) parent.findViewById(R.id.price_text);
            tvtotal = (TextView) parent.findViewById(R.id.total_text);

            edcommodity = (AutoCompleteTextView) parent.findViewById(R.id.commodity_edit);
            edquantity = (EditText) parent.findViewById(R.id.quantity_edit);
            edprice = (EditText) parent.findViewById(R.id.price_edit);
            edtotal = (EditText) parent.findViewById(R.id.total_edit);

        }
    }


    private void hideKeypad() {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
