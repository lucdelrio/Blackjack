package inteligencia_artificial;

import java.util.List;
import java.util.ArrayList;

public class Hand implements Cloneable {

    private final Jugador jugador;
    private final List<Carta> cartas;
    private boolean stay;
    
    Hand(Jugador p) {
        
        jugador = p;
        cartas = new ArrayList<Carta>();
    }
    
    private Hand (Jugador p, List<Carta> cs, boolean stay) {
        
        this.jugador = p;
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
    
    public Jugador getJugador() {
        return jugador;
    }
    
    public boolean isStay () {
        return stay;
    }
    
    public void setIsStay (boolean s) {
        stay = s;
    }
    
    /**
     * @return true iff jugador's hand has gone bust
     */
    public boolean isBust () {
        return valorDeLaMano() > 21;
    }
    
    /**
     * @return true iff jugador's hand is a blackjack
     */
    public boolean isBlackjack () {
        return cartas.size() == 2 && valorDeLaMano() == 21;
    }
    
    /**
     * @return true iff the jugador's hand value is 21
     */
    public boolean isTwentyOne () {
        return valorDeLaMano() == 21;
    }
    
    /**
     * @return hand value of jugador
     */
    public int valorDeLaMano() {
        
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
            if (c.getNumeroCarta() == NumeroCarta.As)
                number ++;
        }
        
        return number;
    }
    
    public int handValueWithoutAces () {
        
        int val = 0;
        
        for (Carta c : cartas) {
            
            if (c.getNumeroCarta() != NumeroCarta.As) {
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
        
        return new Hand(this.jugador, clonedCards, this.stay);
    }
    
    @Override
    public String toString () {
        String str = "Hand: ";
        
        for (Carta c : cartas) {
            str += c.toString() + " ";
        }
        
        str += "value: " + valorDeLaMano();
        
        return str;
    }
}