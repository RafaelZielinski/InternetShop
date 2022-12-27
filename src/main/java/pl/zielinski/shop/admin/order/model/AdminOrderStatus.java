package pl.zielinski.shop.admin.order.model;

public enum AdminOrderStatus {
    NEW("Nowe"),
    COMPLETED("Zrealizowane"),
    PAID("Opłacone"),
    PROCESSING("Przetwarzanie"),
    WAITING_FOR_DELIVERY("Czeka na dostawe"),
    CANCELED("Anulowane"),
    REFUND("Zwrócone");

    private String value;

    AdminOrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
