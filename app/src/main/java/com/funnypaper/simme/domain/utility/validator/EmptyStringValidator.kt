package com.funnypaper.simme.domain.utility.validator

/**
 * Checks if provided input is empty.
 */
class EmptyStringValidator : IValidator<String> {
    override fun isValid(input: String): ValidationResult<String> =
        validationResult(
            input = input,
            isValid = input.isNotEmpty(),
            error = ValidationError("Input value should not be empty.")
        )
}