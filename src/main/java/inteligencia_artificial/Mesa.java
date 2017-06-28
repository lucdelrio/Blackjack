package inteligencia_artificial;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Mesa implements Cloneable {
    
    private List<Carta> mazo;
    private List<Carta> cartasRepartidas;
    
    /**
     * Constructor for a new Mesa
     * @param shuffle - whether the Mesa should be shuffled
     */
    public Mesa(int n, boolean shuffle) {
        
        mazo = new ArrayList<Carta>();
        for (int i=0; i<n; i++) {
            mazo.addAll(crearMesa());
        }
        cartasRepartidas = new ArrayList<Carta>();
        
        if (shuffle) {
            barajar();
        }
    }
    
    public List<Carta> getCartasRepartidas() {
        return cartasRepartidas;
    }
    
    private Mesa(List<Carta> cartasMazo, List<Carta> cartasRepartidas) {
        
        this.mazo = cartasMazo;
        this.cartasRepartidas = cartasRepartidas;
    }
    
    /**
     * Shuffles the deck of undealt cards
     */
    public void barajar() {
        
        List<Carta> cList = new ArrayList<Carta>();
        Random random = new Random();
        while(!mazo.isEmpty()) {
            
            int i = random.nextInt(mazo.size());
            
            Carta c = mazo.remove(i);
            cList.add(c);
        }
        
        mazo = cList;
    }
    
    public int remainingCards () {
        
        return mazo.size();
    }
    
    public void reset () {
        
        mazo.addAll(cartasRepartidas);
        cartasRepartidas.clear();
        barajar();
    }
    
    /**
     * Deals one card and moves it to the un-dealt stack, so it isn't
     * dealt again.
     * @return a Carta
     */
    public Carta darCarta() {
        
        barajar();
        barajar();
        barajar();
        Carta c = mazo.remove(0);
        cartasRepartidas.add(c);
        
        return c;
    }
    
    /**
     * Creates a Mesa in sorted order
     * @return a Mesa in sorted order
     */
    static List<Carta> crearMesa() {
        
        List<Carta> listaDeCartas = new ArrayList<Carta>();
     
        for (int palo=0; palo < 4; palo++) {
            
            for (int numeroCarta=0; numeroCarta < 13; numeroCarta++) {
                
                listaDeCartas.add(new Carta(palo, numeroCarta));
            }
        }
        
        return listaDeCartas;
    }
    
    @Override
    public Mesa clone() {
        
        List<Carta> mazoClone = new ArrayList<Carta>();
        List<Carta> cartasRepartidasClone = new ArrayList<Carta>();
        
        for (Carta c : mazo) {
            mazoClone.add(c.clonarCarta());
        }
        
        for (Carta c : cartasRepartidas) {
            cartasRepartidasClone.add(c.clonarCarta());
        }
        
        return new Mesa(mazoClone, cartasRepartidasClone);
    }
    
    // For internal testing
    public static void main(String[] args) {
        
        Mesa d = new Mesa(1, true);

        for (Carta c : d.mazo) {
            System.out.print(c + " ");
        }
    }
}