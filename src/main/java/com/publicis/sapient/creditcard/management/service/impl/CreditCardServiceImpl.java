package com.publicis.sapient.creditcard.management.service.impl;

import com.publicis.sapient.creditcard.management.dao.CreditCardDao;
import com.publicis.sapient.creditcard.management.entity.CreditCard;
import com.publicis.sapient.creditcard.management.mapper.CreditCardMapper;
import com.publicis.sapient.creditcard.management.model.CreditCardResponse;
import com.publicis.sapient.creditcard.management.service.ICreditCardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditCardServiceImpl implements ICreditCardService {

    private CreditCardDao creditCardDao;
    private CreditCardMapper creditCardMapper;

    public CreditCardServiceImpl(CreditCardDao creditCardDao, CreditCardMapper creditCardMapper) {
        this.creditCardDao = creditCardDao;
        this.creditCardMapper = creditCardMapper;
    }

    @Override
    public CreditCardResponse createCreditCard(CreditCard creditCard) {
        return creditCardMapper.cardDataToResponse(creditCardDao.save(creditCard));
    }

    @Override
    public List<CreditCardResponse> getAllCreditCards() {
        return creditCardDao.findAll().stream()
                .map(creditCardMapper::cardDataToResponse)
                .collect(Collectors.toList());
    }
}
