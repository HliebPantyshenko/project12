import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Scanner;

public class CeasedCypher {
    private static String message = "";
    private static final int[] MUTABLE_CHARACTERS = new int[]{32, 33, 34, 44, 45, 46, 58, 63};
    private static final String REAL_MESSAGE = "Справжнє послання: ";
    private static final String FAKE_MESSAGE = "Зашифроване послання: ";
    private static final String NAME_FILE = "Укажіть ім'я нового файлу: ";
    private static final String FILE_CREATED = "Файл створено!";
    private static final String FILE_WROTE_TO_FILE = "Наше таємне повідомлення записано в файл";
    private static final String STILL_WORKING =
            """
            Бажаєте продовжити?
            Введіть "так" або "ні"
            """;
    private static final String IS_KEY =
            """
            Чи маєте ви ключ?
            Введіть "так", якщо маєте.
            Інші варіанти розглядаються як те, що ви не маєте ключа.
            """;
    private static final String SELECT_KEY = "Введіть ключ: ";
    private static final String IS_READABLE =
            """
            Чи можете ви прочитати текст?
            Введіть "так", якщо можете.
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

        int key = isKey();

        System.out.println(REAL_MESSAGE);
        String decodeMessage = decodeProcess(key);

        creatingNewFile(decodeMessage);

        message = "";
        isStillRunning();
    }

    private static boolean existSymbol(int number){
        for (int mutableCharacter : MUTABLE_CHARACTERS) {
            if (mutableCharacter == number) {
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
            System.out.println(message = message.toLowerCase());
            bufferedReader.close();
        }catch (IOException e) {
                e.printStackTrace();
        }
    }
    private static String decodeProcess(int key){
        if (key == -1){
            return noKeyDecodeProcess();
        }
        return decodeProcessAlgorithm(key);
    }

    private static String noKeyDecodeProcess(){
        boolean isRunning = true;
        int key = 0;
        while (isRunning){
            System.out.println(IS_READABLE);
            String decodedText = decodeProcessAlgorithm(key);
            System.out.println(decodedText);
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            if(answer.equals("так")) {
                isRunning = false;
                return decodedText;
            }
            key+=1;
        }
        return null;
    }

    private static String decodeProcessAlgorithm(int key){
        String new_str = "";
        for (int i = 0; i < message.length(); i++) {
            int characterIndex = message.charAt(i);
            if (characterIndex - key >= 1072 && characterIndex - key <= 1103) {
                int changedSymbol = (message.charAt(i) - key);
                int normalValue = (changedSymbol - 1072) % 32 + 1072;
                new_str += (char) normalValue;
            } else if (existSymbol(characterIndex - key)) {
                new_str += (char) (characterIndex - key);
            } else {
                new_str += (char) (characterIndex);
            }
        }
        return new_str;
    }
    private static String encodeProcess(){
        int key = 3;
        String new_str = "";
        for (int i = 0; i < message.length(); i++) {
            int characterIndex = message.charAt(i);
            if(characterIndex >= 1072 && characterIndex <= 1103){
                int changedSymbol = (message.charAt(i) + key);
                int normalValue = (changedSymbol - 1072) % 32 + 1072;
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
    private static int isKey(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(IS_KEY);
        String answer = scanner.nextLine();
        if(answer.equals("так")){
            System.out.println(SELECT_KEY);
            int number = scanner.nextInt();
            return number;
        }
        return -1;
    }
    private static void isStillRunning(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(STILL_WORKING);
        String answer = scanner.nextLine();
        switch (answer){
            case "так" -> new Menu().run();
            case "ні" -> new Menu().itemToExit();
        }
    }
}
