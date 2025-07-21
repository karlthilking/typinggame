package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.AudioOptions;

public class AudioPlayer {
  private AudioOptions options;
  private Clip backgroundMusic;
  private AudioInputStream song;
  private File soundEffect;
  private Clip actionSound;

  public AudioPlayer() {
    backgroundMusic = null;
    song = null;
    options = new AudioOptions();
  }

  public void playSong(String filePath) {
    File songFile = Paths.get(filePath).toFile();
    try {
      song = AudioSystem.getAudioInputStream(songFile);
      backgroundMusic = AudioSystem.getClip();
      backgroundMusic.open(song);
      backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
      backgroundMusic.start();
    } catch (UnsupportedAudioFileException |LineUnavailableException |IOException e){
      System.out.println("Error: " + e.getMessage());
    }
  }

  public void playSound() {
    try {
      AudioInputStream sound = AudioSystem.getAudioInputStream(soundEffect);
      actionSound = AudioSystem.getClip();
      actionSound.open(sound);
      actionSound.start();
    }
    catch( UnsupportedAudioFileException | LineUnavailableException | IOException e) {
      System.out.println("Error");
    }
  }

  public void selectSound(String sound) {
    soundEffect = options.chooseSoundEffect(sound);
  }

}
