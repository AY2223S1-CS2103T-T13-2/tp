package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.Model;
import seedu.address.logic.commands.exceptions.CommandException;

public class RemarkCommand extends Command {
    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "r/Likes to swim.";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark command not implemented yet";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Successfully updated remark for Person: %1$s";

    private final Index index;
    private final Remark remark;

    public RemarkCommand(Index index, String remark) {
        requireAllNonNull(index, remark);

        this.index = index;
        this.remark = new Remark(remark);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // return new CommandResult("Hello from remark");
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToRemark = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToRemark, remark);

        if (!personToRemark.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToRemark, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS, editedPerson));
    }

    private static Person createEditedPerson(Person personToRemark, Remark remark) {
        assert personToRemark != null;

        return new Person(personToRemark.getName(), personToRemark.getPhone(), personToRemark.getEmail(),
                personToRemark.getAddress(), personToRemark.getTags(), remark);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RemarkCommand)) {
            return false;
        }
        RemarkCommand otherRemarkCommand = (RemarkCommand) other;
        return index.equals(otherRemarkCommand.index) && remark.equals(otherRemarkCommand.remark);
    }
}
