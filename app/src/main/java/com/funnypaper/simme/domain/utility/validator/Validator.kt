package com.funnypaper.simme.domain.utility.validator

/**
 * Generic Validator.
 */
fun interface IValidator<T> {
    /**
     * Checks if given input is considered to be valid.
     * @param input Value to be checked.
     * @return Result of validation process.
     */
    fun isValid(input: T): ValidationResult<T>

    /**
     * Combines two validators into one composite validator.
     */
    operator fun plus(other: IValidator<T>) = CompositeValidator(this, other)
}

/**
 * Composite validator. Can be used for chaining various validators of the same generic type.
 */
class CompositeValidator<T>(vararg val validators: IValidator<T>) : IValidator<T> {
    private val _validators = validators.toMutableList()
    override fun isValid(input: T): ValidationResult<T> =
        _validators.fold(ValidationResult(input)) { acc, iValidator ->
            acc + iValidator.isValid(input)
        }

    /**
     * Combines two composite validators content into one.
     */
    operator fun plus(other: CompositeValidator<T>) =
        CompositeValidator(*validators, *other.validators)

    override operator fun plus(other: IValidator<T>) =
        CompositeValidator(*validators, other)
}

/**
 * Result reported after validation process.
 */
data class ValidationResult<T> internal constructor(
    val input: T,
    val isValid: Boolean = true,
    val errors: List<ValidationError> = emptyList(),
) {
    /**
     * Returns validation result containing both error list and set isValid to boolean and.
     */
    operator fun plus(other: ValidationResult<T>): ValidationResult<T> =
        copy(
            isValid = isValid && other.isValid,
            errors = errors + other.errors
        )
}

/**
 * Constructs validation result with given input and isValid parameters.
 * Error list contains error only if isValid equates to false.
 */
fun <T> validationResult(input: T, isValid: Boolean, error: ValidationError) =
    validationResult(input, isValid, listOf(error))

/**
 * Constructs validation result with given input and isValid parameters.
 * Error list contains errors only if isValid equates to false.
 */
fun <T> validationResult(input: T, isValid: Boolean, errors: List<ValidationError>) =
    ValidationResult(
        input = input,
        isValid = isValid,
        errors = if (isValid) emptyList() else errors
    )

/**
 * Validates value on the left with provided validators.
 * @param validators Validators that will be used in validation process.
 */
infix fun <T> T.validateWith(validators: Collection<IValidator<T>>): ValidationResult<T> =
    validateWith(CompositeValidator(validators = validators.toTypedArray()))

/**
 * Validates value on the left with provided validator.
 * @param validator Validator used in validation process.
 */
infix fun <T> T.validateWith(validator: IValidator<T>): ValidationResult<T> =
    validator.isValid(this)

/**
 * Error found during validation process.
 */
data class ValidationError(val message: String)