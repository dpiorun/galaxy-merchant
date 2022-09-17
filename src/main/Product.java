package main;

public class Product {
    String name;
    Integer unitPrice;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Integer getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(int price) {
        this.unitPrice = price;
    }
}
