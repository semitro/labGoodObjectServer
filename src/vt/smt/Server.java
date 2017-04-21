package vt.smt;


import javafx.util.Pair;
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
            // Пара, чтобы держать обратную связь с клиентом
            Pair<Client,ServerCommand> p = receiver.nextCommand();
            if(p.getValue() != null)
                execute(p);
        }

    }
    private void execute(Pair<Client,ServerCommand> request){
        ServerCommand command = request.getValue();
        Client client = request.getKey();
        if(command instanceof SaveOnServer){
            System.out.println(((SaveOnServer) command).getData().size());
        }
        else
        if(command instanceof GetBearsFromServer){
            try {
                client.getObjectOutStrem().writeObject(new Toy("Медведь с Луны"));

            }catch (IOException e){
                System.out.println("Беда в записи медведя на орбиту");
            }
        }
    }

}
