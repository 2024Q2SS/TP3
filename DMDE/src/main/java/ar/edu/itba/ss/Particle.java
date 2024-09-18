package ar.edu.itba.ss;

public class Particle implements Comparable<Particle> {
    private Coordinates coordinates;
    private Integer id;
    private Double radius;
    private Double mass;
    private Double vx;
    private Double vy;
    private int collisions;
    private Boolean movable;
    private Boolean obstacle;

    public Particle(Integer id, Double radius, Double vx, Double vy, Double mass) {
        this.id = id;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.obstacle = false;
        this.movable = false;
    }

    public Particle(Integer id, Coordinates coordinates, Double radius, Double vx, Double vy, Double mass) {
        this.coordinates = coordinates;
        this.id = id;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.obstacle = false;
        this.movable = false;
    }

    public Particle(Integer id, Coordinates coordinates, Double radius, Double vx, Double vy, Double mass,
            Boolean movable) {
        this.coordinates = coordinates;
        this.id = id;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.movable = movable;
        this.obstacle = true;
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
        if (this.isObstacle()) {
            if (this.isMovable()) {
                this.vx = vx;
            }
        } else
            this.vx = vx;
    }

    public Double getVy() {
        return vy;
    }

    public void setVy(Double vy) {
        if (this.isObstacle()) {
            if (this.isMovable()) {
                this.vy = vy;
            }
        } else
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

    public double collides(Particle b) { // cambiarlo a object pero que sea particle o obstacle?
        double drx = b.getCoordinates().getX() - this.getCoordinates().getX();
        double dry = b.getCoordinates().getY() - this.getCoordinates().getY();
        double dvx = b.getVx() - this.getVx();
        double dvy = b.getVy() - this.getVy();

        double sigma2 = Math.pow(this.getRadius() + b.getRadius(), 2);

        double dr_d_dr = (drx * drx) + (dry * dry);
        double dr_d_dv = (drx * dvx) + (dry * dvy);
        double dv_d_dv = (dvx * dvx) + (dvy * dvy);

        double distance = Math.pow(dr_d_dv, 2) - dv_d_dv * (dr_d_dr - sigma2);

        if (dr_d_dv >= 0)
            return -1;
        if (distance < 0)
            return -1;
        return -((dr_d_dv + Math.sqrt(distance)) / dv_d_dv);
    }

    public void bounceX() {
        Double aux = -1 * this.getVx();
        this.setVx(aux);
        // if ((Math.pow(vx, 2) + Math.pow(vy, 2)) - 1 <= 0.000001)
        // System.out.println("se rompio con bounceY");
        this.increaseCollision();
    }

    public void bounceY() {
        Double aux = -1 * this.getVy();
        this.setVy(aux);
        // if ((Math.pow(vx, 2) + Math.pow(vy, 2)) - 1 <= 0.000001)
        // System.out.println("se rompio con bounceX");
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
        double drx = b.getCoordinates().getX() - this.getCoordinates().getX();
        double dry = b.getCoordinates().getY() - this.getCoordinates().getY();
        double dvx = b.getVx() - this.getVx();
        double dvy = b.getVy() - this.getVy();

        double sigma2 = Math.pow(this.getRadius() + b.getRadius(), 2);
        double dr_d_dv = (drx * dvx) + (dry * dvy);

        double j;
        if (b.isObstacle() && !b.isMovable()) {
            // Si B es un obstáculo, consideramos que tiene masa infinita, por lo que solo
            // this cambia su velocidad
            j = (2 * this.getMass() * dr_d_dv) / (Math.sqrt(sigma2));
        } else {
            // Colisión entre dos partículas movibles
            j = (2 * this.getMass() * b.getMass() * dr_d_dv)
                    / (Math.sqrt(sigma2) * (this.getMass() + b.getMass()));
        }

        double jx = (j * drx) / Math.sqrt(sigma2);
        double jy = (j * dry) / Math.sqrt(sigma2);

        double vxi = this.getVx() + (jx / this.getMass());
        double vyi = this.getVy() + (jy / this.getMass());
        this.increaseCollision();
        this.setVx(vxi);
        this.setVy(vyi);

        if (!b.isObstacle() || (b.isObstacle() && b.isMovable())) {
            double vxj = b.getVx() - (jx / b.getMass());
            double vyj = b.getVy() - (jy / b.getMass());
            b.increaseCollision();
            b.setVx(vxj);
            b.setVy(vyj);
        }
        // if ((Math.pow(vx, 2) + Math.pow(vy, 2)) - 1 <= 0.000001)
        // System.out.println("se rompio con bounce");
        //
    }

    public int getCollisionCount() {
        return collisions;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public boolean isMovable() {
        return movable;
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
