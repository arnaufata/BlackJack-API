package cat.itacademy.s05.t01.blackjackapi.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> cards;
    private int totalValue;


    public Hand() {
        this.cards      = new ArrayList<>();  // Inicialitza una mà buida.
        this.totalValue = 0;
    }


    public List<Card> getCards() {          // Obté la llista de cartes actual de la mà.
        return cards;
    }
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    // Afegeix una carta a la mà i actualitza el valor total
    public void addCard(Card card) {
        cards.add(card);
        totalValue = getHandValue();
    }

    // Calcula el valor total de la mà tenint en compte les regles del Blackjack.
    public int getHandValue() {
        int totalValue = 0;
        int aces = 0;

        for (Card card : cards) {
            String value = card.getValue();

            switch (value) {
                case "A":  // L'As pot ser 1 o 11.
                    totalValue += 11;
                    aces++;
                    break;
                case "K":
                case "Q":
                case "J":
                    totalValue += 10;  // Les figures valen 10.
                    break;
                default:
                    totalValue += Integer.parseInt(value);  // Les altres cartes valen el seu número.
                    break;
            }
        }

        // Ajusta el valor dels Asos si el total passa de 21.
        while (totalValue > 21 && aces > 0) {
            totalValue -= 10;  // L'As passa de valer 11 a 1.
            aces--;
        }

        return totalValue;
    }

    // Comprova si la mà ha sobrepassat 21.
    public boolean isBusted() {
        return getHandValue() > 21;
    }

    // Comprova si la mà és un Blackjack (exactament 21 amb dues cartes).
    public boolean isBlackJack() {
        return cards.size() == 2 && getHandValue() == 21;
    }
}