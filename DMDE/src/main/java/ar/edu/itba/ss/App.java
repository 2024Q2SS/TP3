package ar.edu.itba.ss;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import java.nio.file.Paths;

public class App {
    private static Board board;
    private static Config config;
    private String configPath = "../config.json";
    private final static String rootDir = System.getProperty("user.dir");
    private final static Gson gson = new Gson();

    public void setup(){
        if (!Paths.get(configPath).isAbsolute()) {
            configPath = Paths.get(rootDir, configPath).toString();
        }

        try (FileReader reader = new FileReader(configPath)) {
            config = gson.fromJson(reader, Config.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        board = new Board(config.getMass(),config.getVelocity(),config.getN());
    }


    public static void main(String[] args) {
        setup();
        
    }
}
