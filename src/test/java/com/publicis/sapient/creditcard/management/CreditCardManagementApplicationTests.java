package com.publicis.sapient.creditcard.management;

import com.publicis.sapient.creditcard.management.controller.CreditCardController;
import com.publicis.sapient.creditcard.management.model.CreditCardRequest;
import com.publicis.sapient.creditcard.management.model.CreditCardResponse;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.publicis.sapient.creditcard.management.util.TestUtil.createCreditCardRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CreditCardManagementApplicationTests {

    @Autowired
    CreditCardController creditCardController;

    @Test
    void contextLoads() {
    }
    @Test
    @Description("Test the E2E flow for add credit card")
    public void testAddCreditCard() {
        CreditCardRequest creditCardRequest = createCreditCardRequest();
        ResponseEntity<CreditCardResponse> creditCardResponseEntity = creditCardController.addCreditCard(creditCardRequest);
        assertEquals(HttpStatus.CREATED,creditCardResponseEntity.getStatusCode());
        assertEquals("£40.22",creditCardResponseEntity.getBody().getLimit());
    }

    @Test
    @Description("Test the E2E flow for get all credit cards")
    public void tesGetAll() {
        Pageable pageable = PageRequest.of(0, 3);
        ResponseEntity<List<CreditCardResponse>> creditCardResponseEntity = creditCardController.getCreditCards(pageable);
        assertEquals(HttpStatus.OK, creditCardResponseEntity.getStatusCode());
        assertEquals(3, creditCardResponseEntity.getBody().size());
    }
}
