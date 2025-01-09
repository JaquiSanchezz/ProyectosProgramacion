package models;

public class SelectedServices {

    private int quantity;
    private Service service;
    private float total;
    private String note;
    private String delivered_date;
    private String limit_date;

    public SelectedServices() {
    }

    public SelectedServices(int quantity, Service service, float total, String note, String delivered_date, String limit_date) {
        this.quantity = quantity;
        this.service = service;
        this.total = total;
        this.note = note;
        this.delivered_date = delivered_date;
        this.limit_date = limit_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDelivered_date() {
        return delivered_date;
    }

    public void setDelivered_date(String delivered_date) {
        this.delivered_date = delivered_date;
    }

    public String getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(String limit_date) {
        this.limit_date = limit_date;
    }
}
