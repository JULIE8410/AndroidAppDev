package com.example.finalproject_group7;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




//Custom Adapter Class for FragmentShop and FragmentDiscount
public class CustomAdapter extends ArrayAdapter<Data> {

    private LayoutInflater layoutInflater;

    private List<Integer> imgPics =
            new ArrayList<>(Arrays.asList(R.drawable.pepperoni,R.drawable.meatlovers,R.drawable.chickenlovers,
                    R.drawable.classic, R.drawable.feast, R.drawable.combo,
                    R.drawable.italian, R.drawable.mediterranean, R.drawable.veggie, R.drawable.chickenpesto));

    public CustomAdapter(Context context, int textViewResourceId, List<Data> objects){
        super(context, textViewResourceId, objects);
            layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Data data = (Data)getItem(position);

        //Create rows with item_list_row layout
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_list_row, null);
        }

        //set the all views with the data
        ImageView itemImage;
        itemImage = (ImageView)convertView.findViewById(R.id.itemImage);

        itemImage.setImageResource(imgPics.get(position));
        itemImage.setAdjustViewBounds(true);


        TextView itemName;
        itemName = (TextView)convertView.findViewById(R.id.itemName);
        itemName.setText(data.getItemName());

        TextView itemDiscount = convertView.findViewById(R.id.itemDiscount);
        double discountedPrice = 0;
        if(data.getItemDiscountedPrice() != 0){
            discountedPrice = data.getItemPrice() -
                    (data.getItemPrice()*(data.getItemDiscountedPrice()/100));
            itemDiscount.setText("Discounted");

        }else{
            discountedPrice = data.getItemPrice();
        }

        TextView itemPrice;
        itemPrice = (TextView)convertView.findViewById(R.id.itemPrice);
        itemPrice.setText("$ " + String.format("%.2f", discountedPrice));

        return convertView;
    }
}
