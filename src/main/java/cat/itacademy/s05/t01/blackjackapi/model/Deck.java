package cat.itacademy.s05.t01.blackjackapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    @JsonProperty("cards")
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] valors = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] pals = {"HEARTS", "DIAMONDS", "CLUBS", "SPADES"};

        for (String pal : pals) {
            for (String valor : valors) {
                cards.add(new Card(pal, valor));
            }
        }
    }

    // Mescla la baralla
    public void mesclar() {
        Collections.shuffle(cards);
    }

    // Roba una carta
    public Card robarCarta() {
        return cards.remove(cards.size() - 1);
    }
}