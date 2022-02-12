package com.publicis.sapient.creditcard.management.service;

import com.publicis.sapient.creditcard.management.entity.CreditCard;
import com.publicis.sapient.creditcard.management.model.CreditCardRequest;
import com.publicis.sapient.creditcard.management.model.CreditCardResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICreditCardService {
    
    CreditCardResponse createCreditCard(CreditCard creditCard);

    List<CreditCardResponse> getAllCreditCards( Pageable page);

}
