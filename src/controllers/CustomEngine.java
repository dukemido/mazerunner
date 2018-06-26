/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Muhammad
 */
public class CustomEngine {

    public static Random Rnd = new Random();// One random to avoid same numbers repeating.

    public static BufferedImage readFile(String path) {
        try {
            File test = new File(path);
            BufferedImage img = ImageIO.read(test);
            return img;
        } catch (IOException ex) {
            System.out.println("Invalid path for image.");
        }
        return null;
    }

    public static void playAudio(String path) {
        try {
            // Open an audio input stream.           
            File soundFile = new File(path); //you could also get the sound file with an URL
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            System.out.println("Invalid path for audio.");
        }

    }
}
