package vt.smt.DB;

import java.sql.*;
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
            user.setProperty("password", "bear");
            user.setProperty("name", "bear");
            connect = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bears",
                    user.getProperty("name"), user.getProperty("password"));
            statement = connect.createStatement();
            bearsSet = new CachedRowSetImpl();
            bearsSet.populate(statement.executeQuery("select * from Bear"));
            bearsCashe = getBearsFromDB();
            System.out.println(bearsCashe);
        }catch (Exception e){
            System.out.println(e.getMessage());
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
    // Добавить синхронизацию!!
    public void removeBear(int index){
        bearsCashe.remove(index);
    }
    public void changeBear(int index, Toy newBear){
        if(index >= 0 && index <bearsCashe.size())
            bearsCashe.set(index,newBear);
        else
            System.out.println("Попытка изменить медведя с несуществующим индексом");
    }
}
