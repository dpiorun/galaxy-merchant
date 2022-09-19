package galaxy;

public class Product {
    String name;
    Double unitPrice;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(double price) {
        this.unitPrice = price;
    }
}
