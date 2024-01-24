package com.funnypaper.simme.domain.utility.validator

/**
 * Common base for string validation with regex.
 */
open class RegexValidator<T>(
    val regex: Regex,
    val pattern: String = regex.pattern,
    val patternTip: String = "",
) : IValidator<T> {
    override fun isValid(input: T): ValidationResult<T> =
        validationResult(
            input = input,
            isValid = regex.matches(input.toString()),
            error = ValidationError("Required pattern: $pattern.\n$patternTip")
        )
}