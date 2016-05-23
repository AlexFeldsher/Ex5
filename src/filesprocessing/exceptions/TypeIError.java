package filesprocessing.exceptions;

/**
 * Type I Errors - Warnings
 * 1. A bad FILTER/ORDER name (e.g.greaaaater_than ).
 * These names are also case sensitive
 * (e.g., Size is an illegal order name, and should result in an error)
 * 2. Bad parameters to the hidden/written/executable filters (anything other than YES/NO)
 * 3. Bad parameters to the greater_than/between/smaller_than filters (negative number).
 * 4. Illegal values for the between filter (for example - between#15#7)
 */
public class TypeIError extends Exception {
}
