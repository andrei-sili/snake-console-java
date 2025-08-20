package game;

import java.awt.Point;

/** Gold pickup state + helpers. */
public final class Gold {
    private final Point pos;
    private boolean collected = false;

    public Gold(int x, int y) { this.pos = new Point(x, y); }

    /** True once the player picked it up. */
    public boolean isCollected() { return collected; }

    /** Returns null when collected so Board won't draw it. */
    public Point positionOrNull() { return collected ? null : pos; }

    /** Returns true if the player stands on the gold (and it's not collected). */
    public boolean isAt(Point p) { return !collected && pos.equals(p); }

    /** Pick up if the player stands on it; returns true on first pickup. */
    public boolean tryPickup(Point playerPos) {
        if (isAt(playerPos)) { collected = true; return true; }
        return false;
    }
}
