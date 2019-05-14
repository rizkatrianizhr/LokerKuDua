package com.e.aplikasiku.models;

public class Locker {

    int cost;
    int isOccupied;
    int isOpen;
    String occupiedBy;

    public Locker(int cost, int isOccupied, int isOpen, String occupiedBy) {
        this.cost = cost;
        this.isOccupied = isOccupied;
        this.isOpen = isOpen;
        this.occupiedBy = occupiedBy;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(int isOccupied) {
        this.isOccupied = isOccupied;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public String getOccupiedBy() {
        return occupiedBy;
    }

    public void setOccupiedBy(String occupiedBy) {
        this.occupiedBy = occupiedBy;
    }
}
