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
        if (availableCards.size() > 2) {
            if (canAffordMoreThanOneCard(mana, availableCards)) {
                return lowestCardStrategy.nextCard(mana, availableCards);
            } else {
                return highestCardStrategy.nextCard(mana, availableCards);
            }
        }
        if (availableCards.size() <= 2) {
            return highestCardStrategy.nextCard(mana, availableCards);
        }
        return Optional.empty();
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
