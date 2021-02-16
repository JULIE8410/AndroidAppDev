package com.example.finalproject_group7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//Order Class for order data


public class FragmentOrder extends Fragment {
    View view;
    List<Order> tempOrder = new ArrayList<>();
    List<Order> listOrder = new ArrayList<>();

    //count for Click Event (next and before)
    int count = 0;
    DecimalFormat df;

    Button btnBefore;
    Button btnNext;
    RelativeLayout layoutOrderLocation;
    RelativeLayout layoutOrderContact;

    TextView txtOrderDate;
    TextView txtOrderStatus;
    TextView txtOrderList;
    TextView txtTotalCost;

    SQLiteDatabase db;

    public FragmentOrder(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);

        db = getActivity().openOrCreateDatabase("TomatoDB_v3.db", Context.MODE_PRIVATE, null);

        btnNext = view.findViewById(R.id.btnOrderNext);
        btnBefore = view.findViewById(R.id.btnOrderBefore);

        txtOrderList=view.findViewById(R.id.txtOrderList);
        txtOrderDate = view.findViewById(R.id.txtOrderDate);
        txtOrderStatus = view.findViewById(R.id.txtOrderStatus);
        txtTotalCost=view.findViewById(R.id.txtTotalCost);
        df=new DecimalFormat("$#.##");

        listOrder.clear();
        tempOrder.clear();

        String queryStr = "SELECT * FROM ITEMORDER;";
        try{
            Cursor cursor = db.rawQuery(queryStr, null);
            if(cursor != null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    String orderDate = cursor.getString(cursor.getColumnIndex("orderDate"));
                    String orderList = cursor.getString(cursor.getColumnIndex("orderList"));
                    String orderStatus = cursor.getString(cursor.getColumnIndex("orderStatus"));
                    int orderID=cursor.getInt(cursor.getColumnIndex("itemID"));

                    listOrder.add(new Order(orderDate,orderStatus,orderList,orderID));
                    cursor.moveToNext();
                }
            }
        }catch(Exception ex){
            Log.d("DB PROJECT", "loadIntoListView - FragmentOrder" + ex.getMessage());
        }



        tempOrder.addAll(listOrder);

        if(tempOrder.isEmpty()){
            //Default values
            txtOrderDate.setText("");
            txtOrderStatus.setText("Order Status :\nYou have no order\nIf you have any problems, please contact us :)");
            txtOrderList.setText("");
            txtTotalCost.setText("");
        }else{

            Order order = tempOrder.get(count);
            ArrayList<Data> items = ((MainActivity) getActivity()).ReadItemsData();
            String[] orderListString = order.getOrderList().split(",");
            String txtOrderListTxt = " ";
            Double totalPrice=0.0;

            for (int i = 0; i < items.size(); i++) {

                for (int j = 0; j < orderListString.length; j++) {

                    if (items.get(i).getItemID() == Integer.parseInt(orderListString[j])) {

                        txtOrderListTxt += items.get(i).getItemName() + ", ";
                        totalPrice=totalPrice+items.get(i).getItemPrice();

                    }
                }
            }
            txtOrderListTxt = txtOrderListTxt.substring(0, txtOrderListTxt.length()-2);
            items.clear();
            txtOrderDate.setText("Order Date : "  + order.getOrderDate());
            txtOrderStatus.setText("Order Status : " + order.getOrderStatus());
            txtOrderList.setText("Order List : " + txtOrderListTxt);
            txtTotalCost.setText("Total Price : " + df.format(totalPrice));
            updateData(totalPrice,order.getOrderID());
        }


        //Click Event for moving next
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;

                if(count == listOrder.size()){
                    count = listOrder.size() - 1;
                    Toast.makeText(getContext(),"You have no more Order",
                            Toast.LENGTH_SHORT).show();
                }

                if(count < listOrder.size()){

                    Order order = tempOrder.get(count);
                    ArrayList<Data> items = ((MainActivity) getActivity()).ReadItemsData();
                    String[] orderListString = order.getOrderList().split(", ");
                    String txtOrderListTxt = " ";
                    Double totalPrice=0.0;

                    for (int i = 0; i < items.size(); i++) {

                        for (int j = 0; j < orderListString.length; j++) {

                            if (items.get(i).getItemID() == Integer.parseInt(orderListString[j])) {

                                txtOrderListTxt += items.get(i).getItemName() + ", ";
                                totalPrice=totalPrice+items.get(i).getItemPrice();

                            }
                        }
                    }
                    txtOrderListTxt = txtOrderListTxt.substring(0, txtOrderListTxt.length()-2);
                    items.clear();
                    txtOrderDate.setText("Order Date : " + order.getOrderDate());
                    txtOrderStatus.setText("Order Status : " + order.getOrderStatus());
                    txtOrderList.setText("Order List : " + txtOrderListTxt);
                    txtTotalCost.setText("Total Price : " + df.format(totalPrice));
                    updateData(totalPrice,order.getOrderID());

                }

            }
        });

        //Click Event for moving before
        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;

                if(count < 0){
                    count = 0;
                    Toast.makeText(getContext(),"This is the most recent order",
                            Toast.LENGTH_SHORT).show();
                }

                if(count < listOrder.size()){

                    Order order = tempOrder.get(count);
                    ArrayList<Data> items = ((MainActivity) getActivity()).ReadItemsData();
                    String[] orderListString = order.getOrderList().split(",");
                    String txtOrderListTxt = " ";
                    Double totalPrice=0.0;

                    for (int i = 0; i < items.size(); i++) {

                        for (int j = 0; j < orderListString.length; j++) {

                            if (items.get(i).getItemID() == Integer.parseInt(orderListString[j])) {

                                txtOrderListTxt += items.get(i).getItemName() + ", ";
                                totalPrice=totalPrice+items.get(i).getItemPrice();

                            }
                        }
                    }
                    txtOrderListTxt = txtOrderListTxt.substring(0, txtOrderListTxt.length()-2);
                    items.clear();
                    txtOrderDate.setText("Order ID/Date : " + order.getOrderID() + "/" + order.getOrderDate());
                    txtOrderStatus.setText("Order Status : " + order.getOrderStatus());
                    txtOrderList.setText("Order List : " + txtOrderListTxt);
                    txtTotalCost.setText("Total Price : " + df.format(totalPrice));
                    updateData(totalPrice,order.getOrderID());

                }


            }
        });

        //Click Event for location and contact
        layoutOrderLocation = view.findViewById(R.id.layoutOrderLocation);
        layoutOrderContact = view.findViewById(R.id.layoutOrderContact);

        layoutOrderLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onFragmentChange(5,null);
            }
        });
        layoutOrderContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).contactDialog();
            }
        });

        return view;
    } //end of onCreate

    public  void updateData(Double price,int ID)
    {
        ID++;
        BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
        String updateItemOrderCmd = "UPDATE ITEMORDER SET totalPrice= " + bd.doubleValue() + " WHERE orderID= " + ID +";";
        db.execSQL(updateItemOrderCmd);

        Log.e("DB PROJECT", "Insert ITEMORDER data");
    }

    } //end of FragmentOrder class



