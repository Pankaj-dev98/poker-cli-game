package org.orgless.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Card implements Comparable<Card> {
    public enum Suit {
        CLUB, DIAMOND, HEART, SPADE;

        public char getAscii() {
            return switch(this) {
                case CLUB ->    '\u2663'; // ♣
                case DIAMOND -> '\u2666'; // ♦
                case HEART ->   '\u2665'; // ♥
                case SPADE ->   '\u2660'; // ♠
            };
        }

    }

    public static Comparator<Card> sortRankReversedSuit() {
        return Comparator.comparing(Card::getRank).reversed()
                .thenComparing(Card::getSuit);
    }

    private Card(Suit suit, int rank) {
        this.suit = suit;

        if(rank < 0 || rank > 12)
            throw new IllegalArgumentException("Rank must be between 0 and 12");

        this.rank = rank;

        this.face = setFace();
    }

    public static Card getNumericCard(Suit suit, int num) {
        if(num < 2 || num > 10)
            throw new IllegalArgumentException("Valid numerics cards are between 2-10");

        return new Card(suit, num-2);
    }

    public static Card getFaceCard(Suit suit, char ch) {
        if(ch != 'K' && ch != 'Q' && ch != 'J' && ch != 'A')
            throw new IllegalArgumentException("Valid faces are K, Q, J and A");

        if(ch == 'K')
            return new Card(suit, 11);
        else if(ch == 'Q')
            return new Card(suit, 10);
        else if(ch == 'J')
            return new Card(suit, 9);
            // Card must be an ACE
        else
            return new Card(suit, 12);
    }

    private final int rank;
    public int getRank() {
        return rank;
    }

    // Face contains either the number of the card, or the face value of the card,
    // Jack, Queen, King or Ace
    private final Suit suit;
    public Suit getSuit() {
        return suit;
    }

    private final String face;

    public String getFace() {
        return face;
    }

    private String setFace() {
        if(rank < 9)
            return String.valueOf(rank + 2);
        else if(rank == 9)
            return "J";
        else if(rank == 10)
            return "Q";
        else if(rank == 11)
            return "K";
        else if(rank == 12)
            return "A";
        else
            return null;
    }

    public static List<Card> getStandardDeck() {
        List<Card> cards = new ArrayList<>(52);

        Suit[] suits = Suit.values();
        for(int i = suits.length - 1; i >= 0; i--) {
            for(int j = 0; j <= 12; j++) {
                Card currentCard = new Card(suits[i], j);
                cards.add(currentCard);
            }
        }
        return cards;
    }

    public static void printDeck(List<Card> currentDeck) {
        printDeck(currentDeck, "Current Deck", 4);
    }

    public static void printDeck(List<Card> deck, String description, int rows) {
        if(rows > deck.size())
            rows = deck.size();
        System.out.println("-".repeat(30));

        if(description != null)
            System.out.println(description);

        int cardsInRow = deck.size() / rows;
        for(int i = 0; i < rows; i++) {
            int startIndex = i * cardsInRow;
            int endIndex = startIndex + cardsInRow;

            deck.subList(startIndex, endIndex)
                    .forEach(c -> System.out.print(c + " "));
            System.out.println();
        }
    }



    @Override
    public int compareTo(Card c) {
        if(c == null)
            throw new IllegalArgumentException("Compared card is null");

        if(rank == c.rank)
            return suit.name().compareTo(c.suit.name());
        return rank - c.rank;
    }

    @Override
    public String toString() {
        return "%s%c(%d)".formatted(face, suit.getAscii(), rank);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof Card card))
            return false;
        return rank == card.rank && suit == card.suit;
    }
}
