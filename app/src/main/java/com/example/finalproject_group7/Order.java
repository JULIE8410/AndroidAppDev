package com.example.finalproject_group7;

public class Order {
        private String orderDate;
        private String orderStatus;
        private String orderList;
        private int orderID;

        public Order(String orderDate, String orderStatus, String orderList, int orderID){
            this.orderDate = orderDate;
            this.orderID = orderID;
            this.orderList = orderList;
            this.orderStatus = orderStatus;
        }
        public String getOrderDate(){return this.orderDate;}
        public String getOrderStatus(){return this.orderStatus;}
        public String getOrderList(){return this.orderList;}
        public int getOrderID(){return this.orderID;}
}
