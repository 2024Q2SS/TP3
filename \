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
        if (this.getVx() > 0) {
            return (0.1 - this.getRadius() - this.getCoordinates().getX()) / this.getVx();
        } else if (this.getVx() < 0) {
            return (this.getRadius() - this.getCoordinates().getX()) / this.getVx();
        }
        return -1;
    }

    public double collidesY() { // predice tiempo a colision con pared HORIZONTAL
        if (this.getVy() > 0) {
            return (0.1 - this.getRadius() - this.getCoordinates().getY()) / this.getVy();
        } else if (this.getVy() < 0) {
            return (this.getRadius() - this.getCoordinates().getY()) / this.getVy();
        }
        return -1;
    }

    public double collides(Particle b) {
        double drx = b.getCoordinates().getX() - this.getCoordinates().getX();
        double dry = b.getCoordinates().getY() - this.getCoordinates().getY();
        double dvx = b.getVx() - this.getVx();
        double dvy = b.getVy() - this.getVy();
        double sigma2 = Math.pow(this.getCoordinates().getX() - b.getCoordinates().getX(), 2)
                + Math.pow(this.getCoordinates().getY() - b.getCoordinates().getY(), 2);

        double distance = Math.pow(drx * dvx + dry * dvy, 2)
                - ((dvx * dvx + dvy * dvy) * ((drx * drx + dry * dry) - sigma2)); // Check
                                                                                  // collision
                                                                                  // based
                                                                                  // on
                                                                                  // radii
                                                                                  // sum

        if ((dvx * drx + dvy * dry) >= 0)
            return -1;
        if (distance < 0)
            return -1;
        return -(((drx * dvx + dvy * dry) + Math.sqrt(distance)) / (dvx * dvx + dvy * dvy));
    }

    public void bounceX() {
        Double aux = -1 * this.getVx();
        this.setVx(aux);
        if ((Math.pow(vx, 2) + Math.pow(vy, 2)) != 1)
            System.out.println("se rompio con bounceY");
        this.increaseCollision();
    }

    public void bounceY() {
        Double aux = -1 * this.getVy();
        this.setVy(aux);
        if ((Math.pow(vx, 2) + Math.pow(vy, 2)) != 1)
            System.out.println("se rompio con bounceX");
        this.increaseCollision();
    }

    public void updatePosition(double time) {
        Double x = this.getCoordinates().getX() + this.getVx() * time;
        Double y = this.getCoordinates().getY() + this.getVy() * time;
        this.getCoordinates().setX(x);
        this.getCoordinates().setY(y);

    }

    public void increaseCollision() {
        this.collisions++;
    }

    public void bounce(Particle b) {
        double drx = b.getCoordinates().getX() - this.coordinates.getX();
        double dry = b.getCoordinates().getY() - this.coordinates.getY();
        double dvx = b.getVx() - this.getVx();
        double dvy = b.getVy() - this.getVy();
        double sigma2 = Math.pow(this.getCoordinates().getX() - b.getCoordinates().getX(), 2)
                + Math.pow(this.getCoordinates().getY() - b.getCoordinates().getY(), 2);

        double j = ((2 * this.getMass() * b.getMass()) * (dvx * drx + dvy * dry))
                / ((drx * drx + dry * dry) * (this.getMass() + b.getMass()));

        double jx = (j * drx) / Math.sqrt(sigma2);
        double jy = (j * dry) / Math.sqrt(sigma2);

        double vxi = this.getVx() + jx / this.getMass();
        double vyi = this.getVy() + jy / this.getMass();

        double vxj = b.getVx() - jx / b.getMass();
        double vyj = b.getVy() - jy / b.getMass();
        this.increaseCollision();
        b.increaseCollision();
        this.setVx(vxi);
        this.setVy(vyi);

        b.setVx(vxj);
        b.setVy(vyj);

        if ((Math.pow(vx, 2) + Math.pow(vy, 2)) != 1)
            System.out.println("se rompio con bounce");

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
