package vt.smt;


import vt.smt.Commands.*;
import java.io.IOException;

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
                t.sleep(1);
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
            ServerCommand command = receiver.nextCommand();
            if(command != null)
                execute(command);
        }

    }
    private void execute(ServerCommand command){
        if(command instanceof SaveOnServer){
            System.out.println(((SaveOnServer) command).getData().size());
        }
    }
}
