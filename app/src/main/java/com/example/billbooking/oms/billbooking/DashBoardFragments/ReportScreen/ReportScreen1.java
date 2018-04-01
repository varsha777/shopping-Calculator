package com.example.billbooking.oms.billbooking.DashBoardFragments.ReportScreen;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.billbooking.oms.billbooking.DBModel.Commodity;
import com.example.billbooking.oms.billbooking.DBModel.ReportGenerate;
import com.example.billbooking.oms.billbooking.DatabaseFiles.DatabaseHelper;
import com.example.billbooking.oms.billbooking.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;


public class ReportScreen1 extends Fragment {

    BarChart barChartSales, barChartPurchases;
    DatabaseHelper dbh = new DatabaseHelper(getActivity());
    SQLiteDatabase db = null;
    TextView salesTotal, purchasesTotal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_screen1, container, false);
        barChartSales = (BarChart) view.findViewById(R.id.sales_barchart);
        barChartPurchases = (BarChart) view.findViewById(R.id.purchase_barchart);

        salesTotal = (TextView) view.findViewById(R.id.sales_total);
        purchasesTotal = (TextView) view.findViewById(R.id.purchses_total);

        dbh = new DatabaseHelper(getActivity());
        ArrayList<ReportGenerate> reports = dbh.getAllReports();

        if (reports.size() != 0) {
            salesTotal.setText("Sales:: " + String.valueOf(reports.get(0).getSaleTotal()));
            purchasesTotal.setText("Purchases:: " + reports.get(0).getPurchaseTotal());
            Log.e("Sales Total", Double.toString(reports.get(0).getSaleTotal()));

            ArrayList<BarEntry> salesValues = new ArrayList<>();
            salesValues.add(new BarEntry((float) reports.get(0).getSaleTop1Value(), 0));
            salesValues.add(new BarEntry((float) reports.get(0).getSaleTop2Value(), 1));
            salesValues.add(new BarEntry((float) reports.get(0).getSaleTop3Value(), 2));
            salesValues.add(new BarEntry((float) reports.get(0).getSaleTop4Value(), 3));

            ArrayList<BarEntry> purchaseValues = new ArrayList<>();
            purchaseValues.add(new BarEntry((float) reports.get(0).getPurchaseTop1Value(), 0));
            purchaseValues.add(new BarEntry((float) reports.get(0).getPurchaseTop2Value(), 1));
            purchaseValues.add(new BarEntry((float) reports.get(0).getPurchaseTop3Value(), 2));
            purchaseValues.add(new BarEntry((float) reports.get(0).getPurchaseTop4Value(), 3));

            BarDataSet bardataset = new BarDataSet(salesValues, "Cells");
            BarDataSet bardataset2 = new BarDataSet(purchaseValues, "Cells");

            //substring(0, 8) + "..."

            ArrayList<String> sales = new ArrayList<String>();
            sales.add(reports.get(0).getSaleTop1Name().length() > 10 ? reports.get(0).getSaleTop1Name().substring(0, 8) + "..." : reports.get(0).getSaleTop1Name());
            sales.add(reports.get(0).getSaleTop2Name().length() > 10 ? reports.get(0).getSaleTop2Name().substring(0, 8) + "..." : reports.get(0).getSaleTop2Name());
            sales.add(reports.get(0).getSaleTop3Name().length() > 10 ? reports.get(0).getSaleTop3Name().substring(0, 8) + "..." : reports.get(0).getSaleTop3Name());
            sales.add(reports.get(0).getSaleTop4Name().length() > 10 ? reports.get(0).getSaleTop4Name().substring(0, 8) + "..." : reports.get(0).getSaleTop4Name());

            ArrayList<String> purchases = new ArrayList<String>();
            purchases.add(reports.get(0).getPurchaseTop1Name().length() > 10 ? reports.get(0).getPurchaseTop1Name().substring(0, 8) + "..." : reports.get(0).getPurchaseTop1Name());
            purchases.add(reports.get(0).getPurchaseTop2Name().length() > 10 ? reports.get(0).getPurchaseTop2Name().substring(0, 8) + "..." : reports.get(0).getPurchaseTop2Name());
            purchases.add(reports.get(0).getPurchaseTop3Name().length() > 10 ? reports.get(0).getPurchaseTop3Name().substring(0, 8) + "..." : reports.get(0).getPurchaseTop3Name());
            purchases.add(reports.get(0).getPurchaseTop4Name().length() > 10 ? reports.get(0).getPurchaseTop4Name().substring(0, 8) + "..." : reports.get(0).getPurchaseTop4Name());

            BarData salesData = new BarData(sales, bardataset);
            barChartSales.setData(salesData); // set the data and list of lables into chart

            BarData purchaseData = new BarData(purchases, bardataset2);
            barChartPurchases.setData(purchaseData);

            barChartSales.setDescription("Over All Sales");  // set the description
            barChartPurchases.setDescription("Over All Purchases");  // set the description

            bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

            barChartSales.animateY(5000);
            barChartPurchases.animateY(5000);

        }

        return view;
    }

}
