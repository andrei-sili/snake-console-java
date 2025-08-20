package game;

import java.util.Scanner;

public final class Input {
    public enum Command { UP, DOWN, LEFT, RIGHT, QUIT, NONE }

    private Input() {}

    public static Command readCommand(Scanner sc) {
        System.out.print("Move (W/A/S/D, Q to quit): ");
        String line;
        do {
            if (!sc.hasNextLine()) return Command.QUIT;
            line = sc.nextLine().trim();
        } while (line.isEmpty());

        char c = Character.toLowerCase(line.charAt(0));
        return switch (c) {
            case 'w' -> Command.UP;
            case 'a' -> Command.LEFT;
            case 's' -> Command.DOWN;
            case 'd' -> Command.RIGHT;
            case 'q' -> Command.QUIT;
            default -> Command.NONE;
        };
    }
}
