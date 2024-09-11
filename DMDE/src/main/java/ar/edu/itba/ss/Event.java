package ar.edu.itba.ss;


public class Event{
    private Particle a;
    private Particle b;
    private double t;
    private int aCollisionCount;
    private int bCollisionCount;

    public Event(double t, Particle a, Particle b){
        this.a = a;
        this.b = b;
        this.t = t;
        this.aCollisionCount = a.getCollisionCount();
        this.bCollisionCount = b.getCollisionCount();
    }
    
    //si ambas son null -> invalido
    //si ninguna es null colision de particulas
    //si a es null pero b no entonces colison contra pared horizontal
    //si a no es null pero b si entonces colision contra pared vertical

    public Particle getA(){
        return a;
    }

    public Particle getB(){
        return b;
    }
    
    public double getTime(){
        return t;
    }
    
    public boolean isInvalidated(int currentA, int currentB){
        return currentA == aCollisionCount && currentB == bCollisionCount;
    }
}
