package game;

import java.awt.Point;
import java.util.Scanner;

/**
 * M3 loop: player control + Gold & Door.
 * - Read one-char commands (W/A/S/D, Q)
 * - Move orthogonally by 1 cell with clamping
 * - Render every turn
 * - Pick up Gold (once), then win if reaching Door while holding gold
 * - Snake stays OFF for this milestone
 */
public class Game {
    private final Player player = new Player(1, 1);

    // Snake not implemented yet in M3 -> keep it off-board
    private static final Point OFF = new Point(-999, -999);

    // Gold & Door state for M3
    private final Gold gold = new Gold(5, 3);
    private final Door door = new Door(9, 1);

    public void run() {
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                clearScreen();

                // Render with a border and spacing; show gold only until picked up
                System.out.println(Board.renderWithBorder(
                        player.getPosition(),
                        /*snake*/ OFF,
                        /*gold*/ gold.positionOrNull(),
                        /*door*/ door.position(),
                        /*hasGold*/ gold.isCollected(),
                        /*spacing*/ 2));

                System.out.printf("Player: (%d,%d)  Gold: %s%n",
                        player.getPosition().x, player.getPosition().y,
                        gold.isCollected() ? "yes" : "no");

                // Read and apply one command
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


                // 1) First, pick up the gold (only once)
                if (gold.tryPickup(player.getPosition())) {
                    System.out.println("Picked up the gold!");
                    pause();
                }

                // 2) Then, if holding gold AND standing on the door -> win
                if (gold.isCollected() && door.isAt(player.getPosition())) {
                    System.out.println("You win! Reached the door with the gold.");
                    return; // end the game loop
                }
            }
        }
        System.out.println("Goodbye!");
    }

    /** Applies a relative move and clamps inside [0..WIDTH-1] x [0..HEIGHT-1]. */
    private void movePlayerBy(int dx, int dy) {
        // Note: getPosition() may return a live reference or a copy depending on your Player impl.
        var p  = player.getPosition();
        int nx = clamp(p.x + dx, 0, Board.WIDTH  - 1);
        int ny = clamp(p.y + dy, 0, Board.HEIGHT - 1);
        player.moveTo(nx, ny);
    }

    /** Integer clamp helper. */
    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    /** Small pause to let the user read messages. */
    private static void pause() {
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    /** Clears the console using ANSI; some Windows shells might ignore it (harmless). */
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}


