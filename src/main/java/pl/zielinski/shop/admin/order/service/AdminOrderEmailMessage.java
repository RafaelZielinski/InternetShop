package pl.zielinski.shop.admin.order.service;


import pl.zielinski.shop.common.dto.OrderStatus;

public class AdminOrderEmailMessage {

    public static String createProcessingEmailMessage(Long id, OrderStatus newStatus) {
       return "Twoje zamówienie: " + id + " jest przetwarzane." +
               "\nStatus został zmieniony na: " + newStatus.getValue() +
               "\nTwoje zamówienie jest przetwarzane przez naszych pracowników" +
               "\nPo skompletowaniu niezwłocznie przekażemy je do wysyłki" +
               "\n\n Pozdrawiamy" +
               "\n Sklep Shop";
    }

    public static String createCompletedEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamówienie: " + id + " zostało zrealizowane." +
                "\nStatus twojego zamówienia został zmieniony na: " + newStatus.getValue() +
                "\nDziękujęmy za zakupy i zapraszamy ponownie" +
                "\n Sklep Shop";
    }
    public static String createRefundEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamówienie: " + id + " zostało zwrócone." +
                "\nStatus twojego zamówienia został zmieniony na: " + newStatus.getValue() +
                "\nPozdrawiamy" +
                "\n Sklep Shop";
    }
}
