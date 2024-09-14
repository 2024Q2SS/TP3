package ar.edu.itba.ss;

import java.util.Set;
import java.util.TreeSet;

import java.util.Map;
import java.util.PriorityQueue;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Board {

    // private Map<Coordinates, Cell> cellMap = new HashMap<>();
    private PriorityQueue<Event> eventQueue = new PriorityQueue<>();
    private Double interactionRadius;
    private Set<Particle> particles = new TreeSet<>();
    private Double length = 0.1;
    private Double obstacleRadius = 0.005;
    private Double particleRadius = 0.001;
    private String configPath = "../config.json";
    private final static String rootDir = System.getProperty("user.dir");
    private PrintWriter output;
    private Double mass;
    private Double velocity;
    private Integer N;
    private Integer max_frames;

    public Board(Double mass, Double velocity, Integer N, Integer max_frames) {
        this.mass = mass;
        this.velocity = velocity;
        this.N = N;
        this.max_frames = max_frames;

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
        for (Particle other : particles) {
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
        do {
            aux = new Coordinates(Math.random() * (max - min) + min, Math.random() * (max - min) + min);
            System.out.println("looking for new coords");
        } while (aux.getX() - particleRadius < min || aux.getX() + particleRadius > max ||
                aux.getY() - particleRadius < min || aux.getY() + particleRadius > max ||
                !notInsideObstacle(aux) || !notOnOtherParticle(aux));
        return aux;
    }

    public void initialize() {
        for (int i = 0; i < N; i++) {
            Coordinates coord = generateCoord();
            Double vx = 0.0, vy = 0.0;
            while ((Math.pow(vx, 2) + Math.pow(vy, 2)) != 1) {
                Integer neg = Math.random() > 0.5 ? -1 : 1;
                vx = Math.random() * neg;
                neg = Math.random() > 0.5 ? -1 : 1;
                vy = Math.sqrt(1 - Math.pow(vx, 2));
            }
            Particle aux = new Particle(i, coord, particleRadius, vx, vy, mass);
            System.out.println("created particle " + i);
            particles.add(aux);
        }
        recalculateCollisions(particles);
    }

    public void recalculateCollisions(Set<Particle> needRecalculation) {
        double t = -1;
        for (Particle particle : needRecalculation) {
            t = particle.collidesX(); // si no hay colision con una pared vertical => devuelve -1
            if (t > 0) {
                Event aux = new Event(t, particle, null);
                eventQueue.add(aux);
            } // si no va a colisionar con paredes verticales, entonces me fijo contra
              // horizontales
            t = particle.collidesY();
            if (t > 0) {
                Event aux = new Event(t, null, particle);
                eventQueue.add(aux);
            }
            for (Particle other : particles) {

                if (other.getId() == particle.getId())
                    continue;
                t = particle.collides(other);
                if (t > 0) {
                    Event aux = new Event(t, particle, other);
                    eventQueue.add(aux);
                }
            }
        }

    }

    public void updateParticles(double time) {
        for (Particle p : particles) {
            p.updatePosition(time);
        }
    }

    public void updateBoard() {
        // Set<Particle> toRecalc = new HashSet<>();
        String path = Paths.get(rootDir, "output.csv").toString();
        int count = 0;
        try (PrintWriter csvWriter = new PrintWriter(new FileWriter(path))) {
            csvWriter.println("id,x,y");
            Boolean invalid = false;
            for (Particle p : particles) {
                csvWriter.println(p.getId() + "," + p.getCoordinates().getX() + "," + p.getCoordinates().getY());
            }
            while (!eventQueue.isEmpty() && count < max_frames) {
                Event e1 = eventQueue.remove();
                Particle a = e1.getA();
                Particle b = e1.getB();
                if (e1.isInvalidated(a, b)) {
                    /*
                     * if (a != null) {
                     * toRecalc.add(a);
                     * }
                     * if (b != null) {
                     * 
                     * toRecalc.add(b);
                     * }
                     */
                    invalid = true;
                } else {
                    if (a != null) {
                        if (b != null) { // ambos son distintos de null
                            updateParticles(e1.getTime());
                            a.bounce(b);
                            // toRecalc.add(a);
                            // toRecalc.add(b);

                        } else {
                            updateParticles(e1.getTime());
                            a.bounceX();
                            // toRecalc.add(a);
                        }
                    } else if (b != null) {
                        updateParticles(e1.getTime());
                        b.bounceY();
                        // toRecalc.add(b);
                    }

                }
                if (!invalid) {
                    for (Particle p : particles) {
                        csvWriter
                                .println(p.getId() + "," + p.getCoordinates().getX() + "," + p.getCoordinates().getY());
                    }

                }
                recalculateCollisions(particles); // recalculamos solo para las que estuvieron en colisiones
                // toRecalc.clear();
                count++;
                invalid = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Particle> getParticles() {
        return particles;
    }

}
