package Blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>();
    }

    public void createFullDeck() {
        for (Suits suit : Suits.values()) {
            for (Values value : Values.values()) {
                this.deck.add(new Card(suit, value));
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public Card getCard(int i) {
        return this.deck.get(i);
    }

    public void removeCard(int i) {
        this.deck.remove(i);
    }

    public void addCard(Card addCard) {
        this.deck.add(addCard);
    }

    public int deckSize() {
        return this.deck.size();
    }

    public void draw(Deck comingFrom) {
        this.deck.add(comingFrom.getCard(0));
        comingFrom.removeCard(0);
    }

    public String getCardsAsString() {
        String cardsAsString = "";
        for (Card card : this.deck) {
            cardsAsString += card.toString() + "\n";
        }
        return cardsAsString;
    }


    public void moveAllToDeck(Deck moveTo) {
        int deckSize = this.deck.size();

        // put cards into moveTo deck
        for (int i = 0; i < deckSize; i++) {
            moveTo.addCard(this.getCard(i));
        }

        // remove cards from current deck
        for (int i = 0; i < deckSize; i++) {
            this.removeCard(0);
        }
    }
}

