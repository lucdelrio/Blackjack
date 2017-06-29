package inteligencia_artificial;

public class Program {

    public static void main(String[] args) {
        
        int learning            = 100000;
        double startEpsilon     = 0.5;
        double endEpsilon       = 0.05;
        int games               = 500;
       
        QLearningPolicy qPolicy = new QLearningPolicy(false);
        Jugador agent = new Jugador(0, "Humano" , qPolicy );
        
        System.out.println("******** Training ********");
        
        double epsilon = startEpsilon;
        while (epsilon > endEpsilon) {
            
            System.out.println("Starting trials of " + learning + " episodes:");
            System.out.println("Epsilon: " + epsilon);
            qPolicy.setEpsion(epsilon);
            trainAgent(agent, qPolicy, learning);
            
            epsilon -= 0.1;
        }
        
        System.out.println("******** Training Complete ********");
        
        System.out.println("********* Q-policy Derived after " + learning + " trials *********");
        
        qPolicy.printQValues();
        qPolicy.setEpsion(0.0);
        
        System.out.println("********* Jugando con " + games + " manos *********");
        
        playRealgames(agent, qPolicy, games);
    }
    
    public static void trainAgent (Jugador agent, QLearningPolicy policy, int ITERATIONS) {
        
        Juego episode = new Juego(agent);

        for (int i=0; i < ITERATIONS; i++) {
            

            episode = Juego.playRound(episode);
            
            episode.printResult();
            episode.reset();
            if (episode.isLowOnCards()) {
                episode.redeck();
            }
            
            //policy.printQValues();
        }
    }
    
    public static void playRealgames (Jugador agent, QLearningPolicy policy, int ITERATIONS) {
        
        int totalBet = 1 * ITERATIONS;
        int totalReward = 0;
        int wins = 0;
        int losses = 0;
        int draws = 0;
        
        Juego g = new Juego(agent);
        for (int i=0; i < ITERATIONS; i++) {
            
            
            g = Juego.playRound(g);
            
            // notify players
            //g.printResult();
            int reward = g.getReward(agent);
            
            if (reward > 0) { wins++; }
            else if (reward < 0) { losses++; }
            else { draws++; }
            
            totalReward += g.getReward(agent);
            
            g.reset();
            if (g.isLowOnCards()) {
                g.redeck();
            }
        }
        
        System.out.println("**** Total Apostado: $" + totalBet);
        System.out.println("**** Total Ganado: $" + totalReward);
        System.out.println("**** Total Gana: " + wins);
        System.out.println("**** Total Pierde: " + losses);
        System.out.println("**** Total Empata: " + draws);
    }
}