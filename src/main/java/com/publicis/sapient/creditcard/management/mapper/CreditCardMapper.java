package com.publicis.sapient.creditcard.management.mapper;


import com.publicis.sapient.creditcard.management.entity.CreditCard;
import com.publicis.sapient.creditcard.management.model.CreditCardRequest;
import com.publicis.sapient.creditcard.management.model.CreditCardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.Currency;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {
    @Named("cardRequestToCreditCard")
    CreditCard cardRequestToCreditCard(CreditCardRequest creditCardRequest);

    @Mapping(source = "limit", target = "limit", qualifiedByName = "convertCurrency")
    @Mapping(source = "balance", target = "balance", qualifiedByName = "convertCurrency")
    @Named("cardRequestToResponse")
    CreditCardResponse cardDataToResponse(CreditCard creditCard);

    @Named("convertCurrency")
    public static String addCurrency(final BigDecimal balance) {
        Currency currency = Currency.getInstance("GBP");
        return currency.getSymbol() + balance;
    }
}
