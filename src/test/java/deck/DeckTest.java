package deck;


import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeckTest {

    // (Suit) -> (Card) -> Boolean
    private static Function<SuitType, Predicate<Card>> filterBySuit = suitType -> card -> card.getSuit().equals(suitType);

    @Test
    public void testDeal(){
        final Deck deck = Deck.newDeck();
        deck.shuffle();
        final List<Card> hand1 = Main.deal(deck, 13);
        final List<Card> hand2 = Main.deal(deck, 13);
        final List<Card> hand3 = Main.deal(deck, 13);
        final List<Card> hand4 = Main.deal(deck, 13);

        assertTrue("deck is empty", !deck.dealOneCard().isPresent());

    }

    @Test
    public void testUnshuffled(){
        final Deck deck = Deck.newDeck();
        List<Card> hand1 = Main.deal(deck, 5);

        final Deck deck2 = Deck.newDeck();
        List<Card> hand2 = Main.deal(deck2, 5);

        assertTrue("unshuffled hands should match", Stream.concat(hand1.stream(), hand2.stream()).distinct().count() == 5);
    }

    @Test
    public void testShuffle() {
        final Deck deck2 = Deck.newDeck();
        List<Card> hand2 = Main.deal(deck2, 5);

        final Deck deck3 = Deck.newDeck();
        deck3.shuffle();

        List<Card> hand3 = Main.deal(deck3, 5);
        assertTrue("shuffled hands should 'probably' not match", Stream.concat(hand2.stream(), hand3.stream()).distinct().count() > 5);
    }

    @Test
    public void testReShuffle(){
        final Deck deck1 = Deck.newDeck();
        deck1.shuffle();

        List<Card> hand1 = Main.deal(deck1, 10);
        assertTrue("hand1 has 10 cards", hand1.size() == 10);

        //reshuffle
        deck1.shuffle();
        List<Card> hand2 = Main.deal(deck1, 52);
        assertTrue("hand2 has 42 cards", hand2.size() == 42);


        final Set<String> cardsSet = Stream.concat(hand1.stream(), hand2.stream())
                .map(card -> card.getLabel()).collect(Collectors.toSet());
        assertTrue("all cards are unique after shuffle", cardsSet.size() == 52);

    }

    @Test
    public void sanityCheck(){

        final Deck deck = Deck.newDeck();
        deck.shuffle();

        final List<Card> allCards = Main.deal(deck, 55);
        assertTrue("handsize 52", allCards.size() == 52);

        final Set<String> cardsSet = allCards.stream().map(card -> card.getLabel()).collect(Collectors.toSet());
        assertTrue("all cards are unique 52", cardsSet.size() == 52);

        final List<Card> _10s = allCards.stream().filter(c -> c.getValue() == 10).collect(Collectors.toList());
        assertTrue("there are 4 10s", _10s.size() == 4);

        assertTrue("13 clubs ", allCards.stream().filter(filterBySuit.apply(SuitType.CLUBS)).count() == 13);
        assertTrue("13 diamonds ", allCards.stream().filter(filterBySuit.apply(SuitType.DIAMONDS)).count() == 13);
        assertTrue("13 spades ", allCards.stream().filter(filterBySuit.apply(SuitType.SPADES)).count() == 13);
        assertTrue("13 hearts ", allCards.stream().filter(filterBySuit.apply(SuitType.HEARTS)).count() == 13);

    }

}
