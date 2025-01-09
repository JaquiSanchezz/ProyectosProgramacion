package models;

public class SelectedProduct {

    private int quantity;
    private Product product;
    private float total;

    public SelectedProduct(int quantity, Product product, float total) {
        this.quantity = quantity;
        this.product = product;
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
