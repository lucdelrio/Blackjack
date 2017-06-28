package inteligencia_artificial;

/**
 * Created by lucas on 27/06/17.
 */
public class Card {

    private final Suit suit;
    private final Rank rank;

    /**
     * Enum-based constructor
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public Card (Suit suit, Rank rank) {

        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Integer-based constructor
     * @param suit the suit index of the card
     * @param rank the rank index of the card
     */
    public Card (int suit, int rank) {

        this.suit = suitFromInt(suit);
        this.rank = rankFromInt(rank);
    }

    /**
     * @return the suit of the card
     */
    public Suit getSuit () {
        return suit;
    }

    /**
     * @return the rank of the card
     */
    public Rank getRank () {
        return rank;
    }

    public boolean isHighCard () {

        return this.baseValue() > 9 || this.rank == Rank.Ace;
    }

    public boolean isLowCard () {

        return this.baseValue() < 7 && this.rank != Rank.Ace;
    }

    public int baseValue () {

        switch (rank) {
            case Deuce: return 2;
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
            case Ace: return 1;
            default: throw new RuntimeException("Invalid Rank");
        }
    }

    /**
     *
     */
    @Override
    public Card clone () {
        return new Card(this.suit, this.rank);
    }

    /**
     * The String-representation of the Card
     */
    public String toString () {
        return suitAsString() + rankAsString();
    }

    // private
    private static Suit suitFromInt (int i) {

        switch (i) {
            case 0: return Suit.Trebol;
            case 1: return Suit.Diamantes;
            case 2: return Suit.Corazones;
            case 3: return Suit.Picas;
            default: throw new RuntimeException("Invalid Suit Index");
        }
    }

    // private
    private static Rank rankFromInt (int i) {

        switch (i) {
            case 0: return Rank.Deuce;
            case 1: return Rank.Three;
            case 2: return Rank.Four;
            case 3: return Rank.Five;
            case 4: return Rank.Six;
            case 5: return Rank.Seven;
            case 6: return Rank.Eight;
            case 7: return Rank.Nine;
            case 8: return Rank.Ten;
            case 9: return Rank.Jack;
            case 10: return Rank.Queen;
            case 11: return Rank.King;
            case 12: return Rank.Ace;
            default: throw new RuntimeException("Invalid Rank Index");
        }
    }

    // private
    private String suitAsString () {

        switch (suit) {
            case Trebol: return "♣";
            case Diamantes: return "♦";
            case Corazones: return "♥";
            case Picas: return "♠";
            default: throw new RuntimeException("Invalid Suit");
        }
    }

    // private
    private String rankAsString () {

        switch (rank) {
            case Deuce: return "2";
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
            case Ace: return "A";
            default: throw new RuntimeException("Invalid Rank");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {

            Card other = (Card)obj;

            return other.rank == this.rank && other.suit == this.suit;
        }

        return false;
    }

    @Override
    public int hashCode () {
        return baseValue();
    }

    String[] VALUES = {
            "As", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "J", "Q", "K"
    };


    public String getCard() {
        return VALUES[value].toString() + " de " + SUIT[suit].toString();
    }

    public int getCardValue(){
        int cardValue = 0;
        for(int i = 0; i < VALUES.length; i ++){
            switch ( i ) {
                case 0: cardValue = 1;
                case 2: cardValue = 2;
                case 3: cardValue = 3;
                case 4: cardValue = 4;
                case 5: cardValue = 5;
                case 6: cardValue = 6;
                case 7: cardValue = 7;
                case 8: cardValue = 8;
                case 9: cardValue = 9;
                case 10: cardValue = 10;
                case 11: cardValue = 10;
                case 12: cardValue = 10;
                case 13: cardValue = 10;
            }
        }
        return cardValue;
    }

    @Override
    public Card clone () {
        return new Card(this.suit, this.value);
    }

    // private
    private String suitAsString () {

        switch (suit) {
            case Clubs: return "♣";
            case Diamonds: return "♦";
            case Hearts: return "♥";
            case Spades: return "♠";
            default: throw new RuntimeException("Invalid Suit");
        }
    }

    // private
    private static Rank rankFromInt (int i) {

        switch (i) {
            case 0: return Rank.Deuce;
            case 1: return Rank.Three;
            case 2: return Rank.Four;
            case 3: return Rank.Five;
            case 4: return Rank.Six;
            case 5: return Rank.Seven;
            case 6: return Rank.Eight;
            case 7: return Rank.Nine;
            case 8: return Rank.Ten;
            case 9: return Rank.Jack;
            case 10: return Rank.Queen;
            case 11: return Rank.King;
            case 12: return Rank.Ace;
            default: throw new RuntimeException("Invalid Rank Index");
        }
    }

    // private
    private String rankAsString () {

        switch (rank) {
            case Deuce: return "2";
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
            case Ace: return "A";
            default: throw new RuntimeException("Invalid Rank");
        }
    }
}
