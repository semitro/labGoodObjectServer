package vt.smt;


import javafx.util.Pair;
import sun.net.ConnectionResetException;
import vt.smt.Commands.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedDeque;
/**
 * Created by semitro on 18.04.17.
 */
class Receiver{
    private ServerSocket socket;
    private Server executor;
    private ConcurrentLinkedDeque<Client> clients = new ConcurrentLinkedDeque<>();

    public Receiver(int port, Server executor) throws IOException{
        socket = new ServerSocket(port);
        this.executor = executor;
        Thread t = new Thread(this::listen);
        t.start();
        System.out.println("Сервер запущен.");
    }

    public void sendToAll(ServerAnswer command){
        for(Client currentClient : clients){
            try {
                currentClient.sendCommand(command);
            } catch (IOException bad) {
                System.out.println("Receiver::sendToAll не удалось отправить команду");
                System.out.println(bad.getMessage());
            }
        }
    }
    //Добавить демон удаления ушедших клиетов
    private void listen(){
        while (true) {
            try {
                Client newClient = new Client(socket.accept());
                clients.add(newClient);
                ClientHandler handler = new ClientHandler(newClient,executor);
                handler.start();
                newClient.sendCommand(new vt.smt.Commands.Message("Добро пожаловать во Вселенскую Берлогу"));
                System.out.println("Подключился новый клиент" + newClient.getSocket().getInetAddress());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
