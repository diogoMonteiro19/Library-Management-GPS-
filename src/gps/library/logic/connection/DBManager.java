package gps.library.logic.connection;

import logIn.LogIn;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DBManager {
    Connection conn;
    Statement stmt;
    private boolean logged = false;
    private boolean isAdmin = false;
    boolean itworked = false;
    int capacity = 0;
    int id = 0;
    HashMap<Integer, String[]> reserva;
    List<Integer> students;



    public DBManager(){
        String myUrl = "jdbc:sqlite:library.db";
        try {
            conn = DriverManager.getConnection(myUrl);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        reserva = new HashMap<>();
        students = new ArrayList<>();
    }

    public boolean verifyLogInExists(String email, String password){
        String query = "SELECT * FROM users";

        try {
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                if(email.equals(rs.getString("mail")) && password.equals(rs.getString("password"))){
                    if(rs.getInt("students_id") == 0){
                        isAdmin = true;
                    }
                    else{
                        isAdmin = false;
                    }
                    logged = true;
                    itworked = true;
                    return true;
                }else{
                    itworked = false;
                }
            }

        }catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            itworked = false;
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;//pessoa nao existe na BD
    }


    public boolean login(String mail, String password){
        try {
            stmt = conn.createStatement();
            String query = "SELECT user_id FROM users where mail ='"+mail +"'";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){
                id = rs.getInt("user_id");
            }
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            itworked = false;
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return verifyLogInExists(mail, password);
    }

    public boolean createNewUser(String email, String password, int students_id) {
        boolean verify = false;
        if (!verifyLogInExists(email, password)) {
            verify =true;
        }
        if(verify) {
            try {
                String query = " insert into users (password,mail,students_id)"
                        + " values ('" + password + "','" + email + "'," + students_id + ")";
                stmt = conn.createStatement();
                if (stmt.executeUpdate(query) < 1) {
                    return false;
                }
                logged = true;
                itworked = true;
                return true;
            } catch (Exception e) {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
                itworked = false;
                return false;
            } finally {
//                if (conn2 != null) {
//                    try {
//                        conn2.close();
//                    } catch (Exception e) {
//                        System.err.println("Got an exception!");
//                        System.err.println(e.getMessage());
//                        return false;
//                    }
//                }
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void updateCapacity(int capacity){
        try{
            //TODO: meter conn em variavel da classe?
            stmt = conn.createStatement();

            stmt.executeUpdate("update capacity set capacity = " + capacity);
            itworked = true;
        }catch (Exception e){
            System.err.println("Problema na query à base de dados!!!!\nContacte o administrador do sistema" + e);
            itworked=false;
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void queryCapacity(){

        try{
            //TODO: meter conn em variavel da classe?
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT capacity FROM capacity");
            int capacity_queryed=-1;
            while (rs.next())
            {
                capacity_queryed = rs.getInt("capacity");
                break;
            }

            capacity = capacity_queryed;

        }catch (Exception e){
            capacity = -1;
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void confirmReserve(int id){

        try {
            // create a mysql database connection
            stmt = conn.createStatement();

            stmt.executeUpdate( " update reserves set confirm =1 where reserves_id = "+ id + "");

        }catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelReserve(int id){
        try {
            // create a mysql database connection
            stmt = conn.createStatement();

            stmt.executeUpdate( "Delete from reserves where reserves_id = "+ id + "" );
        }catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<Integer, String[]> getReserves() {

        int  iduser=0, idreserve=0;
        Timestamp date;
        String /*date,*/ confirm, id_office;

        HashMap<Integer, String[]> reserva = new HashMap<Integer, String[]>();

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT reservers_reserves_id FROM users_has_reserves  Where users_user_id = "  + id); // alterar
//            stmt.close();
            while (rs.next()) {
                stmt = conn.createStatement();
                idreserve = rs.getInt("reservers_reserves_id");

                ResultSet rsss = stmt.executeQuery("SELECT office_id, date, confirm FROM reserves  Where reserves_id = " + idreserve + "");

                while (rsss.next()) {
                    id_office = rsss.getString("office_id");
                    date = rsss.getTimestamp("date");
                    confirm = rsss.getString("confirm");

                    java.util.Date data = new java.util.Date();
                    data.setTime(date.getTime());
                    String formattedDate = new SimpleDateFormat("dd-MM-yyyy hh:mm").format(data);

                    String[] aux = new String[3];
                    aux[0] = formattedDate;
                    aux[1] = confirm;
                    aux[2] = id_office;

                    reserva.put(idreserve, aux);
                }
                rsss.close();
            }
            rs.close();
            return reserva;

        } catch (Exception e) {

            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return null;
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<Integer, String[]> getAdminReserves() {

        int id = 0;

        HashMap<Integer, String[]> reserva = new HashMap<Integer, String[]>();
        Timestamp date;
        String /*date,*/ confirm;

        try {
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT reserves_id, date, confirm FROM reserves");
//            stmt.close();
            while (rs.next()) {
                id = rs.getInt("reserves_id");
                date = rs.getTimestamp("date");
                confirm = rs.getString("confirm");

                java.util.Date data = new java.util.Date();
                data.setTime(date.getTime());
                String formattedDate = new SimpleDateFormat("dd-MM-yyyy hh:mm").format(data);

                String[] aux = new String[2];
                aux[0] = formattedDate;
                aux[1] = confirm;
                reserva.put(id, aux);

            }
            rs.close();
            return reserva;

        } catch (Exception e) {

            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return null;

        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPenalties(){

        try{
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT penalties FROM users where user_id = " + id);
            Timestamp penalties_queryed = null;
            while (rs.next())
            {

                penalties_queryed = rs.getTimestamp("penalties");
                break;
            }

            if(penalties_queryed.getYear() == 70){
                return 0;
            }

            LocalDateTime now = LocalDateTime.now();
            long time[] = getTime(now, penalties_queryed.toLocalDateTime());
            System.out.println(time[0]);
            System.out.println(time);
            if(time[0] > 0)
            {
                return (int) time[0];//(int) penalties_queryed;
            }

        }catch (Exception e){
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;

    }

    private static long[] getTime(LocalDateTime dob, LocalDateTime now) {
        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long hours = seconds / (60*60);
        long minutes = ((seconds % (60*60)) / 60);
        long secs = (seconds % 60);

        return new long[]{hours, minutes, secs};
    }

    public List<?> getHours(Timestamp day) {
        List<Integer> horas = new ArrayList<>(14);

        LocalDateTime t1 = LocalDateTime.now();
        int actualHour = t1.getHour();
        for(int i = 0; i<14; i++){
            horas.add(0);
        }
        for(int i = actualHour; i>= 9; i--){
            horas.set(i-9, 5);
        }
        try{
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select count(date) AS number, date\n" +
                    "from reserves\n" +
                    "where date like '" + day.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "%'\n" +
                    "group by date\n" +
                    "order by date asc");
            while (rs.next())
            {
                Timestamp time = rs.getTimestamp("date");

                int hour = time.toLocalDateTime().getHour();
                if(horas.get(hour-9) == 0) {
                    horas.set(hour - 9, rs.getInt("number"));
                }
            }

            rs.close();
            return horas;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void verifyStudents(List<Integer> student){
        try {
            stmt = conn.createStatement();
            students.clear();
            int conta = 0;
            for (int i = 0; i < student.size(); i++) {
                ResultSet rs2 = stmt.executeQuery("SELECT * FROM users where students_id = " + student.get(i));
                while (rs2.next()) {
//                    int user_id = rs2.getInt("user_id");
//                    stmt.executeUpdate("insert into users_has_reserves(users_user_id, reservers_reserves_id, reserver)\n" +
//                            "values (" + user_id + ", " + reserve_id + ", 0)");
                    students.add(rs2.getInt("user_id"));
                    conta++;
                }
            }
            if(conta == student.size()){
                itworked = true;
            }
            else{
                itworked = false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean newReserve(Timestamp day, List<Integer> students, int office_id){
        try{
            stmt = conn.createStatement();

            stmt.executeUpdate("insert into reserves(date, office_id, confirm)\n" +
                    "values ('"+ day + "', " + office_id + ", 0)");
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int reserve_id = rs.getInt(1);
            stmt.executeUpdate("insert into users_has_reserves(users_user_id, reservers_reserves_id, reserver)\n" +
                    "values (" + id + ", " + reserve_id + ", 1)");
            rs.close();
            for(int i = 0; i<this.students.size(); i++) {
                stmt.executeUpdate("insert into users_has_reserves(users_user_id, reservers_reserves_id, reserver)\n" +
                    "values (" + this.students.get(i) + ", " + reserve_id + ", 0)");
            }

            stmt.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void logout(){
        logged = false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getId(){
        return id;
    }

    public boolean isItworked(){
        return itworked;
    }

    public int getCapacity(){
        return capacity;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setItworked(boolean itworked) {
        this.itworked = itworked;
    }
}
