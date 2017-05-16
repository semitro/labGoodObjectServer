package vt.smt;

import vt.smt.Commands.ServerAnswer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Locale;
/**
 * Клиент инкапсулирует сокет и связанный с ним поток объектов,
 * А также, локаль самого клиента.
 */
public class Client {

    private Socket socket;

    private ObjectInputStream ois;
    private ObjectOutputStream out;

    private Locale locale;

    public Client(Socket socket){
        locale = Locale.getDefault();
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


    public void sendCommand(ServerAnswer command) throws IOException{
        out.writeObject(command);
        System.out.println("Отправил команду " + command);
    }
    public ObjectInputStream getObjectInputStream(){
        return ois;
    }

    public Locale getLocale(){
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
