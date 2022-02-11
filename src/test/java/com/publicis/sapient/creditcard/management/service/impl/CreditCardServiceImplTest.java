package com.publicis.sapient.creditcard.management.service.impl;

import com.publicis.sapient.creditcard.management.dao.CreditCardDao;
import com.publicis.sapient.creditcard.management.entity.CreditCard;
import com.publicis.sapient.creditcard.management.mapper.CreditCardMapper;
import com.publicis.sapient.creditcard.management.model.CreditCardResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreditCardServiceImplTest {

    @Mock
    private CreditCardDao creditCardDao;
    @Mock
    private CreditCardMapper creditCardMapper;
    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @BeforeAll
    public void setup() {
        creditCardService = new CreditCardServiceImpl(creditCardDao, creditCardMapper);
    }

    @Test
    @DisplayName("Checks a valid credit card addition")
    public void testCreditCardCreate() {
        CreditCard creditCard = CreditCard.builder().id(1L).cardNumber("4242424242426742").name("TestCard").build();
        CreditCardResponse creditCardResponse = CreditCardResponse.builder().cardNumber("4242424242426742").name("TestCard").build();
        when(creditCardMapper.cardDataToResponse(any())).thenReturn(creditCardResponse);
        when(creditCardDao.save(any())).thenReturn(creditCard);
        CreditCardResponse response = creditCardService.createCreditCard(creditCard);
        verify(creditCardMapper).cardDataToResponse(creditCard);
        Assertions.assertEquals("TestCard", response.getName());
    }

    @Test
    public void testGetAllCreditCards() {
        // TODO: 10/02/2022  Add appropriate test ,also some negative tests
    }

}