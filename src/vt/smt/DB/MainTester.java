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
        System.out.println(BearsInteraction.getInstance().getAllBears());

    }
}
