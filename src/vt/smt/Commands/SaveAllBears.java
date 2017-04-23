package vt.smt.Commands;


import java.util.Collections;
import vt.smt.Data.Toy;
import java.util.LinkedList;
import java.util.List;
/**
 * Просьба сохранить  всех медведей
 */

public class SaveAllBears implements ServerCommand, ServerAnswer{
    private LinkedList<Toy> data;
    public SaveAllBears(List<Toy> objects){
        data = new LinkedList<Toy>(objects);
    }
    public LinkedList<Toy> getData(){
        return data;
    }
}
