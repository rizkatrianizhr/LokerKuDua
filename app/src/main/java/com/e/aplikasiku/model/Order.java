package com.e.aplikasiku.model;

import java.util.Date;

public class Order {
    int bill;
    String email;
    String locker;
    Date orderDate;

    public Order(int bill, String email, String locker, Date orderDate) {
        this.bill = bill;
        this.email = email;
        this.locker = locker;
        this.orderDate = orderDate;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocker() {
        return locker;
    }

    public void setLocker(String locker) {
        this.locker = locker;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
