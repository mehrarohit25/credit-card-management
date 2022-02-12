package com.publicis.sapient.creditcard.management.controller;

import com.publicis.sapient.creditcard.management.api.CardsApi;
import com.publicis.sapient.creditcard.management.mapper.CreditCardMapper;
import com.publicis.sapient.creditcard.management.model.CreditCardRequest;
import com.publicis.sapient.creditcard.management.model.CreditCardResponse;
import com.publicis.sapient.creditcard.management.service.ICreditCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class CreditCardController implements CardsApi {

    private static final Logger LOG = LoggerFactory.getLogger(CreditCardController.class);

    private ICreditCardService creditCardService;

    private CreditCardMapper creditCardMapper;

    public CreditCardController(ICreditCardService creditCardService, CreditCardMapper creditCardMapper) {
        this.creditCardService = creditCardService;
        this.creditCardMapper = creditCardMapper;
    }

    @Override
    public ResponseEntity<CreditCardResponse> addCreditCard(@Valid CreditCardRequest creditCardRequest) {
        LOG.info("Credit card request received with {}", creditCardRequest);
        CreditCardResponse creditCardResponse = creditCardService.createCreditCard(creditCardMapper.cardRequestToCreditCard(creditCardRequest));
        LOG.info("Credit Card added with {}", creditCardResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(creditCardResponse);
    }

    @Override
    public ResponseEntity<List<CreditCardResponse>> getCreditCards(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        LOG.info("Get all Credit card started");
        return ResponseEntity.status(HttpStatus.OK).body(creditCardService.getAllCreditCards(pageable));
    }
}
