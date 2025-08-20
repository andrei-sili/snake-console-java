package game;

import java.awt.Point;

/** Door location + checks. */
public final class Door {
    private final Point pos;

    public Door(int x, int y) { this.pos = new Point(x, y); }

    public Point position() { return pos; }

    /** True if player stands on the door. */
    public boolean isAt(Point p) { return pos.equals(p); }
}

