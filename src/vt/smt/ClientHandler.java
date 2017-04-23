package vt.smt;

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
            try {
                command = null;
                command = (ServerCommand) client.getObjectInputStream().readObject();
            }
            catch (IOException | ClassNotFoundException | NullPointerException e) {
                if(e != null && e.getMessage().contains("Connection reset")) {
                    try {
                        client.getSocket().close();
                        client.getObjectInputStream().close();
                        System.out.println("До встречи, " + client.getSocket().getInetAddress() + "!");
                        return;
                    }catch (IOException ex){
                        System.out.println(ex.getMessage() + "\n - это случилось в ClientHandler");
                        return;
                    }
                }
                System.out.println(e.getMessage());
            }
            executor.execute(new Pair<>(client, command));
        }
    }
}
