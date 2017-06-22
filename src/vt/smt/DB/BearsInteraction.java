package vt.smt.DB;

import com.sun.rowset.CachedRowSetImpl;
import vt.smt.Data.Toy;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by semitro on 21.04.17.
 */
public class BearsInteraction {
    private static final Logger log = Logger.getLogger(BearsInteraction.class.getName());

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
            bearsSet.populate(statement.executeQuery("select * from Toy"));
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
                Toy toy = new Toy(
                        bearsSet.getString("name"),
                        bearsSet.getFloat("weight"),
                        bearsSet.getBoolean("isClean")
                );
                toy.setCreationTime(
                        bearsSet.getDate("creationTime").toLocalDate().atStartOfDay(ZoneId.systemDefault()));
                ans.add(toy);
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
            statement.execute("DELETE from Toy");
            bearsCashe.forEach(bear->{
                try {
                    String ormStr = ORM.getInsertQuery(bear);
                    log.log(Level.INFO, ormStr);
                    statement.execute(ormStr);
//                    statement.execute(
//                            "insert into bear(name, weight, isclean) VALUES('" +
//                                    e.getName() + "', " + e.getWeight() + ", " + e.isClean() + ")");
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
        try {
            bearsCashe.remove(index);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Нас попросили удалить слишком мощного медведя");
            e.printStackTrace();
        }
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