package com.pmul.cajasupermercado.model;

public class Producto {
    private String name;
    private int id, stock, w_stock;
    private double price;

    public Producto() {
    }

    public Producto(String name, int id, int stock, int w_stock, double price) {
        this.name = name;
        this.id = id;
        this.stock = stock;
        this.w_stock = w_stock;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getW_stock() {
        return w_stock;
    }

    public void setW_stock(int w_stock) {
        this.w_stock = w_stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", stock=" + stock +
                ", w_stock=" + w_stock +
                ", price=" + price +
                '}';
    }
}