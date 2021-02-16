package com.example.finalproject_group7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

//2020.11.29 Morning -> 11.30 7pm Last modified


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;  //Drawer
    NavigationView navigationView;  //Navigation
    Toolbar toolbar;    //Toolbar
    FragmentShop fragmentShop = new FragmentShop(); //Used for displaying items(menus)
    Main_Fragment fragment = new Main_Fragment();   //Main fragment
    FragmentDiscount fragmentDiscount = new FragmentDiscount(); //Used for displaying discounted items(menus)
    FragmentOrder fragmentOrder = new FragmentOrder();  //Used for showing order history
    FragmentCart fragmentCart = new FragmentCart(); //Used for displaying items a user chosen

    //For back button event

    List<Data> temp = new ArrayList<>();

    //SQLiteDatabase db;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDB();
        createTables();
        addMenuData(1, "Pepperoni", 20.99,"Premium pepperoni, real cheese made from mozzarella and your choice of crust",0.0);
        addMenuData(2, "MeatLovers", 22.99,"A masterpiece of hearty, high-quality meats including pepperoni, savory sausage, real beef, and hickory-smoked bacon",0.0);
        addMenuData(3, "ChickenLovers", 21.99,"Smothered in smoky BBQ sauce, it’s topped with grilled chicken, and hickory-smoked bacon",15.99);
        addMenuData(4, "Classic", 15.99,"A mouth-watering combination of pepperoni, savory Italian sausage, and fresh green peppers",10.99);
        addMenuData(5, "Feast", 20.99,"Loaded with tender slices of steak, melted Provolone cheese, onions, green peppers, mushrooms, and American cheese",0.0);
        addMenuData(6, "Combo", 27.99,"Loads of pepperoni, ham, savory Italian sausage, beef crumble, and fresh onions",20.99);
        addMenuData(7, "Italian", 30.99,"Creamy Alfredo sauce, fresh baby spinach, fresh onions, feta, Parmesan-Asiago, provolone and cheese",0.0);
        addMenuData(8, "Mediterranean", 28.99,"Roasted red peppers, fresh baby spinach, fresh onions, fresh mushrooms, and tomatoes",22.99);
        addMenuData(9, "Veggie", 10.99,"A medley of fresh green peppers, onion, tomatoes, mushrooms, and olives.",0.0);
        addMenuData(10, "Chicken Pesto", 32.99,"American cheese, taco seasoning, grilled chicken, fresh onions, fresh green peppers, and fresh tomatoes",0.0);


        //Navigation
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        //Call a method by passing toolbar
        setSupportActionBar(toolbar);

        //Put navigation view to front
        navigationView.bringToFront();

        //Make Custom navigation drawer  ** Reference : https://guides.codepath.com/android/fragment-navigation-drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //Click Events for FragmentMain
        RelativeLayout layoutShop = findViewById(R.id.layoutShop);
        RelativeLayout layoutSale = findViewById(R.id.layoutSale);
        RelativeLayout layoutOrder = findViewById(R.id.layoutOrder);
        RelativeLayout layoutCart = findViewById(R.id.layoutCart);
        RelativeLayout layoutMap = findViewById(R.id.layoutMap);
        RelativeLayout layoutContact = findViewById(R.id.layoutContact);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.layoutShop:
                        onFragmentChange(1, null);
                        navigationView.setCheckedItem(R.id.nav_shop);
                        break;
                    case R.id.layoutSale:
                        onFragmentChange(2, null);
                        navigationView.setCheckedItem(R.id.nav_shop);
                        break;
                    case R.id.layoutOrder:
                        onFragmentChange(3, null);
                        navigationView.setCheckedItem(R.id.nav_order);
                        break;
                    case R.id.layoutCart:
                        onFragmentChange(4, null);
                        navigationView.setCheckedItem(R.id.nav_cart);
                        break;
                    case R.id.layoutMap:
                        onFragmentChange(5, null);
                        navigationView.setCheckedItem(R.id.nav_location);
                        break;
                    case R.id.layoutContact:
                        contactDialog();
                }
            }
        };
        layoutShop.setOnClickListener(clickListener);
        layoutSale.setOnClickListener(clickListener);
        layoutOrder.setOnClickListener(clickListener);
        layoutCart.setOnClickListener(clickListener);
        layoutMap.setOnClickListener(clickListener);
        layoutContact.setOnClickListener(clickListener);

    } //End of onCreate Method

    //If a user clicks a backkey
    public void onBackPressed(){
        super.onBackPressed();

    }

    //Click Event for NavigationBar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                onFragmentChange(0, null);
                break;
            case R.id.nav_shop:
                onFragmentChange(1, null);
                break;
            case R.id.nav_order:
                onFragmentChange(3, null);
                break;
            case R.id.nav_cart:
                onFragmentChange(4, null);
                break;
            case R.id.nav_location:
                onFragmentChange(5, null);
                break;
            case R.id.nav_contact:
                contactDialog();
                break;
        }

        //Set the checked item a user selected
        navigationView.setCheckedItem(item.getItemId());

        //Close the drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Fragment Changing
    //using addToBackStack method to move back previous Fragment
    public void onFragmentChange(int index , List<String> data){
        ScrollView scroll = findViewById(R.id.scroll);
        if(index == 0 ){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment ).addToBackStack(null).commit();
        }else if(index == 1){
            temp.clear();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentShop).addToBackStack(null).commit();
        }else if(index == 2){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentDiscount).addToBackStack(null).commit();
        } else if(index == 3){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentOrder).addToBackStack(null).commit();
        }else if(index == 4){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentCart).addToBackStack(null).commit();
        }else if(index == 5){
            Intent intent = new Intent(this, GoogleMapActivity.class);
            startActivity(intent);
        }else if(index == 7){
            temp.clear();

            //Create fragmentItetm object using new Instance
            FragmentItem fragmentItem = FragmentItem.newInstance(
                    Integer.parseInt(data.get(0)),data.get(1),data.get(2),Double.parseDouble(data.get(3)),
                    Double.parseDouble(data.get(4)),data.get(5));
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentItem).addToBackStack(null).commit();
        }
        //When we move the Fragment, reset the starting point
        scroll.smoothScrollTo(0, navigationView.getTop());
    }


    //get data from SharedPreference
    public ArrayList<Data> ReadItemsData() {

        /*private String itemImage ;
        private String itemName ;
        private String itemDescription ;
        private double itemDiscountedPrice ;
        private double itemPrice ;
        private int itemID ;*/

        String queryStr = "SELECT * FROM MENU;";

        //insert, delete, update : build in exception handling
        try{
            Cursor cursor = db.rawQuery(queryStr, null);
            if(cursor != null){  //If data exists
                cursor.moveToFirst();   //Go to first row
                while(!cursor.isAfterLast()){ //If it is not the end row

                    temp.add(new Data(cursor.getString(cursor.getColumnIndex("ProductName")), "R.drawable.home_icon", cursor.getDouble(cursor.getColumnIndex("ProductPrice")), cursor.getDouble(cursor.getColumnIndex("ProductDiscountedPrice")), cursor.getInt(cursor.getColumnIndex("ProductID")), cursor.getString(cursor.getColumnIndex("ProductDescription")))); //Adding record to the list of string array
                    cursor.moveToNext();

                }
            }
        }catch(Exception ex){
            Log.d("DB PROJECT", "Item does not add" + ex.getMessage());
        }
        return (ArrayList<Data>) temp;
    }

    //For Contact
    public void contactDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Please call us").setMessage("Would you like to call us?");
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:778-855-8410"));
                startActivity(tt);

            }
        });

        builder.show();
    }

    //Preventing List get cut  ** Reference : https://stackoverflow.com/questions/21620764/android-listview-measure-height
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        //Calculate the desired width of the listview and it is used as a constant
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        //pass desiredWidth to listitem measure
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //Set height
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void openDB()
    {
        //Open database
        try{
            db = openOrCreateDatabase("TomatoDB_v3.db", Context.MODE_PRIVATE, null);
            Log.d("DB PROJECT", "Database Open _ Main Activity");
        }catch(Exception ex){
            Log.d("DB PROJECT", "Database opening error _ Main Activity" + ex.getMessage());
        }
    }

    public void createTables(){
        try{
            //It turns FK features on, PRAGMA(pragmatic) : kind of meta commands
            String setPRAGMAForeignKeysOn = "PRAGMA foreign_keys=ON;";

            String dropMenuTableCmd = "DROP TABLE IF EXISTS" + " MENU";
            String dropItemOrderTableCmd = "DROP TABLE IF EXISTS" + " ITEMORDER";

            String createMenuTableCmd = "CREATE TABLE MENU " + "(ProductID INTEGER PRIMARY KEY, ProductName TEXT, ProductPrice REAL,ProductDescription TEXT,ProductDiscountedPrice REAL);";

            String createItemOrderTableCmd = "CREATE TABLE ITEMORDER " +
                    "(orderID INTEGER PRIMARY KEY AUTOINCREMENT, orderDate TEXT, orderList TEXT, orderStatus TEXT,totalPrice REAL,itemID TEXT);";


           //String createImageTableCmd = "CREATE TABLE IMAGE " + "(ImageID INTEGER PRIMARY KEY, Image TEXT);";

            //Specify features turn on
            db.execSQL(setPRAGMAForeignKeysOn);

            db.execSQL(dropItemOrderTableCmd);     //Dropping grades table first
            db.execSQL(dropMenuTableCmd); //Cannot drop students table if grades table references student id

            db.execSQL(createMenuTableCmd);
            db.execSQL(createItemOrderTableCmd);
            //db.execSQL(createImageTableCmd);

            Log.e("DB PROJECT", "Creating tables");

        }catch(Exception ex){
            Log.e("DB PROJECT", "Error creating tables");
        }
    }

    //add records to MENU table
    private  void addMenuData(int id, String name, Double price,String desc,Double discountedPrice){
        long result;
        ContentValues val = new ContentValues();

        val.put("ProductID", id);
        val.put("ProductName", name);
        val.put("ProductPrice", price);
        val.put("ProductDescription", desc);
        val.put("ProductDiscountedPrice", discountedPrice);

        result = db.insert("MENU",null,val); //no field may be nullable, so no empty

        Log.e("DB PROJECT", "Insert MENU data");

        if(result != -1){
            Log.d("DB PROJECT", "rowid == " + result + " insert ProductID with " + id);
        }else{
            Log.d("DB PROJECT", "Error inserting menu rec with id " + id);

        }

    }

}