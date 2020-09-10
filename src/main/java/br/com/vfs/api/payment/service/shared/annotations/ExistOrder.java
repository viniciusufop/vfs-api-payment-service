package br.com.vfs.api.payment.service.shared.annotations;

import br.com.vfs.api.payment.service.shared.validators.ExistOrderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {ExistOrderValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ExistOrder {

    String message() default "{br.com.vfs.api.payment.service.bean-validation.exist-order}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
