package deck;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Card
 */
public class Card {

    private final SuitType suit;
    private final Integer value;
    private final String label;

    private Card(final SuitType suit, final Integer value, final String label) {
        this.suit = suit;
        this.value = value;
        this.label = label;
    }

    /**
     * Builder of Card object, starts a new chain for building a card
     *
     * @return CardBuilder
     */
    public static CardBuilder build() {
        return new CardBuilder();
    }

    public SuitType getSuit() {
        return suit;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    /**
     *  CardBuilder - Builds a new Card
     */
    public static class CardBuilder {

        // λ(Predicate, String) -> λ(CardBuilder) -> String?
        private static Function<CardBuilder, Optional<String>> newRule(Predicate<CardBuilder> p, String msg) {
            return o -> p.test(o)? Optional.<String>empty() : Optional.of(msg);
        }

        private Optional<SuitType> suit;
        private Optional<Integer> value;

        //Rules to validate a Cards arguments
        private List<Function<CardBuilder, Optional<String>>> validationRules = Arrays.asList(
                newRule(cb -> cb.suit.isPresent(), "No suit set"),
                newRule(cb -> cb.value.isPresent(), "No value set"),
                newRule(cb -> {
                    if (!cb.value.isPresent()) {
                        return false;
                    }
                    return cb.value.get() > 0 && cb.value.get() < 14;
                }, "Card value is not valid"));

        protected CardBuilder() {
            this.suit = Optional.empty();
            this.value = Optional.empty();
        }

        public CardBuilder setSuit(final SuitType suit) {
            this.suit = Optional.ofNullable(suit);
            return this;
        }

        public CardBuilder setValue(final Integer value) {
            this.value = Optional.ofNullable(value);
            return this;
        }

        /**
         *  Helper to generate a label on a valid Card
         *
         * @return String - User friendly for a Card
         */
        private String createLabel() {

            String cardName = new HashMap<Integer, String>(){{
                put(1, "ace");
                put(11, "jack");
                put(12, "queen");
                put(13, "king");
            }}.getOrDefault(this.value.get(), this.value.get().toString());

            return String.format("%s of %s", cardName, this.suit.get().toString());
        }

        /**
         * Finalize the CardBuilder and create a Card, runs validation logic to ensure a Card is valid
         *
         * @return Card
         * @throws IllegalStateException if not all valid arguments have been supplied to create a Card
         */
        public Card create() throws IllegalStateException {
            List<String> validationErrors = validationRules.stream()
                    .flatMap(fn -> {
                        Optional<String> msg = fn.apply(this);
                        return msg.isPresent() ? Stream.of(msg.get()) : Stream.empty();
                    }).collect(Collectors.toList());

            if (!validationErrors.isEmpty()) {
                throw new IllegalStateException(String.format("Rules Broken [ %s ]", String.join(", ", validationErrors)));
            }

            return new Card(this.suit.get(), this.value.get(), createLabel());
        }

    }
}
