package com.funnypaper.simme.domain.utility.validator

/**
 * Used for validating dates in string.
 */
class TimeValidator(
    regex: Regex,
    pattern: String = regex.pattern,
    patternTip: String = "",
) : IValidator<String> by
// Delegate implementation to CompositeValidator<T>
// This composite validator consist of regex and empty string validators
RegexValidator(regex, pattern, patternTip) + EmptyStringValidator()