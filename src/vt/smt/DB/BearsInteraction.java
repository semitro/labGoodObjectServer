package vt.smt.DB;

import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.sun.rowset.CachedRowSetImpl;
import sun.awt.image.ImageWatched;
import vt.smt.Data.Toy;

import javax.sql.rowset.CachedRowSet;

/**
 * Created by semitro on 21.04.17.
 */
public class BearsInteraction {
    private Properties user = new Properties();
    private Connection connect;
    private Statement statement;
    private CachedRowSet bearsSet;
    private LinkedList<Toy> bearsCashe;
    private BearsInteraction(){
        try {
            Class.forName("org.postgresql.Driver");
            user.setProperty("user", "bear");
            user.setProperty("password", "bear");
            connect = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bears",
                    user.getProperty("user"),user.getProperty("password"));
            statement = connect.createStatement();
            bearsSet = new CachedRowSetImpl();
            bearsSet.populate(statement.executeQuery("select * from Bear"));
            bearsCashe = getBearsFromDB();

            System.out.println(bearsCashe);
            connect.close();
            bearsSet.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {

        }
    }
    static BearsInteraction instance;
    // Доделать нормальную обработку данных!
    public static BearsInteraction getInstance(){
        if(instance == null)
            instance = new BearsInteraction();
         return instance;
    }

    private LinkedList<Toy> getBearsFromDB(){
        try {
            LinkedList<Toy> ans = new LinkedList<>();
            // bearsSet.next();
            while(bearsSet.next()){
                ans.add(new Toy(
                        bearsSet.getString("name"),
                        bearsSet.getFloat("weight"),
                        bearsSet.getBoolean("isClean")
                ));
            }

            return ans;
        }catch (SQLException e){
            System.out.println("BearsIneraction::getAllBearsFromDB(): плохой запрос");
            System.out.println(e.getMessage());
        }
        return null;
    }
    // Возможно, следует добавить копирование
    public LinkedList<Toy> getAllBears(){
        return bearsCashe;
    }
    public void commitChanges(){
        try {
            connect = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bears",
                    user.getProperty("user"), user.getProperty("password"));
            statement = connect.createStatement();
            statement.execute("begin;");
            statement.execute("DELETE from bear");
            bearsCashe.forEach(e->{
                try {
                    statement.execute(
                            "insert into bear(name, weight, isclean) VALUES('" +
                                    e.getName() + "', " + e.getWeight() + ", " + e.isClean() + ")");
                }catch (SQLException ex){
                    System.out.println("Беда при коммите мишек в БД");
                    ex.printStackTrace();
                }
            });
            statement.execute("commit;");
        }catch (Exception e){
            try {
                statement.execute("ROLLBACK; ");
            }catch (SQLException oops){
                System.out.println("Приехали. С данными беда, сисадмин кричит, программист молятся");
                oops.printStackTrace();};
        }
    }
    public void removeBear(int index){
        bearsCashe.remove(index);
    }
    public void changeBear(int index, Toy newBear){
        if(index >= 0 && index <bearsCashe.size())
            bearsCashe.set(index,newBear);
        else
            System.out.println("Попытка изменить медведя с несуществующим индексом");
    }
    public void insertBear(int index, Toy newBear){
        if(index > bearsCashe.size())
            bearsCashe.add(newBear);
        bearsCashe.add(index,newBear);
    }
    public void sortBears(){
        Collections.sort(bearsCashe);
    }
}