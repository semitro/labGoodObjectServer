package vt.smt;


import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by semitro on 18.04.17.
 */
public class Main {
    public static void main(String argv[]){
        Server server = new Server();
        server.start();
    }
}
