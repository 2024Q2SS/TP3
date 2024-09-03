package ar.edu.itba.ss;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.nio.file.Paths;

import com.google.gson.Gson;

public class App {
    private static Board board;
    private static Config config;
    private String configPath = "../config.json";
    private final static String rootDir = System.getProperty("user.dir");

    public void setup() {
        if (!Paths.get(configPath).isAbsolute()) {

            configPath = Paths.get(rootDir, configPath).toString();

        }

        try (FileReader reader = new FileReader(configPath)) {
            config = new Gson().fromJson(reader, Config.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        board = new Board(config.getMass(), config.getVelocity(), config.getN());
    }

    public static void main(String[] args) {
        App app = new App();
        app.setup();
        System.out.println(config);

        String path = Paths.get(rootDir, "output.csv").toString();
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            writer.println("p_id,p_x,p_y");
            for (Particle particle : board.getParticles().values()) {
                writer.println(particle.getId() + "," + particle.getCoordinates().getX() + ","
                        + particle.getCoordinates().getY());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
