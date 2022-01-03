package reservaAdmin;
import java.sql.*;

public class ReservaAdmin {



    public void UpdateReserve(int IdReserve){

            try {
                // create a mysql database connection
                String myDriver = "org.gjt.mm.mysql.Driver TODO:METER CONEXÕES A BD REAL";
                String myUrl = "jdbc:mysql://localhost/test TODO:METER CONEXÕES A BD REAL";
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "root", "");

                String query = " update set reserves where reservers_id = "+ IdReserve + " confirm =1 ";


            }catch (Exception e)
            {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }

    }
    public void DeleteReserve(int IdReserve){

            try {
                // create a mysql database connection
                String myDriver = "org.gjt.mm.mysql.Driver TODO:METER CONEXÕES A BD REAL";
                String myUrl = "jdbc:mysql://localhost/test TODO:METER CONEXÕES A BD REAL";
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "root", "");

                String query = "Delete from reserves where reservers_id = "+ IdReserve + "" ;

            }catch (Exception e)
            {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }

    }


}
