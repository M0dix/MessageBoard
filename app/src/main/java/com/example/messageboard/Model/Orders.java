package com.example.messageboard.Model;

public class Orders {
    private String date, userName, userPhoneNumber, productName, count, productPhoneNumber, price;

    public Orders() {
    }

    public Orders(String date, String userName, String userAddress, String userPhoneNumber, String productName, String count, String productPhoneNumber, String price) {
        this.date = date;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.productName = productName;
        this.count = count;
        this.productPhoneNumber = productPhoneNumber;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductPhoneNumber() {
        return productPhoneNumber;
    }

    public void setProductPhoneNumber(String productPhoneNumber) {
        this.productPhoneNumber = productPhoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
