import java.util.Scanner;

public class Main {
    private static final int EXIT_NUMBER = 0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning){
            int menuItem = scanner.nextInt();
            if (menuItem == EXIT_NUMBER){
                isRunning = false;
            }
        }

    }
}