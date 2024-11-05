package cat.itacademy.s05.t01.blackjackapi.model;

public class Bet {

    private double amount;
    private String betStatus;  // "WIN", "LOSE", "PUSH"

    public Bet(double amount) {
        this.amount    = amount;
        this.betStatus = "IN_PROGRESS";
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBetStatus() {
        return betStatus;
    }
    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }
}
