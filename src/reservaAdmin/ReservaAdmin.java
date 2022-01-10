package reservaAdmin;
import java.sql.*;

public class ReservaAdmin {



    public void UpdateReserve(int IdReserve){



            try {
                // create a mysql database connection
                String myUrl = "jdbc:sqlite:library.db";
                Connection conn = DriverManager.getConnection(myUrl);
                Statement st = conn.createStatement();

                st.executeUpdate( " update set reserves where reservers_id = "+ IdReserve + " confirm =1 ");



            }catch (Exception e)
            {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }

    }
    public void DeleteReserve(int IdReserve){

            try {
                // create a mysql database connection
                String myUrl = "jdbc:sqlite:library.db";
                Connection conn = DriverManager.getConnection(myUrl);
                Statement st = conn.createStatement();

                st.executeUpdate( "Delete from reserves where reservers_id = "+ IdReserve + "" );

            }catch (Exception e)
            {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }

    }


}
