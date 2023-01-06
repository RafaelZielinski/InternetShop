package pl.zielinski.shop.admin.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zielinski.shop.admin.order.model.AdminOrder;
import pl.zielinski.shop.common.dto.OrderStatus;
import pl.zielinski.shop.common.mail.EmailClientService;

import static pl.zielinski.shop.admin.order.service.AdminOrderEmailMessage.createCompletedEmailMessage;
import static pl.zielinski.shop.admin.order.service.AdminOrderEmailMessage.createProcessingEmailMessage;
import static pl.zielinski.shop.admin.order.service.AdminOrderEmailMessage.createRefundEmailMessage;

@Service
@AllArgsConstructor
class EmailNotificationForStatusChange {

    private final EmailClientService emailClientService;

   public void sendEmailNotification(OrderStatus newStatus, AdminOrder adminOrder) {
        // statusy PROCESSING, COMPLETED, REFUND
        if(newStatus == OrderStatus.PROCESSING) {
            sendEmail(adminOrder.getEmail(),
                    "Zamówienie " + adminOrder.getId() + " zmieniło status na: " + newStatus.getValue(),
                    createProcessingEmailMessage(adminOrder.getId(), newStatus) );
        } else if(newStatus ==OrderStatus.REFUND){
            sendEmail(adminOrder.getEmail(),
                    "Zamówienie " + adminOrder.getId() + " zostało zrealizowane"  ,
                    createCompletedEmailMessage(adminOrder.getId(), newStatus)) ;
        } else if(newStatus == OrderStatus.COMPLETED){
            createRefundEmailMessage(adminOrder.getId(), newStatus) ;
        }
    }

    private void sendEmail(String email, String subject, String content) {
        emailClientService.getInstance().send(email, subject, content);
    }
}
