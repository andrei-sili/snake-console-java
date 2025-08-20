package game;

import java.awt.Point;
import java.util.Scanner;

/**
 * Snake game loop: only player control; re-render each turn; clamp at edges.
 */
public class Game {
    private final Player player = new Player(1, 1);


    private static final Point OFF = new Point(-999, -999);

    public void run() {
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                clearScreen(); // purely aesthetic; safe to remove if your terminal ignores ANSI

                // Render with a border and a bit of spacing between cells for readability
                System.out.println(Board.renderWithBorder(
                        player.getPosition(), /*snake*/ OFF, /*gold*/ null, /*door*/ OFF,
                        /*hasGold*/ false, /*spacing*/ 2));

                Input.Command cmd = Input.readCommand(sc);

                switch (cmd) {
                    case UP    -> movePlayerBy(0, -1);
                    case DOWN  -> movePlayerBy(0,  1);
                    case LEFT  -> movePlayerBy(-1, 0);
                    case RIGHT -> movePlayerBy(1,  0);
                    case QUIT  -> running = false;
                    case NONE  -> {
                        System.out.println("Unknown command. Use W/A/S/D to move, Q to quit.");
                        pause();
                    }
                }
            }
        }
        System.out.println("Goodbye!");
    }

    /** Applies a relative move and clamps inside [0..WIDTH-1] x [0..HEIGHT-1]. */
    private void movePlayerBy(int dx, int dy) {
        var p  = player.getPosition(); // read current (copy)
        int nx = clamp(p.x + dx, 0, Board.WIDTH  - 1);
        int ny = clamp(p.y + dy, 0, Board.HEIGHT - 1);
        player.moveTo(nx, ny);
    }

    /** Integer clamp helper. */
    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    /** Small pause to let the user read error messages. */
    private static void pause() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    /** Clears the console using ANSI; some Windows shells might ignore it (harmless). */
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

