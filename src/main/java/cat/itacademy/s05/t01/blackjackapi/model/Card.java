package cat.itacademy.s05.t01.blackjackapi.model;

public class Card {

    private String suit; // Pal de la carta (ex: "HEARTS", "SPADES").
    private String value; // Valor de la carta (ex: "A", "2", "K").

    public Card(String suit, String value){
        this.suit  = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}