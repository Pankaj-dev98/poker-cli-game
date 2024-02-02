package org.orgless.game.poker;

import org.orgless.game.Card;

import java.util.*;
import java.util.function.Consumer;

// Each poker game has a player count (the number of players playing), as well as field 'cardsInHand' because different poker
// games are played with a different number of cards.

// Here we assume the poker games will always be played with draw.
public class PokerGame {
    private final List<Card> deck = Card.getStandardDeck();

    private int playerCount;
    private int cardsInHand;
    private List<PokerHand> pokerHands;
    private List<Card> remainingCards;

    public PokerGame(int playerCount, int cardsInHand) {
        this.playerCount = playerCount;
        this.cardsInHand = cardsInHand;
        pokerHands = new ArrayList<>(cardsInHand);
    }

    public void startPlay() {
        Collections.shuffle(deck);
        Card.printDeck(deck);

        // When we cut the deck, we cut the deck somewhere around the middle and place the top part below the bottom part
        Collections.rotate(deck, new Random().nextInt(15, 35) * -1);
        Card.printDeck(deck);

        deal();
        System.out.println("-".repeat(30));

        Consumer<PokerHand> checkHand = PokerHand::evalHand;
        pokerHands.forEach(checkHand.andThen(System.out::println));

        int cardsDealt = playerCount * cardsInHand;
        int cardsRemaining = deck.size() - cardsDealt;

        remainingCards = new ArrayList<>(Collections.nCopies(cardsRemaining, null));
        remainingCards.replaceAll(c -> deck.get(cardsDealt + remainingCards.indexOf(c)));

        Card.printDeck(remainingCards, "Remaining cards", 2);

    }

    private void deal() {
        Card[][] hands = new Card[playerCount][cardsInHand];

        for(int deckIndex = 0, i = 0; i < cardsInHand; i++) {
            for(int j = 0; j < playerCount; j++)
                hands[j][i] = deck.get(deckIndex++);
        }

        // We need to create the poker hands from this data
        int playerNo = 1;
        for(Card[] hand: hands)
            pokerHands.add(new PokerHand(playerNo++, Arrays.asList(hand)));
    }


}
