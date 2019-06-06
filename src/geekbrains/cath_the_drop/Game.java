package geekbrains.cath_the_drop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends JFrame {

    private static Game game_window;

    private static long last_frame_time;

    private static Image background;
    private static Image drop;
    private static Image g_ower;
    private static float drop_left = 200;
    private static float drop_top = -100;
    private static float drop_v = 200;
    private static int count = 0;

    public static void main(String[] args) throws IOException {
        background = ImageIO.read(Game.class.getResourceAsStream("background.png"));
        drop = ImageIO.read(Game.class.getResourceAsStream("drop.png"));
        g_ower = ImageIO.read(Game.class.getResourceAsStream("game_over.png"));
        game_window  = new Game();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(200, 100);
        game_window.setSize(906, 478);
        game_window.setResizable(false);
        last_frame_time = System.nanoTime();
        GameField game_Field = new GameField();
        game_Field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float drop_right = drop_left + drop.getWidth(null);
                float drop_bot = drop_top + drop.getHeight(null);
                boolean is_hit = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bot;
                if(is_hit){
                    drop_top = -100;
                    drop_left = (int) (Math.random() * (game_Field.getWidth() - drop.getWidth(null)));
                    drop_v += 20;
                    ++count;
                    game_window.setTitle("Score: " + count);
                }
            }
        });
        game_window.add(game_Field);
        game_window.setVisible(true);
    }

    private static void onRepaint(Graphics g){
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;

        drop_top += drop_v * delta_time;
        g.drawImage(background, 0, 0, null);
        g.drawImage(drop, (int) drop_left, (int) drop_top, null);
        if(drop_top >= game_window.getHeight())  g.drawImage(g_ower, 280, 120, null);
    }

    private static class GameField extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
