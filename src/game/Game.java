package game;

import java.awt.Point;

public class Game {
    public void run() {
        Point player = new Point(0, 0);
        Point snake  = new Point(8, 6);
        Point gold   = new Point(5, 3);
        Point door   = new Point(9, 0);
        boolean hasGold = false;

        Board.drawToConsoleWithBorder(player, snake, gold, door, hasGold,2);

    }
}
