import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    public MainMenu(Runnable onStart, Runnable onExit) {
        setPreferredSize(new Dimension(360, 640));
        setBackground(new Color(112, 197, 206));
        setLayout(new GridBagLayout());

        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("FLAPPY BIRD");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = makeButton("Mulai Permainan");
        JButton exitButton = makeButton("Keluar");
        startButton.addActionListener(e -> onStart.run());
        exitButton.addActionListener(e -> onExit.run());

        box.add(title);
        box.add(Box.createVerticalStrut(50));
        box.add(startButton);
        box.add(Box.createVerticalStrut(15));
        box.add(exitButton);

        add(box);
    }

    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 20));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(220, 50));
        b.setPreferredSize(new Dimension(220, 50));
        b.setFocusPainted(false);
        return b;
    }
}
