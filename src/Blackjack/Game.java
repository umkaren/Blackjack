package Blackjack;

import java.util.Scanner;

public class Game {
    private Deck deck;
    private Deck playerHand;
    private Deck dealerHand;
    private int playerBalance;
    private boolean isInitialBalanceSet;

    public Game() {
        this.deck = new Deck();
        this.playerHand = new Deck();
        this.dealerHand = new Deck();
        this.playerBalance = 0; // Default balance
        this.isInitialBalanceSet = false;
    }

    public void setInitialBalance() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your initial balance:");
        this.playerBalance = scanner.nextInt();
        this.isInitialBalanceSet = true;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Blackjack!");

            if (!isInitialBalanceSet) {
                setInitialBalance();
            }

            System.out.println("Your balance: $" + playerBalance);

            System.out.println("Place your bet (minimum $5, must be a multiple of 5):");
            int bet = scanner.nextInt();

            if (bet < 1 || bet > playerBalance || bet % 5 != 0) {
                System.out.println("Invalid bet amount. Please try again.");
                continue;
            }

            // Deal initial cards
            deck.createFullDeck();
            deck.shuffleDeck();

            playerHand.moveAllToDeck(deck);
            dealerHand.moveAllToDeck(deck);
            playerHand.draw(deck);
            dealerHand.draw(deck);
            playerHand.draw(deck);

            // Play the game
            boolean playerBusted = false;
            boolean dealerBusted = false;

            while (true) {
                System.out.println("Your hand: " + playerHand.getCardsAsString());
                System.out.println("Dealer hand: " + dealerHand.getCard(0).toString() + " + [Hidden]");

                System.out.println("Do you want to Hit (H) or Stand (S)?");
                String choice = scanner.next();

                if (choice.equalsIgnoreCase("H") || choice.equalsIgnoreCase("Hit")) {
                    playerHand.draw(deck);
                    if (getHandValue(playerHand) > 21) {
                        playerBusted = true;
                        break;
                    }
                } else if (choice.equalsIgnoreCase("S") || choice.equalsIgnoreCase("Stand")) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

            // Dealer's turn
            while (getHandValue(dealerHand) < 17) {
                dealerHand.draw(deck);
                if (getHandValue(dealerHand) > 21) {
                    dealerBusted = true;
                    break;
                }
            }

            // Determine the winner
            System.out.println("Your hand: " + playerHand.getCardsAsString());
            System.out.println("Your hand total: " + getHandValue(playerHand));
            System.out.println("Dealer hand: " + dealerHand.getCardsAsString());
            System.out.println("Dealer hand total: " + getHandValue(dealerHand));

            int playerValue = getHandValue(playerHand);
            int dealerValue = getHandValue(dealerHand);

            if (playerBusted) {
                System.out.println("You busted! You lose $" + bet);
                playerBalance -= bet;
            } else if (dealerBusted) {
                System.out.println("Dealer busted! You win $" + bet);
                playerBalance += bet;
            } else if (playerValue > dealerValue) {
                System.out.println("You win $" + bet);
                playerBalance += bet;
            } else if (playerValue < dealerValue) {
                System.out.println("You lose $" + bet);
                playerBalance -= bet;
            } else {
                System.out.println("It's a push! Your bet has been refunded.");
            }

            // Check if the player has enough balance to continue playing
            if (playerBalance < 1) {
                System.out.println("You have no money left. It's game over!");
                break;
            }

            // Reset hands for the next round
            playerHand.moveAllToDeck(deck);
            dealerHand.moveAllToDeck(deck);

            System.out.println("Your current balance is: $" + playerBalance);
            System.out.println("Do you want to play another round? (Y/N)");
            String choice = scanner.next();

            if (choice.equalsIgnoreCase("N") || choice.equalsIgnoreCase("No")) {
                System.out.println("Thanks for playing! You walk away with $" + playerBalance);
                break;
            }
        }
    }

    private int getHandValue(Deck hand) {
        int value = 0;
        int numAces = 0;

        for (int i = 0; i < hand.deckSize(); i++) {
            Card card = hand.getCard(i);
            if (card.getValue() == Values.ACE) {
                numAces++;
            } else if (card.getValue() == Values.TWO) {
                value += 2;
            } else if (card.getValue() == Values.THREE) {
                value += 3;
            } else if (card.getValue() == Values.FOUR) {
                value += 4;
            } else if (card.getValue() == Values.FIVE) {
                value += 5;
            } else if (card.getValue() == Values.SIX) {
                value += 6;
            } else if (card.getValue() == Values.SEVEN) {
                value += 7;
            } else if (card.getValue() == Values.EIGHT) {
                value += 8;
            } else if (card.getValue() == Values.NINE) {
                value += 9;
            } else if (card.getValue() == Values.TEN || card.getValue() == Values.JACK ||
                    card.getValue() == Values.QUEEN || card.getValue() == Values.KING) {
                value += 10;
            }
        }

        // Calculate the value of Aces
        for (int i = 0; i < numAces; i++) {
            if (value + 11 <= 21) {
                value += 11;
            } else {
                value += 1;
            }
        }

        return value;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}
