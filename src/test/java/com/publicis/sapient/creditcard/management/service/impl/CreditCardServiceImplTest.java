
package com.publicis.sapient.creditcard.management.service.impl;

import com.publicis.sapient.creditcard.management.dao.CreditCardDao;
import com.publicis.sapient.creditcard.management.entity.CreditCard;
import com.publicis.sapient.creditcard.management.mapper.CreditCardMapper;
import com.publicis.sapient.creditcard.management.model.CreditCardResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static com.publicis.sapient.creditcard.management.util.TestUtil.createCreditCard;
import static com.publicis.sapient.creditcard.management.util.TestUtil.createCreditCardResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
    public void init() {
        creditCardService = new CreditCardServiceImpl(creditCardDao, creditCardMapper);
    }

  @Test
    @DisplayName("Checks a valid credit card addition")
    public void testCreditCardCreate() {
        CreditCard creditCard = createCreditCard();
        CreditCardResponse creditCardResponse = createCreditCardResponse();
        when(creditCardMapper.cardDataToResponse(any())).thenReturn(creditCardResponse);
        when(creditCardDao.save(any())).thenReturn(creditCard);
        CreditCardResponse response = creditCardService.createCreditCard(creditCard);
        verify(creditCardMapper, times(2)).cardDataToResponse(creditCard);
        assertEquals("test", response.getName());
    }

 @Test
    public void testGetAllCreditCards() {
        CreditCard creditCard = createCreditCard();
        Pageable pageable = PageRequest.of(0, 3);
        Page<CreditCard> creditCardPage = new PageImpl<>(Collections.singletonList(creditCard));
        when(creditCardDao.findAll(pageable)).thenReturn(creditCardPage);
        List<CreditCardResponse> response = creditCardService.getAllCreditCards(pageable);
        assertEquals(1, response.size());
        verify(creditCardMapper).cardDataToResponse(creditCard);
    }

}
