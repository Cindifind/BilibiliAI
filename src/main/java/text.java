import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class text {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("E:\\clinebilibli\\siliconcloud-generated-speech.mp3")) {
            Player player = new Player(fis);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
