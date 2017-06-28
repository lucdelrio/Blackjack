package inteligencia_artificial;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Deck implements Cloneable {
    
    private List<Carta> undealtCards;
    private List<Carta> dealtCards;
    
    /**
     * Constructor for a new Deck
     * @param shuffle - whether the Deck should be shuffled
     */
    public Deck (int n, boolean shuffle) {
        
        undealtCards = new ArrayList<Carta>();
        for (int i=0; i<n; i++) {
            undealtCards.addAll(createDeck());
        }
        dealtCards = new ArrayList<Carta>();
        
        if (shuffle) {
            shuffle();
        }
    }
    
    public List<Carta> getDealtCards () {
        return dealtCards;
    }
    
    private Deck(List<Carta> ud_cards, List<Carta> d_cartas) {
        
        this.undealtCards = ud_cards;
        this.dealtCards = d_cartas;
    }
    
    /**
     * Shuffles the deck of undealt cards
     */
    public void shuffle () {
        
        List<Carta> cList = new ArrayList<Carta>();
        Random rand = new Random();
        while(!undealtCards.isEmpty()) {
            
            int i = rand.nextInt(undealtCards.size());
            
            Carta c = undealtCards.remove(i);
            cList.add(c);
        }
        
        undealtCards = cList;
    }
    
    public int remainingCards () {
        
        return undealtCards.size();
    }
    
    public void reset () {
        
        undealtCards.addAll(dealtCards);
        dealtCards.clear();
        shuffle();
    }
    
    /**
     * Deals one card and moves it to the un-dealt stack, so it isn't
     * dealt again.
     * @return a Carta
     */
    public Carta deal () {
        
        shuffle();
        shuffle();
        shuffle();
        Carta c = undealtCards.remove(0);
        dealtCards.add(c);
        
        return c;
    }
    
    /**
     * Creates a Deck in sorted order
     * @return a Deck in sorted order
     */
    static List<Carta> createDeck () {
        
        List<Carta> cList = new ArrayList<Carta>();
     
        for (int suit=0; suit < 4; suit++) {
            
            for (int rank=0; rank < 13; rank++) {
                
                cList.add(new Carta(suit, rank));
            }
        }
        
        return cList;
    }
    
    @Override
    public Deck clone() {
        
        List<Carta> ud_cards = new ArrayList<Carta>();
        List<Carta> d_cartas = new ArrayList<Carta>();
        
        for (Carta c : undealtCards) {
            ud_cards.add(c.clonarCarta());
        }
        
        for (Carta c : dealtCards) {
            d_cartas.add(c.clonarCarta());
        }
        
        return new Deck(ud_cards, d_cartas);
    }
    
    // For internal testing
    public static void main(String[] args) {
        
        Deck d = new Deck(1, true);

        for (Carta c : d.undealtCards) {
            System.out.print(c + " ");
        }
    }
}