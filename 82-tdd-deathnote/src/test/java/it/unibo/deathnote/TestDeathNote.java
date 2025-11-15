package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.*;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.DeathNoteImpl;

class TestDeathNote {

    private static final String LIGHT = "Ligh Yagami";
    private static final int EXP_CAUSE = 100;
    private static final int EXP_DETAILS = 6100;
    private final DeathNote myNote = new DeathNoteImpl();

    void testIllegalRule() {

        //rule zero
        final IllegalArgumentException ruleZero = assertThrows(
            IllegalArgumentException.class, () -> myNote.getRule(0)
            );
            assertNotNull(ruleZero.getMessage());
            assertFalse(ruleZero.getMessage().isBlank());

        //rule negative
        final IllegalArgumentException ruleNegative = assertThrows(
            IllegalArgumentException.class, () -> myNote.getRule(-1)
            );
            assertNotNull(ruleNegative.getMessage());
            assertFalse(ruleNegative.getMessage().isBlank());
    }

    void testValidName() {
        final DeathNote myNote = new DeathNoteImpl();
        //name is not already written in the DeathNote
        assertFalse(myNote.isNameWritten(LIGHT));
        assertFalse(myNote.isNameWritten("L"));
        assertFalse(myNote.isNameWritten(""));      //check for empty name
        //write a valid name
        myNote.writeName(LIGHT);
        //checking if name was written in the DeathNote
        assertTrue(myNote.isNameWritten(LIGHT));
        //checking that other humans were not written in the DeathNote
        assertFalse(myNote.isNameWritten("L"));
        assertFalse(myNote.isNameWritten(""));
    }
    
    void testDeathCause() {
        final DeathNote myNote = new DeathNoteImpl();
        //making sure that a name is written before the death cause
        final IllegalStateException exception = assertThrows(
            IllegalStateException.class, () -> myNote.writeDeathCause("heart attack")
            );
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isBlank());
        //writing names and causes
        myNote.writeName(LIGHT);
        assertEquals("heart attack", myNote.getDeathCause(LIGHT));
        myNote.writeName("L");
        assertEquals("karting accident", myNote.getDeathCause("L"));
        //putting thread to sleep for an ammount of time that won't let me modify cause of death
        try {  
        Thread.sleep(EXP_CAUSE);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
        //trying to modify death cause and testing if it does not change
        assertFalse(myNote.writeDeathCause("ate shinigami's apple"));
        assertEquals("karting accident", myNote.getDeathCause("L"));
    }

    void testDeathSpecific() {
        final DeathNote myNote = new DeathNoteImpl();
        //making sure that a name is written before death details
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> myNote.writeDetails("")
            );
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isBlank());
        //writing a name on the book
        myNote.writeName(LIGHT);
        //check if there are no details yet
        assertEquals("", myNote.getDeathDetails(LIGHT));
        //writing details
        myNote.writeDetails("ran for too long");
        assertEquals("ran for too long", myNote.getDeathDetails(LIGHT));
        //writing another name
        myNote.writeName("L");
        try {  
        Thread.sleep(EXP_DETAILS);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
        myNote.writeDetails("ate shinigami's apple");
        assertEquals("", myNote.getDeathDetails("L"));

    }
}