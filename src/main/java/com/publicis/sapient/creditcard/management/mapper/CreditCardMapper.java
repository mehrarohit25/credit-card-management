package com.publicis.sapient.creditcard.management.mapper;


import com.publicis.sapient.creditcard.management.entity.CreditCard;
import com.publicis.sapient.creditcard.management.model.CreditCardRequest;
import com.publicis.sapient.creditcard.management.model.CreditCardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Currency;

import static com.publicis.sapient.creditcard.management.util.Constants.DEFAULT_CURRENCY;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {

    @Named("cardRequestToCreditCard")
    @Mapping(source = "cardNumber", target = "cardNumber", qualifiedByName = "encodeCardNumber")
    CreditCard cardRequestToCreditCard(CreditCardRequest creditCardRequest);

    @Mapping(source = "limit", target = "limit", qualifiedByName = "convertCurrency")
    @Mapping(source = "balance", target = "balance", qualifiedByName = "convertCurrency")
    @Mapping(source = "cardNumber", target = "cardNumber", qualifiedByName = "decodeCardNumber")
    @Named("cardRequestToResponse")
    CreditCardResponse cardDataToResponse(CreditCard creditCard);

    @Named("convertCurrency")
    public static String addCurrency(BigDecimal balance) {
        Currency currency = Currency.getInstance(DEFAULT_CURRENCY);
        return currency.getSymbol() + balance;
    }

    @Named("encodeCardNumber")
    public static String encodeCardNumber(String cardNumber) {
        String encodedString = Base64.getEncoder().encodeToString(cardNumber.getBytes());
        return encodedString;
    }

    @Named("decodeCardNumber")
    public static String decodeCardNumber(String cardNumber) {
        byte[] decodedBytes = Base64.getDecoder().decode(cardNumber);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
