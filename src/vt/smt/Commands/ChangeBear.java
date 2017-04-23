package vt.smt.Commands;

/**
 *
 */
import com.sun.istack.internal.NotNull;
import vt.smt.Data.Toy;
public class ChangeBear implements ServerCommand, ServerAnswer{
    private int index;
    private Toy newBear;
    public ChangeBear(@NotNull Toy newBear,int index){
        this.newBear = new Toy(newBear);
        this.index = index;
    }
    public ChangeBear(ChangeBear copy){
        this(copy.getBear(),copy.getIndex());
    }
    public int getIndex(){
        return index;
    }
    public Toy getBear(){
        return newBear;
    }
}
