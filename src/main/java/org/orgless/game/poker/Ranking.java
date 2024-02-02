package org.orgless.game.poker;

/*
 * This enum represents the card ranks.
 * We set up the ranks such that the worst card is the 0'th ordinal position ie. none (nothing of value in hand)
 *
 */
public enum Ranking {
    NONE, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND;


    @Override
    public String toString() {
        return this.name().replace('_', ' ');
    }
}
