package inteligencia_artificial;
import java.util.List;
import java.util.Random;

public class BlackjackUtil {

    public static double getNumber() {
        Random generadorDeNumero = new Random();
        double number = generadorDeNumero.nextDouble();
        return number;
    }

    public static Accion getAccionRandom(List<Accion> listaDeAcciones){
        Random random = new Random();
        return listaDeAcciones.get(random.nextInt(listaDeAcciones.size()));
    }
}
