package ar.edu.itba.ss;

public class Particle implements Comparable<Particle> {
    private Coordinates coordinates;
    private Integer id;
    private Double radius;
    private Double mass;
    private Double vx;
    private Double vy;
    private int collisions;

    public Particle(Integer id, Double radius, Double vx, Double vy, Double mass) {
        this.id = id;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
    }

    public Particle(Integer id, Coordinates coordinates, Double radius, Double vx, Double vy, Double mass) {
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
        this.vx = vx;
    }

    public Double getVy() {
        return vy;
    }

    public void setVy(Double vy) {
        this.vy = vy;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public double collidesX() { // predice tiempo a colision con pared VERTICAL
        if (vx > 0) {
            return (0.1 - radius - coordinates.getX()) / vx;
        } else if (vy < 0) {
            return (radius - coordinates.getX()) / vx;
        }
        return -1;
    }

    public double collidesY() { // predice tiempo a colision con pared HORIZONTAL
        if (vy > 0) {
            return (0.1 - radius - coordinates.getY()) / vy;
        } else if (vy < 0) {
            return (radius - coordinates.getY()) / vy;
        }
        return -1;
    }

    public double collides(Particle b) {
        double drx = b.coordinates.getX() - this.coordinates.getX();
        double dry = b.coordinates.getY() - this.coordinates.getY();
        double dvx = b.getVx() - vx;
        double dvy = b.getVy() - vy;

        double distance = Math.pow(drx * dvx + dry * dvy, 2)
                - ((dvx * dvx + dvy * dvy) * ((drx * drx + dry * dry) - radius * radius));

        if ((dvx * drx + dvy * dry) >= 0)
            return -1;
        if (distance < 0)
            return -1;
        return -(((drx * dvx + dvy * dry) + Math.sqrt(distance)) / (dvx * dvx + dvy * dvy));
    }

    public void bounceX() {
        Double aux = -1 * getVx();
        this.setVx(aux);
        this.increaseCollision();
    }

    public void bounceY() {
        Double aux = -1 * getVy();
        this.setVy(aux);
        this.increaseCollision();
    }

    public void updatePosition(double time) {
        Double x = coordinates.getX() + vx * time;
        Double y = coordinates.getY() + vy * time;
        this.coordinates.setX(x);
        this.coordinates.setY(y);

    }

    public void increaseCollision() {
        this.collisions++;
    }

    public void bounce(Particle b) {
        double drx = b.coordinates.getX() - this.coordinates.getX();
        double dry = b.coordinates.getY() - this.coordinates.getY();
        double dvx = b.getVx() - vx;
        double dvy = b.getVy() - vy;

        double j = ((2 * this.getMass() * b.getMass()) * (dvx * drx + dvy * dry))
                / (radius * (this.getMass() + b.getMass()));

        double jx = (j * (b.coordinates.getX() - this.coordinates.getX())) / this.radius;
        double jy = (j * (b.coordinates.getY() - this.coordinates.getY())) / this.radius;

        double vxi = this.coordinates.getX() + jx / this.getMass();
        double vyi = this.coordinates.getY() + jy / this.getMass();

        double vxj = b.coordinates.getX() + jx / b.getMass();
        double vyj = b.coordinates.getY() + jy / b.getMass();
        this.increaseCollision();
        b.increaseCollision();
        this.setVx(vxi);
        this.setVy(vyi);

        b.setVx(vxj);
        b.setVy(vyj);

    }

    public int getCollisionCount() {
        return collisions;
    }

    @Override
    public String toString() {
        return "id:" + id + " " + coordinates + " (" + vx + "," + vy + ")";
    }

    @Override
    public int compareTo(Particle other) {
        return Integer.compare(this.id, other.id);
    }
}
