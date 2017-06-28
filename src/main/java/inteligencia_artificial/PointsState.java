package inteligencia_artificial;

public class PointsState implements Comparable<PointsState> {

    private int numberOfAces;
    private int points;
    private Accion accion;
    private int cardCount;
    
    public PointsState(int aces, int points, Accion accion) {
        this.numberOfAces = aces;
        this.points = points;
        this.accion = accion;
        this.cardCount = 0;
    }
    
    public PointsState(int aces, int points, Accion accion, int cardCount) {
        
        this.numberOfAces = aces;
        this.points = points;
        this.accion = accion;
        this.cardCount = cardCount;
    }
    
    public int getNumberOfAces() {
        return numberOfAces;
    }

    public int getPoints() {
        return points;
    }

    public Accion getAccion() {
        return accion;
    }
    
    @Override
    public String toString () {
        String str = "" + numberOfAces + "-" + points + "-" + accion.toString()
                   + cardCount;
        
        return str;
    }
    
    @Override
    public boolean equals (Object obj) {
        
        if (obj instanceof PointsState) {
            
            PointsState other = (PointsState)obj;
            
            return other.numberOfAces == this.numberOfAces &&
                   other.points == this.points && 
                   other.accion == this.accion &&
                   other.cardCount == this.cardCount;
        }
        
        return false;
    }
    
    @Override
    public int hashCode () {
        int n = accion == Accion.Pedir ? 10 : 20;
        
        return numberOfAces * 1000 + points * 100 + n + cardCount * 10000;
    }

    @Override
    public int compareTo(PointsState o) {
        
        PointsState other = (PointsState)o;
        
        if (this.numberOfAces < other.numberOfAces)
            return -1;
        else if (this.numberOfAces > other.numberOfAces)
            return 1;
        else {
            if (this.points < other.points)
                return -1;
            else if (this.points > other.points)
                return 1;
            else {
                
                if (this.cardCount < other.cardCount)
                    return -1;
                else if (this.cardCount > other.cardCount)
                    return 1;
                else {
                    if (this.accion == Accion.Pedir && other.accion == Accion.Plantarse)
                        return -1;
                    else if (this.accion == Accion.Plantarse && other.accion == Accion.Pedir)
                        return 1;
                }
            }
        }
        
        return 0;
    }
}
