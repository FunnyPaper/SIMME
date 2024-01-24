package com.funnypaper.simme.domain.utility.format

/**
 * @param regex Regex used for matching time in string
 * @param placeholder Default value that can be used as initial string state
 * @param pattern Pattern that can be used by date formatters
 * @param patternTip Human readable explanation of date formatter pattern
 */
enum class TimeFormat(
    val regex: Regex,
    val placeholder: String,
    val pattern: String,
    val patternTip: String,
) {
    /**
     * Checks for hours, minutes, seconds and milliseconds (all required, hours are limited to 0-23 range)
     */
    Full(
        regex = Regex("""^(([0-1]\d)|(2[0-3])):([0-5]\d):([0-5]\d)\.(\d{3})$"""),
        placeholder = "00:00:00.000",
        pattern = "HH:mm:ss.SSS",
        patternTip = "Correct time range is 00:00:00.000-23:59:59.999"
    ),

    /**
     * Checks for seconds and milliseconds (all required)
     */
    SecondsMilliseconds(
        regex = Regex("""([0-5]\d)\.(\d{3})"""),
        placeholder = "00.000",
        pattern = "ss.SSS",
        patternTip = "Correct time range is 00.000-59.999"
    ),

    /**
     * Checks for minutes and seconds (all required)
     */
    MinutesSeconds(
        regex = Regex("""^([0-5]\d):([0-5]\d)$"""),
        placeholder = "00:00",
        pattern = "mm:ss",
        patternTip = "Correct time range is 00:00-59:59"
    ),

    /**
     * Checks for hours, minutes and seconds (all required, hours are limited to 0-23 range)
     */
    HoursMinutesSeconds(
        regex = Regex("""^(([0-1]\d)|(2[0-3])):([0-5]\d):([0-5]\d)$"""),
        placeholder = "00:00:00",
        pattern = "HH:mm:ss",
        patternTip = "Correct time range is 00:00:00-23:59:59"
    );
}