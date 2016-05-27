package filesprocessing.exceptions;

/**
 * Type I Error
 * 1. A bad FILTER/ORDER name (e.g., greaaater_than).
 * 2. Bad parameters to the hidden/writable/executable filters (anything other than YES/NO).
 * 3. Bad parameters to the greater_than/between/smaller_than filters (negative number).
 * 4. Illegal values for the between filter (for example - between#15#7).
 */
public class BadFilterParameterException extends Exception {
}
