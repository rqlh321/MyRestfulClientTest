package com.example.sic.myrestfulclienttest;

import java.util.Date;

public class Product {

    private long id;
    private String name;
    private long cost;
    private Date date;

    public Product() {

    }

    public Product(String name, long cost) {
        super();
        this.name = name;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
