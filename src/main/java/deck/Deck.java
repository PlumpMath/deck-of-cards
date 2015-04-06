package deck;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *  Deck of Cards
 */

public class Deck {

    //SuitBuilder
    private static final Function<SuitType, List<Card>> makeSuit =
            suit -> IntStream.range(1, 14).<Card>mapToObj(
                    i -> Card.build()
                            .setValue(i)
                            .setSuit(suit)
                            .create()).collect(Collectors.toList());

    //Cards by suit
    private static final List<Card> clubs = makeSuit.apply(SuitType.CLUBS);
    private static final List<Card> diamonds = makeSuit.apply(SuitType.DIAMONDS);
    private static final List<Card> hearts = makeSuit.apply(SuitType.HEARTS);
    private static final List<Card> spades = makeSuit.apply(SuitType.SPADES);

    //unshuffled deck
    private static final List<Card> fullDeck =
            Stream.concat(clubs.stream(),
                    Stream.concat(diamonds.stream(),
                            Stream.concat(hearts.stream(), spades.stream()))).collect(Collectors.toList());

    //Pair/Tuple utility class
    private class Pair<T, U> {
        public final T _1;
        public final U _2;

        public Pair(T t, U u) {
            this._1 = t;
            this._2 = u;
        }
    }

    private Iterator<Card> deckItr;

    protected Deck(final List<Card> cards) {
        this.deckItr = cards.stream().iterator();
    }

    /**
     * Shuffle the deck at current state of cards
     */
    public void shuffle() {
        final Random random = new Random();

        // read out the rest of the cards
        List<Card> restCards = new ArrayList<>();
        Optional<Card> nextCard = this.dealOneCard();

        while(nextCard.isPresent()) {
            restCards.add(nextCard.get());
            nextCard = this.dealOneCard();
        }

        // shuffle the cards that are left
        List<Card> shuffledCards = restCards.stream()
                .map(card -> new Pair<Double, Card>(random.nextDouble(), card))
                .sorted((a, b) -> a._1.compareTo(b._1))
                .map(p -> p._2).collect(Collectors.toList());

        //unsafe swap iterator
        this.deckItr = shuffledCards.stream().iterator();
    }

    /**
     * deal a card, if no cards are left Empty will be returned
     *
     * @return Optional of Card
     */
    public Optional<Card> dealOneCard() {
        if (this.deckItr.hasNext()) {
            return Optional.of(this.deckItr.next());
        }
        return Optional.empty();
    }

    /**
     * Create a new Deck, this deck is Unshuffled by default
     *
     * @return Deck - new Deck of 52 cards
     */
    public static Deck newDeck() {
        return new Deck(fullDeck.stream().map(e -> e).collect(Collectors.toList()));
    }
}
