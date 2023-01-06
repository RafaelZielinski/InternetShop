package pl.zielinski.shop.common.dto;

public enum OrderStatus {
    NEW("Nowe"),
    COMPLETED("Zrealizowane"),
    PAID("Opłacone"),
    PROCESSING("Przetwarzanie"),
    WAITING_FOR_DELIVERY("Czeka na dostawe"),
    CANCELED("Anulowane"),
    REFUND("Zwrócone");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
