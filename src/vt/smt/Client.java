package vt.smt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
    // Для обратоног общения
    public ObjectOutputStream getObjectOutStrem(){return out;}
    public ObjectInputStream getObjectInputStream(){
        return ois;
    }
}
