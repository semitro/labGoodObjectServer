package vt.smt.Commands;

import com.sun.istack.internal.Nullable;
import vt.smt.Data.Toy;
import java.util.Comparator;

/**
 * Created by semitro on 22.04.17.
 */
public class SortBears implements ServerCommand {
    private Comparator<Toy> comp;
    public SortBears(@Nullable Comparator<Toy> comp){
        this.comp = comp;
    }

}
