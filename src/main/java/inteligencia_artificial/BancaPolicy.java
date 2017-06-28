package inteligencia_artificial;

import java.util.List;

public class BancaPolicy implements Policy {

    @Override
    public Accion elegirAccion(Juego estadoDelJuego, Jugador jugadorActual, List<Accion> actions) {
        
        int mejorValor = estadoDelJuego.getBestHandValue();
        int valorManoActual = estadoDelJuego.getHandFromPlayer(jugadorActual).valorDeLaMano();
        
        if (valorManoActual > mejorValor || valorManoActual > 16) {
            
            return Accion.Plantarse;
        }
        
        return Accion.Pedir;
    }

    @Override
    public void observe(Juego state, Accion accion, Jugador p, Juego nextState, int reward) {
        // TODO Auto-generated method stub
    }

}
