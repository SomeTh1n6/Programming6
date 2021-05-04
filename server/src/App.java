import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main (String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        int port = 25534;
        String data = null;
        String log = null;
        //String s = new String("string");
        if (args.length == 3){
            try {
                port = Integer.parseInt(args[0]);
                data = args[1];
                log = args[2];
            }catch (Exception e){
                System.out.println("Ошибка при считывании данных.\n\nВведите\n" +
                        "   1) Порт\n" +
                        "   2) Пусть к файлу с данными\n" +
                        "   3) Путь к файлу , куда писать логи\n");
                System.exit(1);
            }
        }else{
            System.out.println("Ошибка при считывании данных.\n\nВведите\n" +
                    "   1) Порт\n" +
                    "   2) Пусть к файлу с данными\n" +
                    "   3) Путь к файлу , куда писать логи");
            System.exit(1);
        }

        new Server(port,data,log).run();
    }
}
