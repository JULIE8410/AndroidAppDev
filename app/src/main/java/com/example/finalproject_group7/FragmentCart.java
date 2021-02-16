package com.example.finalproject_group7;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentCart extends Fragment implements OnItemClick{

    ListView listViewCart;
    View view;
    EditText txtSearchCart;
    List<Data> tempCart = new ArrayList<>();
    List<Data> listCart = new ArrayList<>();
    CustomAdapterCart adapterCart;
    Button btnClear;
    Button btnOrder;
    String cart;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SQLiteDatabase db;

    public FragmentCart(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cart, container, false);
        listViewCart = (ListView) view.findViewById(R.id.itemListCart);

        sharedPreferences = getActivity().getSharedPreferences("sf",0);
        editor = sharedPreferences.edit();

        db = getActivity().openOrCreateDatabase("TomatoDB_v3.db", Context.MODE_PRIVATE, null);


        txtSearchCart = view.findViewById(R.id.txtSearchCart);
        //Search
        txtSearchCart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = txtSearchCart.getText().toString();
                search(text);
            }
        });

        //Click Event for clearing cart
        btnClear = view.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove("cart");
                editor.commit();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.detach(FragmentCart.this).attach(FragmentCart.this).commit();
            }
        });

        //Click Event for Ordering with cart data
        btnOrder = view.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart = sharedPreferences.getString("cart","");
               if(cart.length()>0){

                   long result;
                   String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                   ContentValues val = new ContentValues();

                   val.put("orderDate", date);
                   val.put("orderList", cart);
                   val.put("orderStatus", "Preparing Food");
                   val.put("totalPrice", 0);
                   val.put("itemID", 0);

                   result = db.insert("ITEMORDER",null,val); //no field may be nullable, so no empty
                   Log.e("DB PROJECT", "Insert item order table  data");
                   if(result != -1){
                       Log.d("DE PROJECT", "rowid == " + result + " insert student id with "+cart);
                   }else{
                       Log.d("DE PROJECT", "Error inserting student rec with id " +cart);

                   }

//                   //clear and refresh the Fragment
                    editor.remove("cart");
                    editor.commit();

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.detach(FragmentCart.this).attach(FragmentCart.this).commit();
                  Toast.makeText(getContext(),"We got your order successfully!!!",Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(getContext(),"You have no item to order",Toast.LENGTH_SHORT).show();
               }
            }
        });


        //Preventing duplicate date
        tempCart.clear();
        listCart.clear();

        //item data from MainActivity(SharedPreference)
        ArrayList<Data> items = ((MainActivity)getActivity()).ReadItemsData();

        cart = sharedPreferences.getString("cart","");
        if(cart.length()>0){
            String[] carts = cart.split(",");
            for(int i =0; i < items.size(); i ++){
                for(int j = 0; j < carts.length; j++){
                    if(items.get(i).getItemID() == Integer.parseInt(carts[j])){
                        listCart.add(items.get(i));
                        System.out.println("Item.get(i) " +items.get(i).getItemName());
                    }
                }

            }

        }
        items.clear();
        tempCart.addAll(listCart);

        adapterCart = new CustomAdapterCart(getContext(), 0, tempCart, this);
        listViewCart.setAdapter(adapterCart);


        ((MainActivity)getActivity()).setListViewHeightBasedOnChildren(listViewCart);

        if(tempCart.isEmpty()){
            Toast.makeText(getContext(),"You have no item in Cart!", Toast.LENGTH_SHORT).show();

        }
        return view;
    }  //end of onCreate


    //for the searching feature
    private void search(String charText){
        tempCart.clear();
        charText = charText.toLowerCase();
        if(charText.length() == 0){
            tempCart.addAll(listCart);

        }else{
            for(int i = 0; i < listCart.size(); i++){
                if(listCart.get(i).getItemName().toLowerCase().contains(charText)){
                    tempCart.add(listCart.get(i));
                }

            }
        }
        adapterCart.notifyDataSetChanged();
    }

    //updating data for MainActivity
    @Override
    public void onClick(List<Data> data) {

        this.tempCart = data;
        tempCart.clear();
    }
}
