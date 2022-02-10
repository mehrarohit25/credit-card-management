package com.publicis.sapient.creditcard.management.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LuhnValidatorTest {
    private static final String VALID_CARD_NUMBER_SIXTEEN = "4242424242426742";
    private static final String INVALID_CARD_NUMBER_SIXTEEN = "1111111111111111";

    private LuhnValidator luhnValidator;

    @BeforeAll
    public void init() {
        luhnValidator = new LuhnValidator();
    }

    @Test
    @DisplayName("Tests a valid 16 digit card")
    public void testValidCardNumber_16_length() {

        boolean isValid = luhnValidator.isValid(VALID_CARD_NUMBER_SIXTEEN, null);
        assertTrue(isValid, "Not a valid credit card number");
    }

    @Test
    @DisplayName("Tests an invalid 16 digit card")
    public void testInvalidCardNumber_16_length() {

        boolean isValid = luhnValidator.isValid(INVALID_CARD_NUMBER_SIXTEEN, null);
        assertFalse(isValid, "valid credit card number passed");
    }

}
