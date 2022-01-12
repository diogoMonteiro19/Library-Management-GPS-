package logIn;
import java.sql.*;


public class LogIn {
    private String email,password;
    private int students_id;
    private boolean logged = false;
    private boolean isAdmin = false;

    public int getStudents_id() {
        return students_id;
    }

    public String getEmail() {
        return email;
    }

    public LogIn (){}

   /* public LogIn(String email,String password ){
        this.email=email;
        this.password = password;


    }*/
    public LogIn(String email,String password,int students_id ){
        this.email=email;
        this.password = password;
        this.students_id = students_id;

    }

    public boolean verifyLogInExists(){
        String query = "SELECT * FROM users";

        try {
            //Class.forName(myDriver);
            Connection conn;
            String myUrl = "jdbc:sqlite:library.db";

            conn = DriverManager.getConnection(myUrl);

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                if(email.equals(rs.getString("mail")) && password.equals(rs.getString("password"))){
                    if(rs.getInt("students_id") == 0){
                        isAdmin = true;
                    }
                    logged = true;
                    return true;//Pessoa ja existe na BD
                }
            }

        conn.close();

        }catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        System.out.println(email);
        return false;//pessoa nao existe na BD
    }

    public boolean createNewUser() {

        Connection conn2 = null;
        boolean verify = false;
        if (!verifyLogInExists()) {
            verify =true;
        }
        if(verify ==true) {
            try {

                // create a mysql database connection

                String myUrl = "jdbc:sqlite:library.db";

                conn2 = DriverManager.getConnection(myUrl);

                String query = " insert into users (password,mail,students_id)"
                        + " values ('" + password + "','" + email + "'," + students_id + ")";
                Statement st2 = conn2.createStatement();
                if (st2.executeUpdate(query) < 1) {
                    return false;
                }
                logged = true;
                return true;


            } catch (Exception e) {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
                return false;
            } finally {
                if (conn2 != null) {
                    try {
                        conn2.close();
                    } catch (Exception e) {
                        System.err.println("Got an exception!");
                        System.err.println(e.getMessage());
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean isLogged() {
        return logged;
    }

    public boolean isAdmin() {
        return isAdmin;
    }


    public void logout(){
        logged = false;
    }
}
