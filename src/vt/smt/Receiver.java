package vt.smt;


import vt.smt.Commands.ServerAnswer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ResourceBundle;
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
    // Оповещение с учётом локали
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("vt/smt/Languages");
    public void sendLocaleMessageToAll(String key){
        for(Client currentClient : clients){
            try {
                resourceBundle = ResourceBundle.getBundle("vt/smt/Languages", currentClient.getLocale());
                currentClient.sendCommand( new vt.smt.Commands.Message(resourceBundle.getString(key)) );
            } catch (IOException bad) {
                System.out.println("Receiver::sendLocaleMessageToAll -  не удалось отправить команду");
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
