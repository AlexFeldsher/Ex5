package filesprocessing.exceptions;

/**
 * Type I Error<br>
 * 1. A bad FILTER/ORDER name (e.g., greaaater_than).<br>
 * 2. Bad parameters to the hidden/writable/executable filters (anything other than YES/NO).<br>
 * 3. Bad parameters to the greater_than/between/smaller_than filters (negative number).<br>
 * 4. Illegal values for the between filter (for example - between#15#7).<br>
 */
public class BadFilterParameterException extends Exception {
}
