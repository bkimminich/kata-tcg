package de.kimminich.kata.tcg.strategy;

import de.kimminich.kata.tcg.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AiStrategy implements Strategy {

    @Override
    public Optional<Card> nextCard(int mana, List<Card> availableCards) {
        if (availableCards.size() > 2) {
            if (canAffordMoreThanOneCard(mana, availableCards)) {
                return new LowestCardFirstStrategy().nextCard(mana, availableCards);
            }
            else {
                return new HighestCardFirstStrategy().nextCard(mana, availableCards);
            }
        }
        if (availableCards.size() == 2) {
            return new HighestCardFirstStrategy().nextCard(mana, availableCards);
        }
        return Optional.empty();
    }

    private boolean canAffordMoreThanOneCard(int mana, List<Card> availableCards) {
        Optional<Card> card = new LowestCardFirstStrategy().nextCard(mana, availableCards);
        if (card.isPresent()) {
            List<Card> remainingCards = new ArrayList<>(availableCards);
            remainingCards.remove(card.get());
            return new LowestCardFirstStrategy().nextCard(mana-card.get().getManaCost(), remainingCards).isPresent();
        }
        return false;
    }

}
