package vt.smt;


import vt.smt.Commands.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedDeque;
/**
 * Created by semitro on 18.04.17.
 */
class Receiver{
    private ServerSocket socket;
    public Receiver(int port) throws IOException{
        socket = new ServerSocket(port);
        Thread t = new Thread(this::listen);
        t.setDaemon(true);
        t.start();
        System.out.println("Сервер запущен.");
    }

    private ConcurrentLinkedDeque<Client> clients = new ConcurrentLinkedDeque<>();
    ServerCommand nextCommand(){
        for(Client currentClient : clients) {
            try {
                return (ServerCommand) currentClient.getObjectInputStream().readObject();
            }catch (ClassNotFoundException e){
                System.out.println("Беда в next command:");
                System.out.println(e.getMessage());
            }
            catch (IOException e){
                System.out.println("next ommand: ioexc");
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    //Добавить демон удаления ушедших клиетов ?
    private void listen(){
        while (true) {
            try {
                Client newClient = new Client(socket.accept());
                clients.add(newClient);
                System.out.println("Подключился новый клиент" + newClient.getSocket().getInetAddress());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
