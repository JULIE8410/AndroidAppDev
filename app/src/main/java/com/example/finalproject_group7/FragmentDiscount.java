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

public class FragmentDiscount extends Fragment {

    ListView listViewDiscounted;
    View view;
    EditText txtSearchDiscount;
    List<Data> tempDiscount = new ArrayList<>();
    List<Data> listDiscount = new ArrayList<>();
    CustomAdapter adapterDiscount;

    public FragmentDiscount(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layout.fragment_discount,container,false);

        listViewDiscounted = view.findViewById(id.itemListDiscount);
        txtSearchDiscount = view.findViewById(id.txtSearchDiscount);

        //Search
        txtSearchDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = txtSearchDiscount.getText().toString();
                search(text);
            }
        });

        //Click Event for each Item
        listViewDiscounted.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> list = new ArrayList<>();

                int itemID = tempDiscount.get(position).getItemID();

                String itemName = tempDiscount.get(position).getItemName();
                String itemDescription = tempDiscount.get(position).getItemDescription();
                double itemPrice = tempDiscount.get(position).getItemPrice();
                double itemDiscountedPrice = tempDiscount.get(position).getItemDiscountedPrice();
                String itemImage = tempDiscount.get(position).getItemImage();

                list.add(""+itemID);
                list.add(itemName);
                list.add(itemDescription);
                list.add(""+itemPrice);
                list.add(""+itemDiscountedPrice);
                list.add(itemImage);

                //pass the data to FragmentItem(Fragment->MainActivity->Fragment)
                ((MainActivity)getActivity()).onFragmentChange(7,list);


            }
        });

        tempDiscount.clear();
        listDiscount.clear();

        ArrayList<Data> items = ((MainActivity)getActivity()).ReadItemsData();
        System.out.println("Item size : " + items.size());
        
        listDiscount = items;

        System.out.println("temp Discount size 1 : " + tempDiscount.size());
        System.out.println("list Discount size 1 : " + listDiscount.size());

        for(int i =0; i < items.size(); i++){
            if(items.get(i).getItemDiscountedPrice() > 0){
                tempDiscount.add(items.get(i));

            }

        }
        //preventing Duplicate
        listDiscount.clear();
        listDiscount.addAll(tempDiscount);
        adapterDiscount = new CustomAdapter(getContext(), 0, tempDiscount);

        listViewDiscounted.setAdapter(adapterDiscount);
        ((MainActivity)getActivity()).setListViewHeightBasedOnChildren(listViewDiscounted);

        return view;
    }

    private void search(String charText){
        tempDiscount.clear();
        charText = charText.toLowerCase();
        if(charText.length() == 0){
            tempDiscount.addAll(listDiscount);

        }else{
            for(int i = 0; i < listDiscount.size(); i++){
                if(listDiscount.get(i).getItemName().toLowerCase().contains(charText)){
                    tempDiscount.add(listDiscount.get(i));
                }
            }
        }
        adapterDiscount.notifyDataSetChanged();
    }

}
