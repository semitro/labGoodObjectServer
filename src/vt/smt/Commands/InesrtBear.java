package vt.smt.Commands;

/**
 * Created by semitro on 23.04.17.
 */
import com.sun.istack.internal.NotNull;
import vt.smt.Data.Toy;
public class InesrtBear implements  ServerCommand {
    Toy bear;
    int index;
    public InesrtBear(@NotNull Toy bear, int index){
        this.bear = new Toy(bear);
        this.index = index;
    }
    public Toy getBear(){
        return bear;
    }
    public int getIndex(){
        return index;
    }
}
