import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CeasedCypher {
    private static String message = "";
    private static final int[] MUTABLE_CHARACTERS = new int[]{32, 33, 34, 44, 45, 46, 58, 63};
    private static final String REAL_MESSAGE = "Справжнє послання: ";
    private static final String FAKE_MESSAGE = "Зашифроване послання: ";
    private static final String NAME_FILE = "Укажіть ім'я нового файлу: ";
    private static final String FILE_CREATED = "Файл створено!";
    private static final String FILE_WROTE_TO_FILE = "Наше таємне повідомлення записано в файл";
    private static final String STILL_WOORKING = """
            Бажаєте продовжити?
            Введіть "так" або "ні"
            """;

    public static void encode(String fileName){
        System.out.println(REAL_MESSAGE);
        readMessage(fileName);

        System.out.println(FAKE_MESSAGE);
        String encodeMessage = encodeProcess();
        System.out.println(encodeMessage);

        creatingNewFile(encodeMessage);

        message = "";
        isStillRunning();
    }

    public static void decode(String fileName){
        System.out.println(FAKE_MESSAGE);
        readMessage(fileName);

        System.out.println(REAL_MESSAGE);
        //String encodeMessage = encodeProcess();

        isStillRunning();
    }

    private static boolean existSymbol(int number){
        for (int i = 0; i < MUTABLE_CHARACTERS.length; i++) {
            if(MUTABLE_CHARACTERS[i] == number){
                return true;
            }
        }
        return false;
    }

    private static void readMessage(String fileName){
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int counter = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (counter != 0){
                    message += " ";
                }
                counter +=1;
                message = message + line;
            }
            System.out.println(message);
            bufferedReader.close();
        }catch (IOException e) {
                e.printStackTrace();
        }
    }

    private static String encodeProcess(){
        int key = 3;
        String new_str = "";
        for (int i = 0; i < message.length(); i++) {
            int characterIndex = message.charAt(i);
            if(characterIndex >= 1040 && characterIndex <= 1071){
                int changedSymbol = (message.charAt(i) + key);
                int normalValue = (changedSymbol - 1040) % 32 + 1040;
                new_str += (char) normalValue;
            } else if(existSymbol(characterIndex)){
                new_str += (char) (characterIndex + key);
            } else {
                new_str += (char) (characterIndex);
            }
        }
        return new_str;
    }

    private static void creatingNewFile(String encodeMessage){
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println(NAME_FILE);

            String fileName = scanner.nextLine();
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println(FILE_CREATED);
            }

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(encodeMessage);
            bufferedWriter.close();

            System.out.println(FILE_WROTE_TO_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void isStillRunning(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(STILL_WOORKING);
        String answer = scanner.nextLine();
        switch (answer){
            case "так" -> new Menu().run();
            case "ні" -> new Menu().itemToExit();
        }
    }
}
