package vt.smt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import vt.smt.Commands.*;
/**
 * Клиент инкапсулирует сокет и связанный с ним поток объектов
 */
public class Client {
    private Socket socket;
    public Client(Socket socket){
        this.socket = socket;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e){
            System.out.println("Беда в конструкторе: ");
            System.out.println(e.getMessage());
        }
    }
    public Socket getSocket(){return socket;}
    private ObjectInputStream ois;
    private ObjectOutputStream out;

    public void sendCommand(ServerAnswer command) throws IOException{
        out.writeObject(command);
        System.out.println("Отправил команду " + command);
    }
    public ObjectInputStream getObjectInputStream(){
        return ois;
    }
}
