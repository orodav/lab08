package it.unibo.deathnote.api;

public class DeathNoteImpl implements DeathNote {

    @Override
    public String getRule(int ruleNumber) {
        throw new IllegalArgumentException(
            "the given rule number is smaller than 1 or larger than the number of rules"
            );
    }

    @Override
    public void writeName(String name) {
        throw new NullPointerException("the given name is null");
    }

    @Override
    public boolean writeDeathCause(String cause) {
        throw new IllegalStateException("there is no name written in this DeathNote, or the cause is null");
    }

    @Override
    public boolean writeDetails(String details) {
        throw new IllegalStateException("there is no name written in this DeathNote, or the details are null");
    }

    @Override
    public String getDeathCause(String name) {
        throw new IllegalArgumentException("the provider name is not written in this DeathNote");
    }

    @Override
    public String getDeathDetails(String name) {
        throw new IllegalArgumentException("the provider name is not written in this DeathNote");
    }

    @Override
    public boolean isNameWritten(String name) {
        throw new IllegalArgumentException("this name was already written in this DeathNote");
    }
}
