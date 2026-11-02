import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flappy Bird");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            MainMenu menu = new MainMenu(
                    () -> startGame(frame),
                    () -> System.exit(0)
            );
            frame.setContentPane(menu);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void startGame(JFrame frame) {
        Logic logic = new Logic();
        View view = new View(logic);
        logic.setView(view);

        frame.setContentPane(view);
        frame.revalidate();
        frame.repaint();
        view.requestFocusInWindow();
        logic.start();
    }
}
