package edu.unc.comp301.adapter;

import java.io.InputStream;
import java.util.Properties;

public class Main {
  public static void main(String[] args) {
    System.out.println("Assn05-Adapter");

    // OpenRoutesService API Key
    Properties cfg = new Properties();
    try (InputStream in = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
      cfg.load(in);
    } catch (Exception e) {
      System.out.println(e);
    }
    String apiKey = cfg.getProperty("ors.api.key");

  }
}
