package vt.smt;

import java.io.EOFException;
import java.io.IOException;

import javafx.util.Pair;
import sun.net.ConnectionResetException;
import vt.smt.Commands.ServerCommand;
/**
 * Created by semitro on 23.04.17.
 */
public class ClientHandler extends Thread{
    private Client client;
    // Возможно, не совсем красиво, но да что уж там
    private Server executor;
    public ClientHandler(Client client, Server server){
        this.client = client;
        this.executor = server;
    }
    @Override
    public void run() {
        ServerCommand command = null;
        while (true) {
            command = null;
            try {
                command = (ServerCommand) client.getObjectInputStream().readObject();
            }
            catch (EOFException e){
                try {
                    client.getSocket().close();
                    client.getObjectInputStream().close();
                    System.out.println("До встречи, " + client.getSocket().getInetAddress() + "!");
                    return;
                }catch (IOException exception){
                    System.out.println("Мы хотели попрощаться клиентами, но словили catch в catch'е при попытке закрытия потока");
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                return;
            }
            catch (ClassNotFoundException | NullPointerException e){
                System.out.println("Словил исключение в clientHandler::run (commandRecive-)");
                System.out.println(e.getMessage());
            }
            executor.execute(new Pair<>(client, command));
        }
    }
}
