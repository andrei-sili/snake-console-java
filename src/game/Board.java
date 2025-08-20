package game;

import java.awt.Point;
import java.util.Objects;

public final class Board {
    public static final int WIDTH  = 10;
    public static final int HEIGHT = 10;

    public static final char CH_PLAYER = 'P';
    public static final char CH_SNAKE  = 'S';
    public static final char CH_GOLD   = 'G';
    public static final char CH_DOOR   = 'D';
    public static final char CH_EMPTY  = '.';
    public static final char CH_BORDER = '#';

    private Board() {}


    public static String render(Point player, Point snake, Point gold, Point door, boolean hasGold) {
        return render(player, snake, gold, door, hasGold, false, 0);
    }

    public static String renderWithBorder(Point player, Point snake, Point gold, Point door,
                                          boolean hasGold, int spacing) {
        return render(player, snake, gold, door, hasGold, true, spacing);
    }

    public static void drawToConsoleWithBorder(Point player, Point snake, Point gold, Point door,
                                               boolean hasGold, int spacing) {
        System.out.print(renderWithBorder(player, snake, gold, door, hasGold, spacing));
    }


    private static String render(Point player, Point snake, Point gold, Point door,
                                 boolean hasGold, boolean withBorder, int spacing) {
        Objects.requireNonNull(player); Objects.requireNonNull(snake); Objects.requireNonNull(door);
        spacing = Math.max(0, spacing);
        String pad = spacing == 0 ? "" : " ".repeat(spacing);


        int innerRowLen = WIDTH + (WIDTH - 1) * spacing;

        StringBuilder sb = new StringBuilder((innerRowLen + (withBorder ? 2 : 0) + 1) * HEIGHT + 2);

        if (withBorder) {
            sb.append(String.valueOf(CH_BORDER).repeat(innerRowLen + 2)).append('\n');
        }

        for (int y = 0; y < HEIGHT; y++) {
            if (withBorder) sb.append(CH_BORDER);

            for (int x = 0; x < WIDTH; x++) {
                sb.append(cellCharAt(x, y, player, snake, gold, door, hasGold));
                if (x < WIDTH - 1) sb.append(pad);
            }

            if (withBorder) sb.append(CH_BORDER);
            sb.append('\n');
        }

        if (withBorder) {
            sb.append(String.valueOf(CH_BORDER).repeat(innerRowLen + 2)).append('\n');
        }

        return sb.toString();
    }

    private static char cellCharAt(int x, int y, Point player, Point snake, Point gold, Point door,
                                   boolean hasGold) {

        if (same(x, y, player)) return CH_PLAYER;
        if (same(x, y, snake))  return CH_SNAKE;
        if (!hasGold && gold != null && same(x, y, gold)) return CH_GOLD;
        if (same(x, y, door))   return CH_DOOR;
        return CH_EMPTY;
    }

    private static boolean same(int x, int y, Point p) { return p != null && p.x == x && p.y == y; }

    public static boolean isInside(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }
}
