package com.publicis.sapient.creditcard.management.util;

import com.publicis.sapient.creditcard.management.model.CreditCardRequest;
import com.publicis.sapient.creditcard.management.model.CreditCardResponse;

public class TestUtil {

    public static CreditCardRequest createCreditCardRequest(){
        return CreditCardRequest.builder().cardNumber("4242424242426742").name("Test").limit(40.22).build();
    }

    public static CreditCardResponse createCreditCardResponse(){
        return CreditCardResponse.builder().cardNumber("4242424242426742").name("test").limit("£40.22").balance("£0.0").build();
    }
}
