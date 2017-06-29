package inteligencia_artificial;

import java.util.*;


public class QLearningPolicy implements Policy {
    
    //Map<CardCountingState, Integer> qValues;

    private static Map<PointsState, Double> qValues = new TreeMap<PointsState, Double>();
    private double epsilon  = 0.30;
    private boolean isCountingPolicy;
    
    public QLearningPolicy() {
        
        isCountingPolicy = false;
    }
    
    public QLearningPolicy(boolean countCards) {
        this.isCountingPolicy = countCards;
    }
    
    public void setEpsion (double ep) {
        epsilon = ep;
    }
   
    @Override
    public Accion elegirAccion(Juego gameState, Jugador p, List<Accion> legalActions) {
        
        if (legalActions.isEmpty())
            throw new RuntimeException("No actions to perform!");

        if(BlackjackUtil.getNumber() < epsilon){
            return BlackjackUtil.getAccionRandom(legalActions);
        }
        else{
            return getActionFromQValues(gameState, p, legalActions);
        }
    }
    
    @Override
    public void observe(Juego state, Accion accion, Jugador p, Juego nextState, int reward) {

        PointsState ps = getPointState(state, p, accion);
        PointsState nps = getPointState(nextState, p, accion);

        double qValue = getQValue(ps);
        double nextStateValue ;
        if( ps.equals(nps) ){
            nextStateValue = getQValue(ps);
        }
        else {
            nextStateValue = getValueFromQValues(nextState, p);
        }
        
        double prevWeightedValue = (1.0 - Configuracion.alpha) * qValue;//-32
        double weightedValue = Configuracion.alpha * (reward + nextStateValue);//-20
        double newValue = prevWeightedValue + weightedValue;//-36

        qValues.put(ps, newValue);
    }

    private double getQValue(PointsState ps){

        if(qValues.containsKey(ps)) {
            return qValues.get(ps);
        }
        
        return 0.0;
    }

    private Double getValueFromQValues(Juego state, Jugador p){
        double maxValue = Double.NEGATIVE_INFINITY;
        List<Accion> legalActions = state.getNextActions();

        for (Accion accion : legalActions){
            PointsState ps = getPointState(state, p, accion);
            double psQValue = getQValue(ps);
            if (psQValue > maxValue){
                maxValue = psQValue;
            }
        }
        
        return maxValue;

    }

    private Accion getActionFromQValues(Juego state, Jugador p, List<Accion> legalActions){
        // default action
        Accion maxAction = null;
        double maxValue = Double.NEGATIVE_INFINITY;

        for (Accion accion : legalActions){
            PointsState ps = getPointState(state, p, accion);
            double psQValue = getQValue(ps);
            
            if (psQValue > maxValue){
                maxValue = psQValue;
                maxAction = accion;
            }
        }
        
        if (maxAction == null) {
            return BlackjackUtil.getAccionRandom(legalActions);
        }
        
        return maxAction;
    }
    
    public void printQValues () {
        
        Set<PointsState> keys = qValues.keySet();
        for(PointsState key : keys) {
            
            Double val = qValues.get(key);
            int intVal = val.intValue();
            
            System.out.println(key.toString() + " " + intVal);
        }
    }
    
    private PointsState getPointState(Juego state, Jugador p, Accion accion){

        Mano h = state.getHandFromPlayer(p);
        
        if (this.isCountingPolicy) {
            
           int cc = 0;
           
           List<Carta> cartas = state.getVisibleCards();
           for (Carta c : cartas) {
               if (c.cartaAlta()) { cc --; }
               else if (c.cartaBaja()) { cc ++; }
           }
            
            return new PointsState(h.cantidadDeAses(), h.valorDeLaManoSinAses(), accion, cc);
        }
        else {
        
            return new PointsState(h.cantidadDeAses(), h.valorDeLaManoSinAses(), accion);
        }
    }
}

