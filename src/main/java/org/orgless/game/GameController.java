package org.orgless.game;

/*
 * This is a poker game with 5 card draw.
 * This game usually has four or more players.
 *
 * The dealer shuffles the deck, and asks another player to cut the deck
 * The dealer deals the cards one at a time to each player, starting with the player on the dealer's left, until eachplayer has 5 cards
 * Each player evaluates his hand for certain card combinations, called card ranks.
 * Each player can discard up to 3 cards.
 * The dealer will replace discarded cards from the remaining pile, in hte order they've been shuffled.
 * Each player re-evaluates his hand if he drew new cards, and bets on his hand
 *
 * Our Deck doesn't have a Joker, so we can never draw 'five of a kind'
 * Also, here, we do not code straights or straight-flushes
 *
 */


import org.orgless.game.poker.PokerGame;

public class GameController {
    public static void main(String[] args) {
        PokerGame fiveCardDraw = new PokerGame(5, 5);
        fiveCardDraw.startPlay();
    }
}
