package game;

import java.awt.Point;

public final class Player {
    private final Point pos = new Point();

    public Player(int startX, int startY) {
        pos.move(startX, startY);
    }

    public Point getPosition() {
        return pos; // sau: return new Point(pos);
    }

    public void moveTo(int x, int y) {
        pos.move(x, y);
    }

    public void translate(int dx, int dy) {
        pos.translate(dx, dy);
    }

    @Override
    public String toString() {
        return "Player(" + pos.x + "," + pos.y + ")";
    }
}
