package ar.edu.itba.ss;

import java.util.Map;
import java.util.List;

public class Config {
    private Double mass;
    private Double velocity;
    private Integer N;
    private Integer max_frames;

    public Config(Double mass, Double velocity, Integer N, Integer max_frames) {
        this.mass = mass;
        this.velocity = velocity;
        this.N = N;
        this.max_frames = max_frames;
    }

    @Override
    public String toString() {
        return "\nConfig{" +
                "N: " + N + ",\n" +
                "mass: " + mass + ",\n" +
                "velocity: " + velocity + ",\n" +
                "max_frames: " + max_frames + ",\n"
                +
                '}';
    }

    public Double getMass() {
        return mass;
    }

    public Double getVelocity() {
        return velocity;
    }

    public Integer getN() {
        return N;
    }

    public Integer getMaxFrames() {
        return max_frames;
    }
}
