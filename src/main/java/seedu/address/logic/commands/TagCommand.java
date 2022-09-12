package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags the person with the given tags. "
            + "Parameters: "
            + "[TAG1] [TAG2] ... \n"
            + "Example: " + COMMAND_WORD + " 1 friend colleague";

    public static final String MESSAGE_SUCCESS = "New tags added: %1$s";

    private final Index index;
    private final Set<Tag> tagsToAdd;

    public TagCommand(Index index, Set<Tag> tagsToAdd) {
        requireNonNull(index);
        requireNonNull(tagsToAdd);
        this.index = index;
        this.tagsToAdd = tagsToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToTag = lastShownList.get(index.getZeroBased());
        Set<Tag> tags = new HashSet<>();
        tags.addAll(personToTag.getTags());
        tags.addAll(tagsToAdd);
        Person taggedPerson = new Person(personToTag.getName(), personToTag.getPhone(),
                personToTag.getEmail(), personToTag.getAddress(), tags);

        model.setPerson(personToTag, taggedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String tagsToAddStringRepresentation = tagsToAdd.stream().reduce(
                "",
                (tagStrings, tag) -> tagStrings + ", " + tag.tagName,
                (x, y) -> x + y);
        // Remove unneeded preceding comma
        tagsToAddStringRepresentation = tagsToAddStringRepresentation.substring(1);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tagsToAddStringRepresentation));
    }
}
