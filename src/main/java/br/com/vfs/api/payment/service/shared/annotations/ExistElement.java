package br.com.vfs.api.payment.service.shared.annotations;


import br.com.vfs.api.payment.service.shared.validators.ExistElementValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ExistElementValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ExistElement {
    String message() default "{br.com.vfs.api.payment.service.bean-validation.exist-element}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> domainClass();
}
