package deck;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CardTest {

    private final String illegalStateSuit = "No suit set";
    private final String illegalStateValue = "No value set";
    private final String illegalStateValueNotSet = "Card value is not valid";

    @Test
    public void illegalStates(){
        try {
            Card.build().create();
            fail("should throw illegal state");
        } catch (IllegalStateException e) {
            if (!Arrays.asList(illegalStateSuit, illegalStateValue, illegalStateValueNotSet)
                    .stream().allMatch(s -> e.getMessage().contains(s)))
            {
                fail("missing state exception");
            }
        }
    }

    @Test
    public void validCard (){
        Card card = Card.build().setSuit(SuitType.CLUBS).setValue(1).create();

        assertTrue("Suit is clubs", card.getSuit().equals(SuitType.CLUBS));
        assertTrue("Card value is 1", card.getValue().equals(1));
        assertTrue("Label is 'ace of clubs'", card.getLabel().equalsIgnoreCase("ace of clubs"));
    }

}
