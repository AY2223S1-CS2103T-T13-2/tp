package seedu.address.model.person;

/**
 * Represents the remarks of a Person in the address book.
 */
public class Remark {
    public static final String MESSAGE_CONSTRAINTS = "Remarks can take any values, and it can be blank";
    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark The remark.
     */
    public Remark(String remark) {
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Remark)
                && value.equals(((Remark) other).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
