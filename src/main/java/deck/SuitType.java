package deck;

import java.util.EnumSet;
import java.util.Optional;

public enum SuitType {
    CLUBS("clubs"),
    DIAMONDS("diamonds"),
    HEARTS("hearts"),
    SPADES("spades");

    private final String suit;

    SuitType(final String suit){
        this.suit = suit;
    }

    public static Optional<SuitType> fromString(final String findSuit) {
        return EnumSet.allOf(SuitType.class).stream()
                .filter(s -> s.toString().equalsIgnoreCase(findSuit)).findFirst();
    }

    @Override
    public String toString() {
        return this.suit;
    }
}
