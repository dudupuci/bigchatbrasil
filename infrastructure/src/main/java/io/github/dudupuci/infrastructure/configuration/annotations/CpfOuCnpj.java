package io.github.dudupuci.infrastructure.configuration.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CpfOuCnpjValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfOuCnpj {
    String message() default "CPF ou CNPJ inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
