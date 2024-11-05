package cat.itacademy.s05.t01.blackjackapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
public class Game {

    @Id
    private String id;

    private PlayerInfo player;
    private Dealer dealer;
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private double aposta;
    private String status; // "IN_PROGRESS", "PLAYER_WINS", "DEALER_WINS", "DRAW"
    //private LocalDateTime dataCreacio;

    public Game() {}

    public Game(Player player, double aposta) {
        this.player = new PlayerInfo(player.getId(), player.getName());
        this.dealer = new Dealer();
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.aposta = aposta;
        this.status = "IN_PROGRESS";
        //this.dataCreacio = LocalDateTime.now();
    }

    // Classe interna per emmagatzemar informació simplificada del jugador
    public static class PlayerInfo {
        private long id;
        private String name;

        public PlayerInfo(long id, String name) {
            this.id = id;
            this.name = name;
        }

        // Getters i setters
        public long getId() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    // Getters i setters per la resta de propietats de Game

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public PlayerInfo getPlayer() {
        return player;
    }
    public void setPlayer(PlayerInfo player) {
        this.player = player;
    }

    public Dealer getDealer() {
        return dealer;
    }
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public Deck getDeck() {
        return deck;
    }
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }
    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }
    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public double getAposta() {
        return aposta;
    }
    public void setAposta(double aposta) {
        this.aposta = aposta;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }


    /*public LocalDateTime getDataCreacio() { return dataCreacio; }
    public void setDataCreacio(LocalDateTime dataCreacio) { this.dataCreacio = dataCreacio; }*/

    // Afegeix una carta a la mà del jugador.
    public void addCardToPlayerHand(Card card) {
        playerHand.addCard(card);
    }

    // Afegeix una carta a la mà del crupier.
    public void addCardToDealerHand(Card card) {
        dealerHand.addCard(card);
    }

    // Comprova si la partida ha acabat.
    public boolean isGameFinished() {
        return "FINISHED".equals(this.status);
    }

    // Finalitza la partida.
    public void finishGame() {
        this.status = "FINISHED";
    }
}