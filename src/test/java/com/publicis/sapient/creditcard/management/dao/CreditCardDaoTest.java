package com.publicis.sapient.creditcard.management.dao;

import com.publicis.sapient.creditcard.management.entity.CreditCard;
import com.publicis.sapient.creditcard.management.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CreditCardDaoTest {

    @Autowired
    CreditCardDao creditCardDao;

    @Test
    @DisplayName("Checks insert and get all credit cards")
    public void testCreateAndGetAll() {
        TestUtil.createCreditCardRequest();
        CreditCard creditCard = CreditCard.builder().cardNumber("4242424242426742").name("Test").limit(BigDecimal.valueOf(40.22)).build();
        CreditCard creditCard1 = creditCardDao.save(creditCard);
        List<CreditCard> creditCards = creditCardDao.findAll();
        CreditCard creditCadResponse = creditCards.get(0);
        assertEquals("4242424242426742",creditCadResponse.getCardNumber());
        assertEquals("GBP",creditCadResponse.getCurrency());
        assertEquals(BigDecimal.valueOf(0.0),creditCadResponse.getBalance());
    }
}