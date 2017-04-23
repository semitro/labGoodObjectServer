package vt.smt.Commands;

/**
 * Created by semitro on 23.04.17.
 */
public class RemoveBear implements ServerCommand,ServerAnswer {
    private int index;
    public RemoveBear(int index){
        this.index = index;
    }
    public int getIndex(){
        return index;
    }
}
