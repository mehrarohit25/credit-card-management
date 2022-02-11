package com.publicis.sapient.creditcard.management.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class LuhnValidator implements ConstraintValidator<Luhn, String> {
    

    public static final int TEN_INT = 10;
    public static final int TWO_INT = 2;
    public static final int NINE_INT = 9;
    public static final int ZERO_INT = 0;
    public static final String EMPTY_STRING = "";

    @Override
    public boolean isValid(final String cardNumber, final ConstraintValidatorContext constraintValidatorContext) {
        return isLuhnNumber(cardNumber);
    }
    /**
     * Validates the card based on Luhn's algorithm
     *
     * @param cardNumber cardNumber to be validated
     * @return true if valid false otherwise
     */
    private static boolean isLuhnNumber(final String cardNumber) {
        if(StringUtils.hasLength(cardNumber)) {
            final int[] cardDigitArray = new int[cardNumber.length()];

            for (int i = ZERO_INT; i < cardNumber.length(); i++) {
                final char c = cardNumber.charAt(i);
                if (!Character.isDigit(c)) {
                    return false;
                }
                cardDigitArray[i] = Integer.parseInt(EMPTY_STRING + c);
            }

            for (int j = cardDigitArray.length - TWO_INT; j >= ZERO_INT; j = j - TWO_INT) {
                int num = cardDigitArray[j];
                num = num * TWO_INT;
                if (num > NINE_INT) {
                    num = num % TEN_INT + num / TEN_INT;
                }
                cardDigitArray[j] = num;
            }

            return Arrays.stream(cardDigitArray).sum() % TEN_INT == ZERO_INT;
        }
        return  true;
    }
}

