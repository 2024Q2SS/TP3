package ar.edu.itba.ss;

import java.util.HashMap;
import java.util.Map;

public class Board{

    private Map<Coordinates, Particle> particles = new HashMap<>();
    private Double length = 0.1;
    private Double obstacleRadius = 0.005;
    private Double particleRadius = 0.001;
    private String configPath = "../config.json";
    private final static String rootDir = System.getProperty("user.dir");
    private Double mass;
    private Double velocity;
    private Integer N;
    
    public Board(Double mass, Double velocity, Integer N){
        this.mass = mass;
        this.velocity = velocity;
        this.N = N;
        initialize();
    }
    
    public Boolean notInsideObstacle(Coordinates position){
        Double dx = position.getX() - 0.1*0.5;
        Double dy = position.getY() - 0.1*0.5;
        Double distanceSq = dx*dx + dy*dy;

        return distanceSq - obstacleRadius - particleRadius > 0;
    }
    


    public Coordinates generateCoord(){
        Double min = 0.0;
        Double max = length;
        Coordinates aux;
        do{
            aux = new Coordinates(Math.random()*(max-min+1)+min,Math.random()*(max-min+1)+min);
        }
        while(aux.getX() != min && aux.getX() != max &&
            aux.getY() != min && aux.getY() != max &&
            notInsideObstacle(aux) && notOnOtherParticle(aux));
        return aux;
    }

    public void initialize(){
        for(int i = 0; i < N; i++){
            Coordinates coord = generateCoord();
            Particle aux = new Particle(i,coord,particleRadius,velocity,mass,Math.random()*360);
            particles.put(coord,aux);
        }
    }

    public Map<Coordinates,Particle> getParticles(){
        return particles;
    }

}
