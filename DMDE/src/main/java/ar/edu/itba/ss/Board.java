package ar.edu.itba.ss;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private Map<Coordinates, Particle> particles = new HashMap<>();
    private Double length = 0.1;
    private Double obstacleRadius = 0.005;
    private Double particleRadius = 0.001;
    private String configPath = "../config.json";
    private final static String rootDir = System.getProperty("user.dir");
    private Double mass;
    private Double velocity;
    private Integer N;

    public Board(Double mass, Double velocity, Integer N) {
        this.mass = mass;
        this.velocity = velocity;
        this.N = N;
        System.out.println("initializing board");
        initialize();
        System.out.println("finished initializing board");
    }

public Boolean notInsideObstacle(Coordinates position) {
    Double dx = position.getX() - 0.05; // Assuming obstacle center is at (0.05, 0.05)
    Double dy = position.getY() - 0.05;
    Double distanceSq = dx * dx + dy * dy;
    Double safeDistance = obstacleRadius + particleRadius + 0.0001; // Adding a small buffer
    return distanceSq >= safeDistance * safeDistance;
}

public Boolean notOnOtherParticle(Coordinates position) {
    if (particles.isEmpty()) {
        return true;
    }
    for (Particle other : particles.values()) {
        Double distance = position.euclideanDistance(other.getCoordinates());
        if (distance < 2 * particleRadius + 0.0001) // Adding a small buffer
            return false;
    }
    return true;
}

    public Coordinates generateCoord() {
        Double min = 0.0;
        Double max = length;
        Coordinates aux;
        System.out.println("generating coord");
        do{
            aux = new Coordinates(Math.random() * (max - min) + min, Math.random() * (max - min) + min);
            System.out.println("looking for new coords");
        }
        while(  
            aux.getX() - particleRadius < min || aux.getX() + particleRadius > max ||
            aux.getY() - particleRadius < min || aux.getY() + particleRadius > max ||
            !notInsideObstacle(aux)  && !notOnOtherParticle(aux)
        );
        return aux;
    }

    public void initialize() {
        for (int i = 0; i < N; i++) {
            Coordinates coord = generateCoord();
            Particle aux = new Particle(i, coord, particleRadius, velocity, mass, Math.random() * 360);
            System.out.println("created particle "+ i);
            particles.put(coord, aux);
        }
    }

    public Map<Coordinates, Particle> getParticles() {
        return particles;
    }

}
