import com.*;
import com.utility.*;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

public class Server{
    public static final Logger logger = Logger.getLogger(Server.class.getName());
    private final int port;
    private SocketChannel socketChannel;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private Request request;
    private CommandHandler commandHandler;
    private CollectionManager manager;
    private final Serializer serializer;
    private final String data;
    private String log;

    public Server(int port,String data, String log) throws IOException {
        this.port = port;
        this.data = data;
        serializer = new Serializer();
        try {
            Handler handler = new FileHandler(log);
            logger.setUseParentHandlers(false);
            handler.setFormatter(new LogFormatte());
            logger.addHandler(handler);
        }catch (Exception e){
            System.out.println("Путь к файлу, куда писать логи - неверен");
            System.exit(1);
        }
    }

    public void run() throws IOException,ClassNotFoundException{
        start();
        getUserInputHandler(manager).start();
        System.out.println("Сервер запущен");
        logger.info("Сервер запущен");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try{
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();

                while(iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isValid()){
                        if (key.isAcceptable()) {
                            socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println("Новое подключение - успешно");
                            logger.info("Новое подключение - успешно!");
                        }
                        if (key.isReadable()){
                            socketChannel = (SocketChannel) key.channel();
                            request = readRequest();
                            System.out.println("Данные от клиента считаны : \n " + request.toString());
                            logger.info("Данные от клиента считаны : \n " + request.toString());
                            socketChannel.configureBlocking(false);
                            socketChannel.register(key.selector(), SelectionKey.OP_WRITE);
                        }
                        if (key.isWritable()){
                            socketChannel = (SocketChannel) key.channel();
                            Response response = new Response(commandHandler.run(manager,request));
                            sendResponse(response);
                            //System.out.println("Ответ от сервера отправлен клиенту : \n" + response.getResponce());
                            //logger.info("Ответ от сервера отправлен клиенту : \n" + response.getResponce());
                            socketChannel.configureBlocking(false);
                            socketChannel.register(key.selector(), SelectionKey.OP_READ);
                        }
                        iterator.remove();
                    }
                }
            }catch (IOException | ClassNotFoundException e){
                try {
                    socketChannel.close();
                } catch (IOException ioException) {
                    System.out.println("Непредвденная ошибка");
                    logger.warning(e.getMessage());
                }
            }
        }
    }

    /**
     * Запуск сервера
     * @throws IOException Ошибки
     */
    private void start() throws IOException {
        try {
            manager = new CollectionManager(data);
            commandHandler = new CommandHandler();
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            System.out.println("Вероятнее всего - занят порт или путь к файлу с коллекцией неверен");
            logger.warning(e.getMessage());
        }
    }

    private Request readRequest() throws IOException, ClassNotFoundException {
        byte[] b = new byte[10000];
        ByteBuffer f = ByteBuffer.wrap(b);
        f.clear();
        socketChannel.read(f);
        return serializer.deserialize(b);
    }

    private void sendResponse(Response response) throws IOException {
        ByteBuffer f1 = ByteBuffer.wrap(serializer.serialize(response));
        socketChannel.write(f1);
    }


    /**
     * Второй поток для работы с клиентами и одновременно с консолью сервера (данный поток на консоль)
     * @param manager коллекция
     * @return как я понял, это закрывается поток
     */
    private static Thread getUserInputHandler(CollectionManager manager)
    {
        return new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                if (scanner.hasNextLine()) {
                    String serverCommand = scanner.nextLine();
                    switch (serverCommand) {
                        case "save":
                            try {
                                manager.save();
                                System.out.println("Успешное сохранение коллекции");
                                logger.info("Успешное сохранение коллекции командой - " + serverCommand);
                            } catch (FileNotFoundException e) {
                                System.out.println("Ошибка при сохранении в файл");
                                logger.warning("Вероятнее всего файл , в который надо сохранить коллекцию , просто не найден\n" + e.getMessage());
                            }
                            break;
                        case "exit":
                            System.out.println("Работа сервера успешно завершена" );
                            logger.info("Работа сервера успешно завершена командой - " + serverCommand);
                            System.exit(1);
                        default:
                            System.out.println("Такой команды нет");
                            logger.info("Введена неверная команда на сервере - " + serverCommand);
                            break;
                    }
                }
                else {
                    System.out.println("Непредвиденная ошибка, команды на сервере надоступны" +
                            "\nДля их использования требуется перезапуск сервера");
                    logger.warning("Непредвиденная ошибка при вводе команды на сервере, вероятнее всего нажато сочетание : " +
                            "Ctrl+D");
                    return;
                }

            }
        });
    }


    /**
     * Класс - форматер для логгера
     * Пишем дату и время запроса - его статус и сообщение
     */
    static class LogFormatte extends Formatter {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd 'и время' hh:mm:ss a zzz");  // текущая дата и время

        @Override
        public String format(LogRecord record){
            return formatForDateNow.format(dateNow) + "\n" + record.getLevel() + ": " + record.getMessage() + "\n\n";
        }
    }
}
