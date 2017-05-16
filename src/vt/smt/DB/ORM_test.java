package vt.smt.DB;

import vt.smt.Data.Toy;

/**
 * Created by semitro on 13.05.17.
 */
public class ORM_test {
    public static void main(String argv[]){
        System.out.println(ORM.getDDL(new Dolbaeb()));
        System.out.println(ORM.getInsertQuery(new Dolbaeb()));

    }
}
class Test{
    int x = 0;

    public int getX() {
        return x;
    }

    vt.smt.Data.Toy toy;
    Test(){
       toy = new vt.smt.Data.Toy("sgg");
    }
}

class Dolbaeb {
    int quantityOfDebt;
    String alias;
    boolean isNahuy;
    public Dolbaeb(){
        alias = "ssfsfs";
        isNahuy = true;
        quantityOfDebt = 24;
    }

    public int getQuantityOfDebt() {
        return quantityOfDebt;
    }

    public void setQuantityOfDebt(int quantityOfDebt) {
        this.quantityOfDebt = quantityOfDebt;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isNahuy() {
        return isNahuy;
    }

    public void setNahuy(boolean nahuy) {
        isNahuy = nahuy;
    }
}