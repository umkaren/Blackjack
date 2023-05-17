package Blackjack;

public class Card {

    private Suits suit;
    private Values value;

    public Card(Suits suit, Values value) {
        this.value = value;
        this.suit = suit;
    }

    public String toString() {
        return this.suit.toString() + "-" + this.value.toString();
    }

    public Values getValue() {
        return this.value;
    }
}


