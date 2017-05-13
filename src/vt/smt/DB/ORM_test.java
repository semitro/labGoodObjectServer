package vt.smt.DB;

import vt.smt.Data.Toy;

/**
 * Created by semitro on 13.05.17.
 */
public class ORM_test {
    public static void main(String argv[]){
        System.out.println(ORM.getDDL(new Test()));
        System.out.println(ORM.getInsertQuery(new Test()));
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