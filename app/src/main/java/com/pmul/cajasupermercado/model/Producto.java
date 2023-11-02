package com.pmul.cajasupermercado.model;

import java.util.Objects;

public class Producto {
    private String name;
    private int id, stock, w_stock;
    private double price;

    public Producto() {
    }

    /**
     * Constructor completo para crear los productos de la tabla productos
     * @param id
     * @param name
     * @param stock
     * @param w_stock
     * @param price
     */
    public Producto(int id, String name, int stock, int w_stock, double price) {
        this.name = name;
        this.id = id;
        this.stock = stock;
        this.w_stock = w_stock;
        this.price = price;
    }

    /**
     * Constructor para crear los productos del carrito
     * @param id
     * @param name
     * @param quantity
     * @param price
     */
    public Producto(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.stock = quantity; //Aquí el stock representa la cantidad de ese producto añadida al carrito
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return id == producto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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