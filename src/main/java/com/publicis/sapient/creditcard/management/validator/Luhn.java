package com.publicis.sapient.creditcard.management.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.publicis.sapient.creditcard.management.util.Constants.NOT_LUHN;


@Documented
@Constraint(validatedBy = LuhnValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Luhn {
    String message() default NOT_LUHN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

