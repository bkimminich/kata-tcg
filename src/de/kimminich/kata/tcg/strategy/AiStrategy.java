package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AiStrategy implements Strategy {

    private Strategy lowestCardStrategy = new LowestCardFirstStrategy();
    private Strategy highestCardStrategy = new HighestCardFirstStrategy();

    @Override
    public Optional<Card> nextCard(int mana, List<Card> availableCards) {
        for (Card card : availableCards) {
            List<Card> remainingCards = new ArrayList<>(availableCards);
            remainingCards.remove(card);
            for (Card comboCard : remainingCards) {
                if (card.getManaCost() + comboCard.getManaCost() == mana) {
                    return Optional.of(card);
                }
            }
        }
        if (canAffordMoreThanOneCard(mana, availableCards)) {
            return lowestCardStrategy.nextCard(mana, availableCards);
        } else {
            return highestCardStrategy.nextCard(mana, availableCards);
        }
    }

    private boolean canAffordMoreThanOneCard(int mana, List<Card> availableCards) {
        Optional<Card> card = lowestCardStrategy.nextCard(mana, availableCards);
        if (card.isPresent()) {
            List<Card> remainingCards = new ArrayList<>(availableCards);
            remainingCards.remove(card.get());
            return lowestCardStrategy.nextCard(mana - card.get().getManaCost(), remainingCards).isPresent();
        }
        return false;
    }


}
