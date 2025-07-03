package org.example;

public class WorkSpace {
    String id, type;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    double price;
    boolean available;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WorkSpace(String id, String type, double price, boolean available) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public String toFileString() {
        return "ID: " + id + ", " + "Type: " + type + ", " + "Price: "+ price + ", " + "Status: " + available;
    }




}
