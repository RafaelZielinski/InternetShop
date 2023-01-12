package pl.zielinski.shop.common.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class WebServiceEmailServiceTest {

    @Autowired
    private WebServiceEmailService under;

    @Test
    void itShouldSendThrowRuntimeexceptionError() {
        //Given
        under = new WebServiceEmailService();
        //When
        //Then
        assertThatThrownBy(() -> under.send("test@wp.pl", "Orders", "List of orders"))
                .hasMessageContaining("Not implemented yet")
                .isInstanceOf(RuntimeException.class);
    }
}
