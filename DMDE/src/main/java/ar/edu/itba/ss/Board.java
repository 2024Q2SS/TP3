package ar.edu.itba.ss;

import java.util.HashMap;
import java.util.Map;

public class Board{

    private Map<Coordinates, Particle> particles = new HashMap<>();
    
    public Board(){

    }



    public Map<Coordinates,Particle> getParticles(){
        return particles;
    }

}
