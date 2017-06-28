package inteligencia_artificial;

/**
 * Created by lucas on 27/06/17.
 */
public class Carta {

    private final Palo palo;
    private final NumeroCarta numeroCarta;

    /**
     * Enum-based constructor
     * @param palo the palo of the card
     * @param numeroCarta the numeroCarta of the card
     */
    public Carta(Palo palo, NumeroCarta numeroCarta) {

        this.palo = palo;
        this.numeroCarta = numeroCarta;
    }

    /**
     * Integer-based constructor
     * @param palo the palo index of the card
     * @param numeroCarta the numeroCarta index of the card
     */
    public Carta(int palo, int numeroCarta) {

        this.palo = posicionAPalo(palo);
        this.numeroCarta = posicionANumero(numeroCarta);
    }

    /**
     * @return the palo of the card
     */
    public Palo getPalo() {
        return palo;
    }

    /**
     * @return the numeroCarta of the card
     */
    public NumeroCarta getNumeroCarta() {
        return numeroCarta;
    }

    public boolean cartaAlta() {

        return this.numeroCarta() > 9 || this.numeroCarta == NumeroCarta.As;
    }

    public boolean cartaBaja() {

        return this.numeroCarta() < 7 && this.numeroCarta != NumeroCarta.As;
    }

    public int numeroCarta() {

        switch (numeroCarta) {
            case Two: return 2;
            case Three: return 3;
            case Four: return 4;
            case Five: return 5;
            case Six: return 6;
            case Seven: return 7;
            case Eight: return 8;
            case Nine: return 9;
            case Ten: return 10;
            case Jack: return 10;
            case Queen: return 10;
            case King: return 10;
            case As: return 1;
            default: throw new RuntimeException("Invalid Rank");
        }
    }

    /**
     *
     */
    public Carta clonarCarta() {
        return new Carta(this.palo, this.numeroCarta);
    }

    /**
     * The String-representation of the Carta
     */
    public String toString () {
        return paloString() + " " + numeroString();
    }

    // private
    private static Palo posicionAPalo(int i) {

        switch (i) {
            case 0: return Palo.Trebol;
            case 1: return Palo.Diamantes;
            case 2: return Palo.Corazones;
            case 3: return Palo.Picas;
            default: throw new RuntimeException("Invalid Suit Index");
        }
    }

    // private
    private static NumeroCarta posicionANumero(int i) {

        switch (i) {
            case 0: return NumeroCarta.Two;
            case 1: return NumeroCarta.Three;
            case 2: return NumeroCarta.Four;
            case 3: return NumeroCarta.Five;
            case 4: return NumeroCarta.Six;
            case 5: return NumeroCarta.Seven;
            case 6: return NumeroCarta.Eight;
            case 7: return NumeroCarta.Nine;
            case 8: return NumeroCarta.Ten;
            case 9: return NumeroCarta.Jack;
            case 10: return NumeroCarta.Queen;
            case 11: return NumeroCarta.King;
            case 12: return NumeroCarta.As;
            default: throw new RuntimeException("Invalid Rank Index");
        }
    }

    // private
    private String paloString() {

        switch (palo) {
            case Trebol: return "♣";
            case Diamantes: return "♦";
            case Corazones: return "♥";
            case Picas: return "♠";
            default: throw new RuntimeException("Invalid Suit");
        }
    }

    // private
    private String numeroString() {

        switch (numeroCarta) {
            case Two: return "2";
            case Three: return "3";
            case Four: return "4";
            case Five: return "5";
            case Six: return "6";
            case Seven: return "7";
            case Eight: return "8";
            case Nine: return "9";
            case Ten: return "10";
            case Jack: return "J";
            case Queen: return "Q";
            case King: return "K";
            case As: return "A";
            default: throw new RuntimeException("Invalid Rank");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Carta) {

            Carta otraCarta = (Carta)obj;

            return otraCarta.numeroCarta == this.numeroCarta && otraCarta.palo == this.palo;
        }

        return false;
    }

    public int getCartaEntero() {
        return numeroCarta();
    }

    String[] VALUES = {
            "As", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "J", "Q", "K"
    };

}