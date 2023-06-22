import java.util.Scanner;

public class Menu {
    private static final int EXIT_NUMBER = 0;
    public void run(){
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
