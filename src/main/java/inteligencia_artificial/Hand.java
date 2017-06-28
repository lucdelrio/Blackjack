package inteligencia_artificial;

import java.util.List;
import java.util.ArrayList;

public class Hand implements Cloneable {

    private final Player player;
    private final List<Carta> cartas;
    private boolean stay;
    
    Hand(Player p) {
        
        player = p;
        cartas = new ArrayList<Carta>();
    }
    
    private Hand (Player p, List<Carta> cs, boolean stay) {
        
        this.player = p;
        this.cartas = cs;
        this.stay = stay;
    }
    
    public void addCard(Carta c) {
        
        cartas.add(c);
    }
    
    public void clearCards () {
        cartas.clear();
        stay = false;
    }
    
    public List<Carta> getCartas() {
        
        List<Carta> ccs = new ArrayList<Carta>();
        
        for (Carta c : cartas) {
            ccs.add(c.clonarCarta());
        }
        return ccs;
    }
    
    public Player getPlayer () {
        return player;
    }
    
    public boolean isStay () {
        return stay;
    }
    
    public void setIsStay (boolean s) {
        stay = s;
    }
    
    /**
     * @return true iff player's hand has gone bust
     */
    public boolean isBust () {
        return handValue() > 21;
    }
    
    /**
     * @return true iff player's hand is a blackjack
     */
    public boolean isBlackjack () {
        return cartas.size() == 2 && handValue() == 21;
    }
    
    /**
     * @return true iff the player's hand value is 21
     */
    public boolean isTwentyOne () {
        return handValue() == 21;
    }
    
    /**
     * @return hand value of player
     */
    public int handValue () {
        
        int fixedValue = handValueWithoutAces();
        int numberOfAces = numberOfAces();
        
        int totalValueWithOneAceMax = fixedValue + numberOfAces + 10;
        int totalValueWithAllAcesMin = fixedValue + numberOfAces;

        if (numberOfAces == 0) {
            return fixedValue;
        }
        else if (totalValueWithOneAceMax <= 21) {
            return totalValueWithOneAceMax;
        }
        else if (totalValueWithAllAcesMin <= 21) {
            return totalValueWithAllAcesMin;
        }
        
        return totalValueWithAllAcesMin;
    }
    
    public int numberOfAces () {
        int number = 0;
        
        for (Carta c : cartas) {
            if (c.getNumero() == NumeroCarta.As)
                number ++;
        }
        
        return number;
    }
    
    public int handValueWithoutAces () {
        
        int val = 0;
        
        for (Carta c : cartas) {
            
            if (c.getNumero() != NumeroCarta.As) {
                val += c.numeroCarta();
            }
        }
        
        return val;
    }
    
    @Override
    public Hand clone () {
        
        List<Carta> clonedCards = new ArrayList<Carta>();
        
        for (Carta c : this.cartas) {
            clonedCards.add(c.clonarCarta());
        }
        
        return new Hand(this.player, clonedCards, this.stay);
    }
    
    @Override
    public String toString () {
        String str = "Hand: ";
        
        for (Carta c : cartas) {
            str += c.toString() + " ";
        }
        
        str += "value: " + handValue();
        
        return str;
    }
}