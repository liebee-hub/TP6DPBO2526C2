import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    int playerStartPosX = frameWidth / 2;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;
    int pipeVelocityX = -2;

    View view;
    Image birdImage;
    Player player;

    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    // State permainan
    int score = 0;
    boolean gameOver = false;

    public Logic() {
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);

        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                placePipes();
            }
        });

        gameLoop = new Timer(1000 / 60, this);
    }

    // Dipanggil setelah View terpasang di window
    public void start() {
        pipesCooldown.start();
        gameLoop.start();
        SoundPlayer.startBgm("bgm.wav");
    }

    public void setView(View view) { this.view = view; }
    public Player getPlayer() { return player; }
    public ArrayList<Pipe> getPipes() { return pipes; }
    public int getScore() { return score; }
    public boolean isGameOver() { return gameOver; }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - (pipeHeight / 4) - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + openingSpace + pipeHeight,
                pipeWidth, pipeHeight, lowerPipeImage);
        // Pipa bawah ditandai "sudah dilewati" agar skor hanya dihitung 1x per pasang pipa
        lowerPipe.setPassed(true);
        pipes.add(lowerPipe);
    }

    public void move() {
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            // Skor +1 saat pipa atas sepenuhnya melewati posisi player
            if (!pipe.isPassed() && pipe.getPosX() + pipe.getWidth() < player.getPosX()) {
                pipe.setPassed(true);
                score++;
                SoundPlayer.play("point.wav");
                if (view != null) view.setScore(score);
            }
        }

        // Buang pipa yang sudah keluar layar agar list tidak membengkak
        pipes.removeIf(p -> p.getPosX() + p.getWidth() < 0);
    }

    // Cek tabrakan dengan pipa atau jatuh ke dasar window
    public boolean checkGameOver() {
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            return true;
        }
        for (int i = 0; i < pipes.size(); i++) {
            if (player.getBounds().intersects(pipes.get(i).getBounds())) {
                return true;
            }
        }
        return false;
    }

    public void endGame() {
        gameOver = true;
        gameLoop.stop();
        pipesCooldown.stop();
        SoundPlayer.stopBgm();
        SoundPlayer.play("hit.wav");
    }

    public void restart() {
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        pipes.clear();
        score = 0;
        gameOver = false;
        if (view != null) view.setScore(0);
        start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (checkGameOver()) {
            endGame();
        }
        if (view != null) view.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!gameOver && (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP)) {
            player.setVelocityY(-10);
            SoundPlayer.play("flap.wav");
        } else if (gameOver && key == KeyEvent.VK_R) {
            restart();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
