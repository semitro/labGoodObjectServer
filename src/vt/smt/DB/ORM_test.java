package vt.smt.DB;

import java.time.ZonedDateTime;

/**
 * Created by semitro on 13.05.17.
 */
public class ORM_test {
    public static void main(String argv[]){
        System.out.println(ORM.getDDL(new vt.smt.Data.Toy("sfs")));
        System.out.println(ORM.getInsertQuery(new Test()));
        System.out.print(ORM.getDDL(new CCSa()));
    }
}
class CCSa{
    boolean asgs;
}
class Test{
    int quantityOfDebt;
    String alias;
    boolean isIs;
    ZonedDateTime time;
    public Test(){
        alias = "ssfsfs";
        isIs = true;
        quantityOfDebt = 0;
        time = ZonedDateTime.now();
    }

    public ZonedDateTime getTime() {
        return time;
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

    public boolean isIs() {
        return isIs;
    }

    public void setIs(boolean is) {
        isIs = is;
    }
}