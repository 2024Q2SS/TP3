package ar.edu.itba.ss;

import java.util.Map;
import java.util.List;

public class Config {
    private Double mass;
    private Double velocity;
    private Integer N;

    
    public Config(Double mass, Double velocity, Integer N){
        this.mass = mass;
        this.velocity = velocity;
        this.N = N;
    }

        @Override
    public String toString() {
        return "\nConfig{" +
                "N: " + N + ",\n" +
                "mass: " + mass+",\n" +
                "velocity: " + velocity + ",\n"
                +
            '}';
    }

    public Double getMass(){
        return mass;
    }

    public Double getVelocity(){
        return velocity;
    }

    public Integer getN(){
        return N;
    }
}

