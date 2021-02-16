package com.example.finalproject_group7;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Custom Adapter Class for FragmentCart
public class CustomAdapterCart extends ArrayAdapter<Data> {

    private LayoutInflater layoutInflater;
    private List<Data> objectCart = new ArrayList<>();

    private List<Integer> imgPics = new ArrayList<>(Arrays.asList(R.drawable.pepperoni,R.drawable.meatlovers,
            R.drawable.chickenlovers, R.drawable.classic, R.drawable.feast, R.drawable.combo,
            R.drawable.italian, R.drawable.mediterranean, R.drawable.veggie, R.drawable.chickenpesto));

    private OnItemClick mCallback;
    private CustomAdapterCart adapterCart = this;

    public CustomAdapterCart(Context context, int textViewResourceId, List<Data> objects, OnItemClick listener){
        super(context, textViewResourceId, objects);

            layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            this.objectCart = objects;
            this.mCallback = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        Data data = (Data)getItem(position);

        //Create rows with item_list_row layout
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_list_row_cart, null);
        }

        //set the all views with the data
        ImageView itemImage;
        itemImage = (ImageView)convertView.findViewById(R.id.itemCartImage);

       itemImage.setImageResource(imgPics.get(position));
       itemImage.setAdjustViewBounds(true);


        TextView itemName;
        itemName = (TextView)convertView.findViewById(R.id.itemCartName);
        itemName.setText(data.getItemName());

        TextView itemDiscount = convertView.findViewById(R.id.itemCartDiscount);
        double discountedPrice = 0;
        if(data.getItemDiscountedPrice() > 0){
            discountedPrice = data.getItemPrice() -
                    (data.getItemPrice()*(data.getItemDiscountedPrice()/100));
            itemDiscount.setText("Discounted!!!");

        }else{
            discountedPrice = data.getItemPrice();
        }

        TextView itemPrice;
        itemPrice = (TextView)convertView.findViewById(R.id.itemCartPrice);
        itemPrice.setText("$ " + String.format("%.2f",discountedPrice));

        //Delete item button(each rows)
        Button btnDelete = convertView.findViewById(R.id.btnDelete);
        final int itemID = data.getItemID();
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //cart data from sharedPreferences
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("sf",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String txtCart = sharedPreferences.getString("cart","");

                String[] cartArray = txtCart.split(",");
                List<String> newCart = new ArrayList<>();
                String newTxtCart = "";
                for(int i = 0; i < cartArray.length; i ++){
                    if(!cartArray[i].equals(""+itemID)){

                        newCart.add(cartArray[i]);
                    }
                }
                for(int i = 0; i < newCart.size(); i ++){
                    if(i == newCart.size()-1){
                        newTxtCart += newCart.get(i);
                    }else{
                        newTxtCart += newCart.get(i) + ",";
                    }

                }
                //update sharedPreferences cart data
                editor.putString("cart", newTxtCart);
                editor.commit();
                //remove exited data for updating cart list
                objectCart.remove(position);
                //pass the data to Activity Main
                mCallback.onClick(objectCart);
                Toast.makeText(getContext(),"Sucessfully deleted from your cart", Toast.LENGTH_SHORT).show();
                //notify adapter to update list
                adapterCart.notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
