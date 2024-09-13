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

        board = new Board(config.getMass(), config.getVelocity(), config.getN(), config.getMaxFrames());
    }

    public static void main(String[] args) {
        App app = new App();
        app.setup();
        System.out.println(config);
        board.updateBoard();
    }
}
