package logIn;
import java.sql.*;


public class LogIn {
    private String email,password;
    private int students_id;

    public LogIn(String email,String password,int students_id){
        this.email=email;
        this.password = password;
        this.students_id  = students_id;
    }

    public boolean verifyLogInExists(){
        String query = "SELECT * FROM users";
        try {
            String myDriver = "org.gjt.mm.mysql.Driver TODO:METER CONEXÕES A BD REAL";
            String myUrl = "jdbc:mysql://localhost/test TODO:METER CONEXÕES A BD REAL";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                if(email == rs.getString("mail") && password == rs.getString("password")){
                    return true;
                }
            }
        }catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return false;
    }

    public void createNewUser(){
        if(!verifyLogInExists()){
            try {
                // create a mysql database connection
                String myDriver = "org.gjt.mm.mysql.Driver TODO:METER CONEXÕES A BD REAL";
                String myUrl = "jdbc:mysql://localhost/test TODO:METER CONEXÕES A BD REAL";
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "root", "");

                String query = " insert into users (password,mail,students_id)"
                        + " values (?, ?, ?)";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, password);
                preparedStmt.setString(2, email);
                preparedStmt.setInt(3, students_id);
            }catch (Exception e)
            {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }
        }
    }
}
