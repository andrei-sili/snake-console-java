package game;

import java.awt.Point;
import java.util.Scanner;

/**
 * Player move + pickup + win, then Snake move + lose check.
 */
public class Game {
    private final Player player = new Player(1, 1);
    private final Snake  snake  = new Snake(8, 8);
    private final Gold gold = new Gold(5, 3);
    private final Door door = new Door(9, 1);

    // Game rule toggle: if true, stepping onto the snake's current tile is an instant lose
    private static final boolean INSTANT_LOSE_ON_PLAYER_STEP = false;

    public void run() {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                clearScreen();

                // Cache copies for this tick
                Point pp = player.getPosition();
                Point sp = snake.getPosition();

                // Render current board
                System.out.println(Board.renderWithBorder(
                        pp, sp,
                        gold.positionOrNull(),
                        door.position(),
                        gold.isCollected(),
                        2));

                // Optional HUD (debug): comment out if you don't want it
                // System.out.printf("Player: (%d,%d) | Snake: (%d,%d)%n", pp.x, pp.y, sp.x, sp.y);

                // Read command
                Input.Command cmd = Input.readCommand(sc);

                // Move player (clamped inside the board)
                switch (cmd) {
                    case UP    -> movePlayerBy(0, -1);
                    case DOWN  -> movePlayerBy(0,  1);
                    case LEFT  -> movePlayerBy(-1, 0);
                    case RIGHT -> movePlayerBy(1,  0);
                    case QUIT  -> { System.out.println("Goodbye!"); return; }
                    case NONE  -> {
                        System.out.println("Unknown command. Use W/A/S/D to move, Q to quit.");
                        pause();
                        continue;
                    }
                }

                // Refresh cached player position after moving
                pp = player.getPosition();

                // Optional rule: instant lose if the player steps onto the snake BEFORE it moves
                if (INSTANT_LOSE_ON_PLAYER_STEP && pp.equals(sp)) {
                    System.out.println(Board.renderWithBorder(
                            pp, sp,
                            gold.positionOrNull(),
                            door.position(),
                            gold.isCollected(),
                            2));
                    System.out.println("You lose! You stepped onto the snake.");
                    return;
                }

                // Pickup step (player standing on gold)
                handlePickupIfAny();

                // Win check (player at door AND gold collected)
                if (checkWinAndExitIfSo()) return;

                // Snake moves towards the player (at most 1 step per axis, may be diagonal)
                moveSnakeTowardsPlayer();

                // Refresh cached snake position after moving
                sp = snake.getPosition();

                // Lose check AFTER snake moves — re-render final state so the bite position is visible
                if (sp.equals(pp)) {
                    System.out.println(Board.renderWithBorder(
                            pp, sp,
                            gold.positionOrNull(),
                            door.position(),
                            gold.isCollected(),
                            2));
                    System.out.println("You lose! The snake bit you.");
                    return;
                }
            }
        }
    }

    /** Applies a relative move and clamps inside [0..WIDTH-1] x [0..HEIGHT-1]. */
    private void movePlayerBy(int dx, int dy) {
        Point p  = player.getPosition(); // copy
        int nx = clamp(p.x + dx, 0, Board.WIDTH  - 1);
        int ny = clamp(p.y + dy, 0, Board.HEIGHT - 1);
        player.moveTo(nx, ny);
    }

    /**
     * Moves the snake one step towards the player on each axis (at most 1 on X and/or 1 on Y),
     * resulting potentially in a diagonal move. Clamped to board bounds.
     */
    private void moveSnakeTowardsPlayer() {
        Point sp = snake.getPosition();
        Point pp = player.getPosition();
        int nx = clamp(sp.x + Integer.compare(pp.x, sp.x), 0, Board.WIDTH  - 1);
        int ny = clamp(sp.y + Integer.compare(pp.y, sp.y), 0, Board.HEIGHT - 1);
        snake.moveTo(nx, ny);
    }

    /** Try to pick up the gold if the player stands on it. */
    private void handlePickupIfAny() {
        if (gold.tryPickup(player.getPosition())) {
            System.out.println("Picked up the gold!");
            pause();
        }
    }

    /** Win only if player is at door AND gold was collected. */
    private boolean checkWinAndExitIfSo() {
        if (gold.isCollected() && door.isAt(player.getPosition())) {
            System.out.println("You win! Reached the door with the gold.");
            return true;
        }
        return false;
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