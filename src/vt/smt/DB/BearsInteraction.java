package vt.smt.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.sun.rowset.CachedRowSetImpl;
import vt.smt.Toy;

import javax.sql.rowset.CachedRowSet;

/**
 * Created by semitro on 21.04.17.
 */
public class BearsInteraction {
    private Properties user = new Properties();
    private Connection connect;
    private Statement statement;
    CachedRowSet rs;
    private BearsInteraction(){
        try {
            Class.forName("org.postgresql.Driver");
            user.setProperty("password", "bear");
            user.setProperty("name", "bear");
            connect = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bears",
                    user.getProperty("name"), user.getProperty("password"));
            statement = connect.createStatement();
            rs = new CachedRowSetImpl();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    static BearsInteraction instance;
    public static BearsInteraction getInstance(){
        if(instance == null)
            instance = new BearsInteraction();
         return instance;
    }
    public LinkedList<Toy> getAllBears(){
        try {
            rs.populate(statement.executeQuery("select * from bear"));
            LinkedList<Toy> ans = new LinkedList<>();

            while(rs.next()){
                ans.add(new Toy(
                        rs.getString("name"),
                        rs.getFloat("weight"),
                        rs.getBoolean("isClean")
                ));
            }
            return ans;
        }catch (SQLException e){
            System.out.println("BearsIneraction::getAllBears(): плохой запрос");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
