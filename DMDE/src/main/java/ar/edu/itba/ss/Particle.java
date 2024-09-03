package ar.edu.itba.ss;

public class Particle {
    private Coordinates coordinates;
    private Integer id;
    private Double radius;
    private Double mass;
    private Double velocity;
    private Double degree;

    public Particle(Integer id, Double radius, Double velocity, Double mass, Double degree) {
        this.id = id;
        this.radius = radius;
        this.velocity = velocity;
        this.mass = mass;
        this.degree = degree;
    }

    public Particle(Integer id, Coordinates coordinates, Double radius, Double velocity, Double mass, Double degree) {
        this.coordinates = coordinates;
        this.id = id;
        this.radius = radius;
        this.velocity = velocity;
        this.mass = mass;
        this.degree = degree;
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

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getDegree() {
        return degree;
    }

    public void setDegree(Double degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "{id:" + id + " " + coordinates;
    }
}
