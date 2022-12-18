package pl.zielinski.shop.order.service.mapper;

import pl.zielinski.shop.order.model.Order;

import java.time.format.DateTimeFormatter;

public class OrderMessageServiceMapper {

    public static String createEmailMessage(Order order) {
        return "Twoje zamówienie o id: " + order.getId() +
                "\nData złożenia: " + order.getPlaceDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                "\nWartość: " + order.getGrossValue() + " PLN " +
                "\n\n" +
                "\nPłatność: " + order.getPayment().getName() +
                (order.getPayment().getNote() != null ? "\n" + order.getPayment().getNote() : "") +
                "\n\nDziękujęmy za zakupy.";
    }
}
