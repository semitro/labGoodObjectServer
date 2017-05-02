package vt.smt;


import javafx.util.Pair;
import vt.smt.Commands.*;
import java.io.IOException;

import vt.smt.DB.BearsInteraction;

/**
 * Сервер, по сути, штука, выполняющая пишедние команды
 */

public class Server {
    private Receiver receiver;
    public Server(){
        try {
           // BearsInteraction.getInstance();
            receiver = new Receiver(2552,this);
        }catch (IOException e){
            System.out.println("Не удалось создат сервер.");
            e.printStackTrace();
        }
    }
    public void start(){

    }
    /**
    *   При выполнении команды, модифицирующей медведей, необходимо обновить данные у всех клиентов
    *
     */
    private synchronized void executeModify(ServerCommand command,Client client){
       // receiver.resetAllInputCommands();
        // Изеняем медведя на сервере и просим сделать то же самое у всех клиентов
        if(command instanceof ChangeBear) {
            BearsInteraction.getInstance().changeBear(
                    ((ChangeBear) command).getIndex(), ((ChangeBear) command).getBear());
            receiver.sendToAll(new ChangeBear((ChangeBear)command));
        }
        if(command instanceof RemoveBear){
            BearsInteraction.getInstance().removeBear(((RemoveBear)command).getIndex());
            receiver.sendToAll(((RemoveBear)command));
        }
        if(command instanceof InsertBear){
            BearsInteraction.getInstance().insertBear(
                    ((InsertBear)(command)).getIndex(),
                    ((InsertBear)(command)).getBear()
            );
            receiver.sendToAll((InsertBear)command);
        }
        if(command instanceof SortBears){
            BearsInteraction.getInstance().sortBears();
            receiver.sendToAll(
                    new SaveAllBears(BearsInteraction.getInstance().getAllBears()));
            receiver.sendToAll(new Message(" \"Ну-ка, Мишки встали в ряд!\" - исполнено"));
        }
        if(command instanceof  CommitChanges){
            BearsInteraction.getInstance().commitChanges();
            receiver.sendToAll(new Message("Все изменения успешно зафиксированы."));
        }
    }

    public void execute(Pair<Client,ServerCommand> request){
        if(request.getValue() == null)
            return;
        System.out.println("Выполняю команду " + request.getValue() +
                " от клиента" + request.getKey().getSocket().getInetAddress());
        ServerCommand command = request.getValue();
        Client client = request.getKey();
        // Команды без модификаций не требуют синхронизации
        if(command instanceof GetAllBears){
            try {
               client.sendCommand(new SaveAllBears(BearsInteraction.getInstance().getAllBears()));
            }catch (Exception e){
                System.out.println("Server::execute::getAllBears: Беда в отправке медведей на орбиту");
                System.out.println(e.getMessage());
            }
        }
        executeModify(command,client);
    }

}
