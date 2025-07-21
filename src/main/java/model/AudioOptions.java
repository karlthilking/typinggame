package model;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


public class AudioOptions {
  private Map<String, File> soundEffects;

  public AudioOptions() {
    this.soundEffects = Map.of(
            "osu", Paths.get("osu.wav").toFile(),
            "minecraft", Paths.get("minecraft.wav").toFile()
    );
  }

  public File chooseSoundEffect(String fileName) {
    if (soundEffects.containsKey(fileName)) {
      return soundEffects.get(fileName);
    } else {
      throw new IllegalArgumentException("Sound effect not found");
    }
  }

}
