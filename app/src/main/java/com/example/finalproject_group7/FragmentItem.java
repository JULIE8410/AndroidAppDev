package com.example.finalproject_group7;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentItem extends Fragment {

    TextView txtNameDetail;
    TextView txtDescriptionDetail;
    TextView txtPriceDetail;
    TextView txtDiscountedPriceDetail;
    ImageView imgItemDetail;
    Button btnAddDetail;
    Button btnBackToShop;
    Button btnGoToCart;

    private List<Integer> imgPics = new ArrayList<>(Arrays.asList(R.drawable.pepperoni,R.drawable.meatlovers,
            R.drawable.chickenlovers, R.drawable.classic, R.drawable.feast, R.drawable.combo,
            R.drawable.italian, R.drawable.mediterranean, R.drawable.veggie, R.drawable.chickenpesto));


    public FragmentItem(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        super.onCreate(savedInstanceState);

        //item Data from FragmentShop and FragmentDiscount
        if (getArguments() != null) {
            imgItemDetail = view.findViewById(R.id.imgItemDetail);
            String image = getArguments().getString("itemImage");
            int index = getArguments().getInt("itemID");
            imgItemDetail.setImageResource(imgPics.get(index));

            txtNameDetail = view.findViewById(R.id.txtNameDetail);
            String name = getArguments().getString("itemName");
            txtNameDetail.setText(name);

            txtDescriptionDetail = view.findViewById(R.id.txtDescriptionDetail);
            String description = getArguments().getString("itemDescription");
            txtDescriptionDetail.setText(description);

            txtPriceDetail = view.findViewById(R.id.txtPriceDetail);
            double price = getArguments().getDouble("itemPrice");
            txtPriceDetail.setText("$"+ price);
            double discountedPercentage = getArguments().getDouble("itemDiscountedPrice");
            double discountedPrice = price-(price * (discountedPercentage /100));
            txtDiscountedPriceDetail = view.findViewById(R.id.txtDiscountedPriceDetail);

            txtDiscountedPriceDetail.setText("$"+ String.format("%.2f",discountedPrice));
        }

        //Click Event for adding cart
        btnAddDetail = view.findViewById(R.id.btnAddDetail);
        btnAddDetail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sf",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int itemID = getArguments().getInt("itemID"); //retrieved from data base (from data class)
                String txtCart = sharedPreferences.getString("cart",""); //user clicked this item
                String[] txtCartArray = txtCart.split(",");
                boolean contain = false;
                for(int i = 0; i < txtCartArray.length; i++){
                    if(txtCartArray[i].equals(""+itemID)){
                        contain = true;
                        Toast.makeText(getContext(),"This item is already in your cart", Toast.LENGTH_SHORT).show();
                    }
                }
                if(!contain){
                    if(txtCart.equals("")){
                        txtCart += itemID;
                    }else{
                        txtCart += "," + itemID;
                    }
                    Toast.makeText(getContext(),"Sucessfully added to your cart", Toast.LENGTH_SHORT).show();
                }

                editor.putString("cart", txtCart);
                editor.commit();

            }
        });

        //Click Event For back to FragmentShop
        btnBackToShop = view.findViewById(R.id.btnBackToShop);
        btnBackToShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onFragmentChange(1, null);
            }
        });

        //Click Event For go to FragmentCart
        btnGoToCart = view.findViewById(R.id.btnGoCart);
        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onFragmentChange(4, null);
            }
        });

        return view;
    }

    //To get data from other Fragment
    public static FragmentItem newInstance(int itemID, String itemName, String itemDescription,
                                                                      double itemPrice, double itemDiscountedPrice, String itemImage  ) {
        
        Bundle bundle = new Bundle();
        bundle.putInt("itemID",itemID);
        bundle.putString("itemName",itemName);
        bundle.putString("itemDescription",itemDescription);
        bundle.putDouble("itemPrice",itemPrice);
        bundle.putDouble("itemDiscountedPrice",itemDiscountedPrice);
        bundle.putString("itemImage",itemImage);
        FragmentItem fragment = new FragmentItem();
        fragment.setArguments(bundle);
        return fragment;
    }
}
