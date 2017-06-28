package inteligencia_artificial;

import java.util.List;

public interface Policy {
    
    public Accion elegirAccion(Juego gameState, Jugador jugador, List<Accion> listaDeAcciones);
    public void observe(Juego state, Accion accion, Jugador jugador, Juego nextState, int reward);
}
