package io.github.dudupuci.infrastructure.configuration.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfOuCnpjValidator implements ConstraintValidator<CpfOuCnpj, String> {


    @Override
    public void initialize(CpfOuCnpj constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        String cleanValue = value.replaceAll("[.\\-/]", "");

        // Verifica se é CPF válido (11 dígitos)
        if (cleanValue.matches("^\\d{11}$")) {
            return isValidCpf(cleanValue);
        }

        // Verifica se é CNPJ válido (14 dígitos)
        if (cleanValue.matches("^\\d{14}$")) {
            return isValidCnpj(cleanValue);
        }

        return false;
    }

    private boolean isValidCpf(String cpf) {
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Valida primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) {
            firstDigit = 0;
        }
        if (firstDigit != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        // Valida segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) {
            secondDigit = 0;
        }
        return secondDigit == Character.getNumericValue(cpf.charAt(10));
    }

    private boolean isValidCnpj(String cnpj) {
        // Verifica se todos os dígitos são iguais
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Valida primeiro dígito verificador
        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights1[i];
        }
        int firstDigit = sum % 11;
        firstDigit = firstDigit < 2 ? 0 : 11 - firstDigit;
        if (firstDigit != Character.getNumericValue(cnpj.charAt(12))) {
            return false;
        }

        // Valida segundo dígito verificador
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights2[i];
        }
        int secondDigit = sum % 11;
        secondDigit = secondDigit < 2 ? 0 : 11 - secondDigit;
        return secondDigit == Character.getNumericValue(cnpj.charAt(13));
    }
}

