package vt.smt.Commands;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by semitro on 18.04.17.
 */

public class SaveOnServer<T> implements ServerCommand {
    private List<T> data;
    public SaveOnServer(List<T> objects){
        data = new LinkedList<T>();
        Collections.copy(data,objects);
    }
    public List<T> getData(){
        return data;
    }
}
