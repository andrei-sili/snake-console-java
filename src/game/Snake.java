package game;

import java.awt.Point;

/** Minimal snake entity that stores its position. */
public final class Snake {
    private final Point pos = new Point();

    public Snake(int x, int y) { pos.move(x, y); }

    /** Return a copy to avoid leaking internal state. */
    public Point getPosition() { return new Point(pos); }

    public void moveTo(int x, int y) { pos.move(x, y); }
}

