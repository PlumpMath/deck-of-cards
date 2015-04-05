package deck;


import org.junit.Test;
import static org.junit.Assert.*;

public class SuitTypeTest {

    @Test
    public void getSuitFromString(){
        assertTrue(SuitType.fromString("Hearts").get().equals(SuitType.HEARTS));
        assertTrue(SuitType.fromString("Clubs").get().equals(SuitType.CLUBS));
        assertTrue(SuitType.fromString("Spades").get().equals(SuitType.SPADES));
        assertTrue(SuitType.fromString("Diamonds").get().equals(SuitType.DIAMONDS));

        //invalid suit
        assertFalse(SuitType.fromString("test").isPresent());
    }
}
