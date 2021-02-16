package com.example.finalproject_group7;

public class Data {

        private String itemImage;
        private String itemName;
        private String itemDescription;
        private double itemDiscountedPrice;
        private double itemPrice;
        private int itemID;

        Data(String itemName, String itemImage, double itemPrice, double itemDiscountedPrice, int itemID,
             String itemDescription){
            this.itemName = itemName;
            this.itemImage = itemImage;
            this.itemPrice = itemPrice;
            this.itemDiscountedPrice = itemDiscountedPrice;
            this.itemID = itemID;
            this.itemDescription = itemDescription;
        }

        public String getItemImage() {
            return this.itemImage;
        }
        public String getItemName() {
            return this.itemName;
        }
        public String getItemDescription() {
            return this.itemDescription;
        }
        public double getItemDiscountedPrice() {
            return this.itemDiscountedPrice;
        }
        public double getItemPrice() {
            return this.itemPrice;
        }
        public int getItemID() {
            return this.itemID;
        }
}
