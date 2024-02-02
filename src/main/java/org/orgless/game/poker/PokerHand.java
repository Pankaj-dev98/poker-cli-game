package org.orgless.game.poker;

import org.orgless.game.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokerHand {
    // The hand that's dealt
    private List<Card> hand;
    // Cards that are kept
    private List<Card> keepers;
    // Cards we might want to trade in
    private List<Card> discards;

    // The dealer will be the last player that is dealt the cards.
    private Ranking score = Ranking.NONE;

    // This int is the order of the player ie. their placement from the dealer
    // Player 1. will be next to the dealer and  dealer will be the last hand to be dealt
    private int playerNo;

    public PokerHand(int playerNo, List<Card> hand) {
        hand.sort(Card.sortRankReversedSuit());
        this.hand = hand;
        this.playerNo = playerNo;
        keepers = new ArrayList<>(hand.size());
        discards = new ArrayList<>(hand.size());
    }

    /*
     * This code evaluates each player's hand.
     * In a real game, we may only do this if we had some automated competitors, or, if we're teaching someone how to play.
     * Here, we evaluate only half of the hand ranks. The ones associated with duplicate face cards in a hand, for simplicity.
     */
    private void setRank(int faceCount) {
        switch (faceCount) {
            case 4 -> score = Ranking.FOUR_OF_A_KIND;
            case 3 -> {
                if(score == Ranking.NONE)
                    score =  Ranking.THREE_OF_A_KIND;
                else
                    score = Ranking.FULL_HOUSE;
            }
            case 2 -> {
                if(score == Ranking.NONE)
                    score = Ranking.ONE_PAIR;
                else if(score == Ranking.THREE_OF_A_KIND)
                    score = Ranking.FULL_HOUSE;
                else
                    score = Ranking.TWO_PAIR;
            }
        }
    }

    public void evalHand() {
        List<String> faceList = new ArrayList<>(hand.size());
        hand.forEach(card -> faceList.add(card.getFace()));

        List<String> duplicateFaceCards = new ArrayList<>();

        faceList.forEach(face -> {
            if(!duplicateFaceCards.contains(face) &&
                    Collections.frequency(faceList, face) > 1)
                duplicateFaceCards.add(face);
        });

        for(String duplicate: duplicateFaceCards) {
            int start = faceList.indexOf(duplicate);
            int last = faceList.lastIndexOf(duplicate);

            // As the hand is sorted, the cards with the same face/rank are contiguous.
            setRank(last - start + 1);

            List<Card> sub = hand.subList(start, last + 1);
            keepers.addAll(sub);
        }
        pickDiscards();
    }

    /*
    * We populate the discards pile for each player.
    * This will be a suggested list of cards to discard, based on cards that aren't part of a card rank as well as lower ranked cards.
    */
    private void pickDiscards() {
        List<Card> temp = new ArrayList<>(hand);
        temp.removeAll(keepers);

        // We may not want to throw away higher rank cards, like an ace or a king.
        int rankedCards = keepers.size();
        Collections.reverse(temp);
        int index = 0;
        for(Card c: temp) {
            if(index++ < 3 && (rankedCards > 2 || c.getRank() < 9))
                discards.add(c);
            else
                keepers.add(c);
        }
    }


    @Override
    public String toString() {
        return "%d. %-16s Rank:%d %-40s Best:%-7s Worst:%-6s %s".formatted(
                playerNo, score, score.ordinal(), hand,
                Collections.max(hand, Comparator.comparing(Card::getRank)),
                Collections.min(hand, Comparator.comparing(Card::getRank)),
                (discards.size() > 0) ? "Discards: " + discards: ""
        );
    }
}
