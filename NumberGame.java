import java.util.Random;
import java.util.Scanner;

/**
 * CODSOFT TASK 1 - NUMBER GAME
 * Features: difficulty levels, limited attempts, replay and score tracking.
 */
public class NumberGame {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        int totalScore = 0, roundsWon = 0, bestAttempts = Integer.MAX_VALUE;
        System.out.println("========== NUMBER GUESSING GAME ==========");

        boolean playAgain;
        do {
            System.out.println("\nChoose difficulty:");
            System.out.println("1. Easy   (1-50, 10 attempts)");
            System.out.println("2. Medium (1-100, 7 attempts)");
            System.out.println("3. Hard   (1-200, 6 attempts)");
            int level = readInt("Choice: ", 1, 3);

            int maximum = level == 1 ? 50 : level == 2 ? 100 : 200;
            int maxAttempts = level == 1 ? 10 : level == 2 ? 7 : 6;
            int secret = random.nextInt(maximum) + 1;
            int used = 0;
            boolean won = false;

            System.out.printf("Guess a number between 1 and %d.%n", maximum);
            while (used < maxAttempts) {
                int guess = readInt("Your guess: ", 1, maximum);
                used++;

                if (guess == secret) {
                    won = true;
                    roundsWon++;
                    int points = (maxAttempts - used + 1) * 10;
                    totalScore += points;
                    bestAttempts = Math.min(bestAttempts, used);
                    System.out.println("Correct! You won " + points + " points.");
                    break;
                } else if (guess < secret) {
                    System.out.println("Too low. Try a higher number.");
                } else {
                    System.out.println("Too high. Try a lower number.");
                }
                System.out.println("Attempts left: " + (maxAttempts - used));
            }

            if (!won) System.out.println("You lost! The number was " + secret + ".");
            System.out.printf("Score: %d | Rounds won: %d%n", totalScore, roundsWon);
            playAgain = readYesNo("Play another round? (Y/N): ");
        } while (playAgain);

        System.out.println("\n============= FINAL RESULT =============");
        System.out.println("Rounds won: " + roundsWon);
        System.out.println("Total score: " + totalScore);
        System.out.println("Best attempts: " +
                (bestAttempts == Integer.MAX_VALUE ? "No win yet" : bestAttempts));
        System.out.println("Thank you for playing!");
    }

    private static int readInt(String message, int min, int max) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { }
            System.out.printf("Enter a whole number from %d to %d.%n", min, max);
        }
    }

    private static boolean readYesNo(String message) {
        while (true) {
            System.out.print(message);
            String answer = sc.nextLine().trim();
            if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) return true;
            if (answer.equalsIgnoreCase("N") || answer.equalsIgnoreCase("NO")) return false;
            System.out.println("Please enter Y or N.");
        }
    }
}
