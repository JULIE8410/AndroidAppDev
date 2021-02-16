package com.example.finalproject_group7;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.finalproject_group7.R.id;
import static com.example.finalproject_group7.R.layout;

public class FragmentShop extends Fragment {

    ListView listView;
    View view;
    EditText txtSearch;
    List<Data> tempShop = new ArrayList<>();
    List<Data> listShop = new ArrayList<>();
    CustomAdapter adapter;

    public FragmentShop(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layout.fragment_shop, container, false);
        listView = (ListView) view.findViewById(id.itemList);

//
        txtSearch = view.findViewById(id.txtSearch);
        //Search
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                String text = txtSearch.getText().toString();
                search(text);
            }
        });

        //Click Event for each Item (to FragmentItem)
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

           @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> list = new ArrayList<>();
                int itemID = tempShop.get(position).getItemID();

               String itemName = tempShop.get(position).getItemName();
               String itemDescription = tempShop.get(position).getItemDescription();
               double itemPrice = tempShop.get(position).getItemPrice();
               double itemDiscountedPrice = tempShop.get(position).getItemDiscountedPrice();
              String itemImage = tempShop.get(position).getItemImage();

               list.add(""+itemID);
               list.add(itemName);
              list.add(itemDescription);
              list.add(""+itemPrice);
                list.add(""+itemDiscountedPrice);
              list.add(itemImage);

             ((MainActivity)getActivity()).onFragmentChange(7,list);


           }
       });

//        item data from MainActivity(SharedPreference)
        ArrayList<Data> items = ((MainActivity)getActivity()).ReadItemsData();

        tempShop = items;
        listShop.clear();
        listShop.addAll(tempShop);
        adapter = new CustomAdapter(getContext(), 0, tempShop);
//
        listView.setAdapter(adapter);
        ((MainActivity)getActivity()).setListViewHeightBasedOnChildren(listView);
//

        return view;
    }


    private void search(String charText){
        tempShop.clear();
        charText = charText.toLowerCase();
        if(charText.length() == 0){
            tempShop.addAll(listShop);

        }else{
            for(int i = 0; i < listShop.size(); i++){
                if(listShop.get(i).getItemName().toLowerCase().contains(charText)){
                    tempShop.add(listShop.get(i));
                }

            }
        }
        adapter.notifyDataSetChanged();
    }




}
