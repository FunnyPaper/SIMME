package com.funnypaper.simme.domain.utility.validator

/**
 * Common base for string validation with regex.
 */
open class RegexValidator(
    val regex: Regex,
    val pattern: String = regex.pattern,
    val patternTip: String = "",
) : IValidator<String> {
    override fun isValid(input: String): ValidationResult<String> =
        validationResult(
            input = input,
            isValid = regex.matches(input),
            error = ValidationError("Required pattern: $pattern.\n$patternTip")
        )
}