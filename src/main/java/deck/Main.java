package deck;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Main {

    protected static List<Card> deal(Deck deck, Integer amount) {
        return IntStream.range(0, amount)
                .mapToObj(i -> deck.dealOneCard())
                .filter(cardOptional -> cardOptional.isPresent())
                .map(cardOptional -> cardOptional.get()).collect(Collectors.toList());
    }



    public static void main(String[] args) {
        Deck deck = Deck.newDeck();
        deck.shuffle();

        List<Card> hand1 = deal(deck, 5);
        List<Card> hand2 = deal(deck, 5);

        System.out.println("-- Hand 1 --");
        hand1.forEach(card -> System.out.println(card.getLabel()));

        System.out.println("-- Hand 2 --");
        hand2.forEach(card -> System.out.println(card.getLabel()));
    }
}