import javax.sound.sampled.*;
import java.net.URL;

// Utility sederhana untuk memutar BGM dan sound effect (bonus)
public class SoundPlayer {
    private static Clip bgm;

    // Putar sound effect satu kali
    public static void play(String fileName) {
        try {
            URL url = SoundPlayer.class.getResource("assets/" + fileName);
            if (url == null) return;
            AudioInputStream in = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) clip.close();
            });
            clip.start();
        } catch (Exception e) {
            // audio gagal tidak boleh membuat game crash
        }
    }

    // Putar BGM berulang
    public static void startBgm(String fileName) {
        try {
            stopBgm();
            URL url = SoundPlayer.class.getResource("assets/" + fileName);
            if (url == null) return;
            AudioInputStream in = AudioSystem.getAudioInputStream(url);
            bgm = AudioSystem.getClip();
            bgm.open(in);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
        }
    }

    public static void stopBgm() {
        if (bgm != null) {
            bgm.stop();
            bgm.close();
            bgm = null;
        }
    }
}
