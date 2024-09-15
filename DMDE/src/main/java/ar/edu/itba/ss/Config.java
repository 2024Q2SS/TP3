package ar.edu.itba.ss;

import java.util.Map;
import java.util.List;

public class Config {
    private Double mass;
    private Double velocity;
    private Integer N;
    private Integer max_frames;
    private Boolean movable_obs;
    private Double obstacle_mass;

    public Config(Double mass, Double velocity, Integer N, Integer max_frames, Boolean movable_obs,
            Double obstacle_mass) {
        this.mass = mass;
        this.velocity = velocity;
        this.N = N;
        this.max_frames = max_frames;
        this.movable_obs = movable_obs;
        this.obstacle_mass = obstacle_mass;
    }

    @Override
    public String toString() {
        return "\nConfig{" +
                "N: " + N + ",\n" +
                "mass: " + mass + ",\n" +
                "velocity: " + velocity + ",\n" +
                "max_frames: " + max_frames + ",\n" +
                "movable_obs: " + movable_obs + ",\n" +
                "obstacle_mass: " + obstacle_mass + ",\n" +
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

    public Boolean getMovable() {
        return movable_obs;
    }

    public Double getObstacleMass() {
        return obstacle_mass;
    }
}
