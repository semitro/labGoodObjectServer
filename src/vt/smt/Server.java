package vt.smt;


import javafx.util.Pair;
import vt.smt.Commands.*;
import java.io.IOException;
import java.util.LinkedList;

import vt.smt.DB.BearsInteraction;

import javax.sql.rowset.CachedRowSet;

/**
 * Created by semitro on 18.04.17.
 */
public class Server {
    private Receiver receiver;
    public Server(){
        try {
            receiver = new Receiver(2552);
        }catch (IOException e){
            System.out.println("Не удалось создат сервер.");
            e.printStackTrace();
        }
    }
    public void start(){

        Thread t = Thread.currentThread();
        while(true){
            try {
                t.sleep(10);
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
            // Пара, чтобы держать обратную связь с клиентом
            Pair<Client,ServerCommand> p = receiver.nextCommand();
            if(p != null && p.getValue() != null)
                execute(p);
        }

    }
    private void execute(Pair<Client,ServerCommand> request){
        System.out.println("Выполняю команду " + request.getValue() +
                " от клиента" + request.getKey().getSocket().getInetAddress());
        // Добавить уровень доступа клиенту
        ServerCommand command = request.getValue();
        Client client = request.getKey();
        if(command instanceof SaveOnServer){
            System.out.println(((SaveOnServer) command).getData().size());
        }
        else
        if(command instanceof GetBearsFromServer){
            try {
               // System.out.println(BearsInteraction.getInstance().getAllBears());
                client.getObjectOutStrem().writeObject(BearsInteraction.getInstance().getAllBears());
            }catch (Exception e){
                System.out.println("Беда в записи медведя на орбиту");
                System.out.println(e.getMessage());
            }
        }


    }

}
