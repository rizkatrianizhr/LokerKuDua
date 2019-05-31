package com.e.aplikasiku.models;

public class Order {
    String idLocker;
    String email;
    String oorderDate;

    int bill;

    public Order(String idLocker, String email, String oorderDate, int bill) {
        this.idLocker = idLocker;
        this.email = email;
        this.oorderDate = oorderDate;
        this.bill = bill;
    }

    public String getIdLocker() {
        return idLocker;
    }

    public void setIdLocker(String idLocker) {
        this.idLocker = idLocker;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOorderDate() {
        return oorderDate;
    }

    public void setOorderDate(String oorderDate) {
        this.oorderDate = oorderDate;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }
}
