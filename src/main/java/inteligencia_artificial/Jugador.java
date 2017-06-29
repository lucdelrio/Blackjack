package inteligencia_artificial;

import java.util.List;

public class Jugador {
    
    private final int     id;
    private final String nombre;
    private final Policy  policy;
    

    public Jugador(int id, String nombre, Policy policy) {
        
        this.id = id;
        this.nombre = nombre;
        this.policy = policy;
    }
    
    public Jugador(int id, String nombre) {
        
        this.id = id;
        this.nombre = nombre;
        this.policy = new BancaPolicy();
    }
    
    /**
     * @return id of player
     */
    public int getId () {
        return id;
    }
    
    /**
     * @return nombre of player
     */
    public String getNombre() {
        return nombre;
    }
    
    public Accion setAccion(List<Accion> acciones, Juego g) {
        
        return this.policy.elegirAccion(g, this, acciones);
    }
    
    public void observe(Juego g, Accion accion, Juego nextState, int reward) {
        
//        if (this.policy instanceof QLearningPolicy && !this.getNombre().equals("Dealer")) {
//            printObservation(g, action, this, nextState, reward);
//        }
        this.policy.observe(g, accion, this, nextState, reward);
    }
    
    private static void printObservation(Juego g, Accion accion, Jugador p, Juego nextState, int reward) {
        
        String str = "Observing: ";
        
        Mano pHand = g.getManoDeJugador(p);
        
        str += p.toString() ;
        str += " takes accion: " + accion;
        str += "\nwith hand " + pHand;
        str += "\nleading to hand " + nextState.getManoDeJugador(p);
        str += "\nearning reward " + reward;
        
        if (pHand.valorDeLaMano() == 18 && pHand.cantidadDeAses() == 0 && accion == Accion.Pedir) {
        
            if (reward > 0) {
                
                System.out.println("!!!!");
            }
            System.out.println(str);
        }
    }
    
    /**
     * String representation of Jugador
     */
    @Override
    public String toString () {
        return nombre;
    }   
}