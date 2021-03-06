package inteligencia_artificial;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

class Juego implements Cloneable {

    private final List<Mano> hands; // the hands involved in the game
    private int turnIndex;          // the index of the hand whose turn it is to move
    private Mesa mesa;              // the mesa of cards used in the game

    /**
     * Constructor for Juego
     * @param ps - the list of players in the game (other than the dealer)
     */
    public Juego(List<Jugador> ps) {

        this.hands = new ArrayList<Mano>();

        for (Jugador p : ps) {
            this.hands.add(new Mano(p));
        }
        this.hands.add(new Mano(new Jugador(0, "Banca")));
        turnIndex = 0;
        mesa = new Mesa(2, true);

        initialize();
    }

    public List<Mano> getHands() {
        return hands;
    }
    
    public List<Carta> getVisibleCards () {
        
        return mesa.getCartasRepartidas();
    }

    /**
     * Constructor for Juego
     * @param p - the player in the game (other than the dealer)
     */
    public Juego(Jugador p) {

        this(makeList(p));
    }

    private static <E> List<E> makeList(E obj) {

        List<E> ps = new ArrayList<E>();
        ps.add(obj);

        return ps;
    }

    private Juego(List<Mano> hands, int turnIndex, Mesa mesa) {
        this.hands = hands;
        this.turnIndex = turnIndex;
        this.mesa = mesa;
    }
    
    public int getValorDeLaMejorMano() {
        
        int value = 0;
        for (int i=0; i<(hands.size()-1); i++) {
            
            int currentValue = hands.get(i).valorDeLaMano();
            
            if (currentValue > value && currentValue <= 21) {
                value = currentValue;
            }
        }
        
        return value;
    }

    /**
     * @param p - a player involved in the game
     * @return the player's hand
     */
    public Mano getManoDeJugador(Jugador p) {

        for (Mano h : hands) {
            if (p.equals(h.getJugador())) {
                return h;
            }
        }
        throw new RuntimeException("Unknown player!");
    }

    /**
     * @return true iff the game has ended
     */
    public boolean hasGameEnded () {

        for (Mano h : hands) {

            if (!h.partidaPerdida() && !h.getPlantarse() && !h.blackJack() && !h.suma21()) {
                return false;
            }
        }

        return true;
    }
    
    public boolean isLowOnCards () {
        
        int requiredCards = 10 * hands.size();
        
        return mesa.remainingCards() < requiredCards;
    }
    
    public void redeck () {
        mesa.reset();
    }

    /**
     * @param p - a player
     * @return - the reward earned by the player
     */
    public int getReward (Jugador p) {

        if (hasGameEnded()) {

            Mano h = getManoDeJugador(p);
            Mano dealer = hands.get(hands.size() - 1);

            return calculateReward(h, dealer);
        }

        return 0;
    }

    /**
     * @return the player whose turn it is to act
     */
    public Jugador getPlayerToAct () {
        return hands.get(turnIndex).getJugador();
    }

    /**
     * @return list of actions available to the player to act
     */
    public List<Accion> getNextActions () {

        List<Accion> actions = new ArrayList<Accion>();

        for (Accion m : Accion.values()) {
            actions.add(m);
        }

        return actions;
    }

    /**
     * @param p - the player to act
     * @param a - the action performed by the player to act
     */
    public Juego performAction (Jugador p, Accion a) {

        Juego clonedGame = this.clone();

        Mano h = clonedGame.getManoDeJugador(p);

        if (a == Accion.Pedir) {

            Carta c = clonedGame.mesa.darCarta();
            h.agregarCarta(c);
            if (h.partidaPerdida() || h.suma21() || h.blackJack()) {
                clonedGame.turnIndex = (clonedGame.turnIndex + 1) % clonedGame.hands.size();
            }
        }
        else if (a == Accion.Plantarse) {
            h.setPlantarse(true);
            clonedGame.turnIndex = (clonedGame.turnIndex + 1) % clonedGame.hands.size();
        }

        return clonedGame;
    }
    
    public Juego redealToDealer () {
        
        Juego clonedGame = this.clone();
        
        Mano dealerHand = clonedGame.hands.get(clonedGame.hands.size() - 1);
        dealerHand.clearCards();
        dealerHand.agregarCarta(clonedGame.mesa.darCarta());
        dealerHand.agregarCarta(clonedGame.mesa.darCarta());

        return clonedGame;
    }

    @Override
    public Juego clone() {

        List<Mano> clonedHands = new ArrayList<Mano>();

        for (Mano h : this.hands) {
            clonedHands.add(h.clone());
        }
        Mesa clonedDeck =   this.mesa.clone();

        return new Juego(clonedHands, this.turnIndex, clonedDeck);
    }

    // Initializes the game, by dealing two cards to each player
    // ending with the dealer
    private void initialize () {

        turnIndex = 0;
        for (Mano h : hands) {

            for (int i=0; i<2; i++) {

                h.agregarCarta(mesa.darCarta());
            }
            
            if (h.suma21() || h.blackJack()) {
                turnIndex = (turnIndex + 1) % hands.size();
            }
        }
    }
    
    public void reset () {
        
        for (Mano h : hands) {
            
            h.clearCards();
        }
        
        initialize();
    }

    // Prints the result of the game, with each player's hand
    // and whether each player won/lost/drew
    public void printResult () {

        System.out.println("------------------------------------");

        for (Mano h : hands) {
            Jugador p = h.getJugador();

            System.out.print(p + "" + h + "\n");
        }

        Mano dealer = hands.get(hands.size() - 1);
        for (Mano h : hands) {

            if (h != dealer) {

                Jugador p = h.getJugador();
                int r = getReward(p);
                String result;

                if (r > 0) { result = " gana"; }
                else if (r < 0) { result = " piede"; }
                else { result = " empata"; }

                System.out.println(p.toString() + result);
            }
        }

        System.out.println("------------------------------------");
    }

    // Calculates and returns the reward earned by the player whose hand
    // is h, when the dealer's hand is `dealerHand`
    private int calculateReward (Mano p, Mano dealerHand) {

        if (p.blackJack() && !dealerHand.blackJack()) {
            return (int)(Configuracion.CantidadApostada * 1.5);
        }
        else if (p.partidaPerdida() || (!dealerHand.partidaPerdida() && p.valorDeLaMano() < dealerHand.valorDeLaMano())) {
            return -Configuracion.CantidadApostada;
        }
        else if (dealerHand.partidaPerdida() || p.valorDeLaMano() > dealerHand.valorDeLaMano()) {
            return Configuracion.CantidadApostada;
        }

        return 0;
    }

    public static Juego playRound (Juego g) {
        
        Map<Jugador, Juego> previousGameDict = new HashMap<Jugador, Juego>();
        Map<Jugador, Juego> nextGameDict = new HashMap<Jugador, Juego>();
        Map<Jugador, Accion> actionsDict = new HashMap<Jugador, Accion>();

        while(!g.hasGameEnded()) {

            Jugador p = g.getPlayerToAct();
            List<Accion> actions = g.getNextActions();

            Accion chosen = p.setAccion(actions, g);
            Juego nextState = g.performAction(p, chosen);

            previousGameDict.put(p, g);
            actionsDict.put(p, chosen);
            nextGameDict.put(p, nextState);
            
            for(Mano h : g.getHands()) {

                Jugador ep = h.getJugador();

                if (previousGameDict.containsKey(ep)) {

                    int reward = nextState.getReward(ep);
                    ep.observe(previousGameDict.get(ep), actionsDict.get(ep), nextGameDict.get(ep), reward);
                }
            }

            g = nextState;
        }
        
        return g;
    }
}
