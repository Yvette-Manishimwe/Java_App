package com.ecommerce.ne.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoleValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole {

    String message() default "Invalid role. Role must be either 'ROLE_ADMIN' or 'ROLE_USER'.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
