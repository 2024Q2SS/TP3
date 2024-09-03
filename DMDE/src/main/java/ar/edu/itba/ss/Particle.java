package ar.edu.itba.ss;

public class Particle {
    private Coordinates coordinates;
    private Integer id;
    private Double radius;
    private Double mass;
    private Double vx;
    private Double vy;

    public Particle(Integer id, Double radius, Double vx,Double vy, Double mass) {
        this.id = id;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
    }

    public Particle(Integer id, Coordinates coordinates, Double radius, Double vx,Double vy, Double mass) {
        this.coordinates = coordinates;
        this.id = id;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
    }

    public void setCoordinate(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Integer getId() {
        return id;
    }

    public Double getRadius() {
        return radius;
    }

    public Double getVx() {
        return vx;
    }

    public void setVx(Double vx) {
        this.vx= vx;
    }
    
    public Double getVy() {
        return vy;
    }

    public void setVy(Double vy) {
        this.vy= vy;
    }
    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }


    @Override
    public String toString() {
        return "{id:" + id + " " + coordinates;
    }
}
