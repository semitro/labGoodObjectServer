package vt.smt.DB;
import com.sun.rowset.CachedRowSetImpl;
import org.postgresql.Driver;
import sun.util.resources.cldr.sah.CalendarData_sah_RU;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.rowset.CachedRowSet;

public class MainTester {
    public static void main(String[] arg) {
        try {
            Class.forName("org.postgresql.Driver");
            Properties user = new Properties();
            user.setProperty("password","bear");
            user.setProperty("name","bear");
            Connection connect =  DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/bears",
                    user.getProperty("name"),user.getProperty("password"));
            Statement statement = connect.createStatement();
            CachedRowSet rs = new CachedRowSetImpl();
            rs.populate(statement.executeQuery("select * from bear"));
            while (rs.next()){
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("weight"));
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
