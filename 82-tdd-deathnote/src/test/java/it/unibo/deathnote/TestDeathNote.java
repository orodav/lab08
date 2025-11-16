package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.DeathNoteImpl;

class TestDeathNote {

    private static final String LIGHT = "Ligh Yagami";
    private static final String L = "L. Lawliet";
    private static final int EXP_CAUSE = 100;
    private static final int EXP_DETAILS = 6100;
    private final DeathNote deathNote = new DeathNoteImpl();

    void testIllegalRule() {
        //rule zero
        final IllegalArgumentException ruleZero = assertThrows(
            IllegalArgumentException.class, () -> deathNote.getRule(0)
            );
            assertNotNull(ruleZero.getMessage());
            assertFalse(ruleZero.getMessage().isBlank());

        //rule negative
        final IllegalArgumentException ruleNegative = assertThrows(
            IllegalArgumentException.class, () -> deathNote.getRule(-1)
            );
            assertNotNull(ruleNegative.getMessage());
            assertFalse(ruleNegative.getMessage().isBlank());
    }

    void testValidName() {
        //name is not already written in the DeathNote
        assertFalse(deathNote.isNameWritten(LIGHT));
        assertFalse(deathNote.isNameWritten(L));
        assertFalse(deathNote.isNameWritten(""));      //check for empty name
        //write a valid name
        deathNote.writeName(LIGHT);
        //checking if name was written in the DeathNote
        assertTrue(deathNote.isNameWritten(LIGHT));
        //checking that other humans were not written in the DeathNote
        assertFalse(deathNote.isNameWritten(L));
        assertFalse(deathNote.isNameWritten(""));
    }

    void testDeathCause() {
        //making sure that a name is written before the death cause
        final IllegalStateException exception = assertThrows(
            IllegalStateException.class, () -> deathNote.writeDeathCause("heart attack")
            );
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isBlank());
        //writing names and causes
        deathNote.writeName(LIGHT);
        assertEquals("heart attack", deathNote.getDeathCause(LIGHT));
        deathNote.writeName(L);
        assertEquals("karting accident", deathNote.getDeathCause(L));
        // Putting thread to sleep to unable modifying cause
        try {
            Thread.sleep(EXP_CAUSE);
        } catch (final InterruptedException e) {
            throw new IllegalStateException("cause no more specifiable due to too much time has passed", e);
        }
        //trying to modify death cause and testing if it does not change
        assertFalse(deathNote.writeDeathCause("ate shinigami's apple"));
        assertEquals("karting accident", deathNote.getDeathCause(L));
    }

    void testDeathSpecified() {
        //making sure that a name is written before death details
        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> deathNote.writeDetails("")
            );
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isBlank());
        //writing a name on the book
        deathNote.writeName(LIGHT);
        //check if there are no details yet
        assertEquals("", deathNote.getDeathDetails(LIGHT));
        //writing details
        deathNote.writeDetails("ran for too long");
        assertEquals("ran for too long", deathNote.getDeathDetails(LIGHT));
        //writing another name
        deathNote.writeName(L);
        try {
        Thread.sleep(EXP_DETAILS);
        } catch (final InterruptedException e) {
            throw new IllegalStateException("details no more specifiable due to too much time has passed", e);
        }
        deathNote.writeDetails("ate shinigami's apple");
        assertEquals("", deathNote.getDeathDetails(L));
    }
}
