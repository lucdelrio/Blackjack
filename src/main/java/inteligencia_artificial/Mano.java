package inteligencia_artificial;

import java.util.List;
import java.util.ArrayList;

public class Mano implements Cloneable {

    private final Jugador jugador;
    private final List<Carta> cartas;
    private boolean plantarse;
    
    Mano(Jugador p) {
        
        jugador = p;
        cartas = new ArrayList<Carta>();
    }
    
    private Mano(Jugador unJugador, List<Carta> cartasDisponibles, boolean plantarse) {
        
        this.jugador = unJugador;
        this.cartas = cartasDisponibles;
        this.plantarse = plantarse;
    }
    
    public void agregarCarta(Carta c) {
        
        cartas.add(c);
    }
    
    public void clearCards () {
        cartas.clear();
        plantarse = false;
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
    
    public boolean getPlantarse() {
        return plantarse;
    }
    
    public void setPlantarse(boolean s) {
        plantarse = s;
    }
    
    /**
     * @return true iff jugador's hand has gone bust
     */
    public boolean partidaPerdida() {
        return valorDeLaMano() > 21;
    }
    
    /**
     * @return true iff jugador's hand is a blackjack
     */
    public boolean blackJack() {
        return cartas.size() == 2 && valorDeLaMano() == 21;
    }
    
    /**
     * @return true iff the jugador's hand value is 21
     */
    public boolean suma21() {
        return valorDeLaMano() == 21;
    }
    
    /**
     * @return hand value of jugador
     */
    public int valorDeLaMano() {
        
        int valorCartasSinAses = valorDeLaManoSinAses();
        int cantidadDeAses = cantidadDeAses();
        
        int valorTotalConAs11 = valorCartasSinAses + cantidadDeAses + 10;
        int valorTotalConAs1 = valorCartasSinAses + cantidadDeAses;

        if (cantidadDeAses == 0) {
            return valorCartasSinAses;
        }
        else if (valorTotalConAs11 <= 21) {
            return valorTotalConAs11;
        }
        else if (valorTotalConAs1 <= 21) {
            return valorTotalConAs1;
        }
        
        return valorTotalConAs1;
    }
    
    public int cantidadDeAses() {
        int number = 0;
        
        for (Carta c : cartas) {
            if (c.getNumeroCarta() == NumeroCarta.As)
                number ++;
        }
        
        return number;
    }
    
    public int valorDeLaManoSinAses() {
        
        int val = 0;
        
        for (Carta c : cartas) {
            
            if (c.getNumeroCarta() != NumeroCarta.As) {
                val += c.numeroCarta();
            }
        }
        
        return val;
    }
    
    @Override
    public Mano clone () {
        
        List<Carta> clonedCards = new ArrayList<Carta>();
        
        for (Carta c : this.cartas) {
            clonedCards.add(c.clonarCarta());
        }
        
        return new Mano(this.jugador, clonedCards, this.plantarse);
    }
    
    @Override
    public String toString () {
        String str = "Mano: ";
        
        for (Carta c : cartas) {
            str += c.toString() + " ";
        }
        
        str += "value: " + valorDeLaMano();
        
        return str;
    }
}