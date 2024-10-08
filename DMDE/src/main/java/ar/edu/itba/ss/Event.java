package ar.edu.itba.ss;

public class Event implements Comparable<Event> {
    private Particle a;
    private Particle b;
    private double t;
    private int aCollisionCount;
    private int bCollisionCount;

    public Event(double t, Particle a, Particle b) {
        this.a = a;
        this.b = b;
        this.t = t;
        this.aCollisionCount = 0;
        if (a != null)
            aCollisionCount = a.getCollisionCount();
        this.bCollisionCount = 0;
        if (b != null)
            bCollisionCount = b.getCollisionCount();
    }

    // si ambas son null -> invalido
    // si ninguna es null colision de particulas
    // si a es null pero b no entonces colison contra pared horizontal
    // si a no es null pero b si entonces colision contra pared vertical

    public Particle getA() {
        return a;
    }

    public Particle getB() {
        return b;
    }

    public double getTime() {
        return t;
    }

    public boolean isInvalidated(Particle a, Particle b) {

        return a != null
                ? (b != null ? (a.getCollisionCount() != aCollisionCount || b.getCollisionCount() != bCollisionCount)
                        : (a.getCollisionCount() != aCollisionCount))
                : (b.getCollisionCount() != bCollisionCount);
    }

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.getTime(), other.getTime());
    }

}
