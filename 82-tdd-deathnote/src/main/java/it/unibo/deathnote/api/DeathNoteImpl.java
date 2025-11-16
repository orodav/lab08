package it.unibo.deathnote.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the DeathNote interface.
 * Stores written names and death information,
 * and allows modifying data only within specific time windows.
 */
public final class DeathNoteImpl implements DeathNote {

    // Time windows for writing cause and details of death
    private static final long CAUSE_TIME_WINDOW = 40;
    private static final long DETAILS_TIME_WINDOW = 6040;
    // Tracks when name is written
    private final Map<String, Long> nameTimestamps = new HashMap<>();
    // Maps names to corresponding cause of death
    private final Map<String, String> deathCauses = new HashMap<>();
    // Maps names to cooresponding death details
    private final Map<String, String> deathDetails = new HashMap<>();

    // Check if ruleNumber is < 1 or bigger than rule list
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Invalid rule number: " + ruleNumber);
        }
        return RULES.get(ruleNumber - 1);
    }

    // Writing a name on the note
    @Override
    public void writeName(final String name) {
        if (name == null) {
            throw new IllegalStateException("The given name is null");
        }
        // Saving name if not already written
        nameTimestamps.put(name, System.currentTimeMillis());
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (cause == null) {
            throw new IllegalStateException("The cause is null");
        }

        // Check if a name was already written
        final String lastNameWritten = getLastWrittenName();
        if (lastNameWritten == null) {
            throw new IllegalStateException("There is no name written in this DeathNote");
        }

        final long writeTime = nameTimestamps.get(lastNameWritten);
        final long currentTime = System.currentTimeMillis();

        // Write cause only if within time window
        if (!deathCauses.containsKey(lastNameWritten)
                && currentTime - writeTime <= CAUSE_TIME_WINDOW) {
            deathCauses.put(lastNameWritten, cause);
            return true;
        }
        return false; // Too late to set cause of death
    }

    @Override
    public boolean writeDetails(final String details) {
        if (details == null) {
            throw new IllegalStateException("The details are null");
        }

        // Check if a name was already written
        final String lastNameWritten = getLastWrittenName();
        if (lastNameWritten == null) {
            throw new IllegalStateException("There is no name written in this DeathNote");
        }

        final long writeTime = nameTimestamps.get(lastNameWritten);
        final long currentTime = System.currentTimeMillis();

        // Allow writing details only if within the time window
        if (!deathDetails.containsKey(lastNameWritten)
                && currentTime - writeTime <= DETAILS_TIME_WINDOW) {
            deathDetails.put(lastNameWritten, details);
            return true;
        }

        return false; // Too late to add details
    }

    @Override
    public String getDeathCause(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException("The name '" + name + "' is not written in this DeathNote");
        }
        // Set default cause to "heart attack" if not specified
        return deathCauses.getOrDefault(name, "heart attack");
    }

    @Override
    public String getDeathDetails(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException("The name '" + name + "' is not written in this DeathNote");
        }
        // Return default details
        return deathDetails.getOrDefault(name, "");
    }

    @Override
    public boolean isNameWritten(final String name) {
        return nameTimestamps.containsKey(name);
    }

    /**
     * @return the most recently written name, or null if none exists.
     */
    private String getLastWrittenName() {
        return nameTimestamps.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
}
