package gps.library.logic.data;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Model {
    int capacity = 19;
    boolean itworked = false;

    /**
     * @return {@code true} if user is logged, {@code false} if
     * he isn't
     */
    public boolean isLogged(){
        return true;
    }

    /**
     * Login a user querying the database
     * @param mail - user mail
     * @param password - user password
     * @return {@code true} if success, {@code false} if failed.
     */
    public boolean login(String mail, String password){
        return true;
    }

    /**
     * Register a user on the database
     * @param number - user number
     * @param mail - user mail
     * @param password - user password
     * @param confPassword - same password checker
     * @return {@code true} if success, {@code false} if failed.
     */
    public boolean register(String number, String mail, String password, String confPassword){
        return true;
    }

    /**
     * Logout a user...Nothing to do with database
     */
    public void logout(){

    }

    /**
     * Gets the reserves from the database for the given user
     */
    public void queryReserves(){

    }

    /**
     * Gets the reserves from the database to the admin
     */
    public void queryAdminReserves(){

    }

    /**
     * Queries the capacity from the database
     * and sets {@code capacity} to that value
     */
    public void queryCapacity()
    {

        try{
        //TODO: meter conn em variavel da classe?
        String myUrl = "jdbc:sqlite:library.db";
        Connection conn = DriverManager.getConnection(myUrl);
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("SELECT capacity FROM capacity");
        int capacity_queryed=-1;
        while (rs.next())
        {
            capacity_queryed = rs.getInt("capacity");
            break;
        }

        capacity = capacity_queryed;

        }catch (Exception e){

            capacity = -1;

        }
    }

    /**
     * Updates the capacity on the database
     * @param capacity - percentage of capacity
     */
    public void updateCapacity(int capacity){
        try{
            //TODO: meter conn em variavel da classe?
            String myUrl = "jdbc:sqlite:library.db";
            Connection conn = DriverManager.getConnection(myUrl);
            Statement st = conn.createStatement();

            st.executeUpdate("update capacity set capacity = " + capacity);
            itworked = true;
        }catch (Exception e){
            System.err.println("Problema na query à base de dados!!!!\nContacte o administrador do sistema");
            itworked=false;
        }
    }

    /**
     * Confirmation of a reserve from the admin
     * @param id - {@code int} representing the id
     *          of the reserve
     */
    public void confirmReserve(int id){

    }

    /**
     * Cancels a reservation by it's id on the list?
     * Mudar a forma como é cancelada e o tipo de retorno caso
     * acham que vale a pena
     * @param id - the reservation {@code id} on the reserves list
     */
    public void cancelReserve(int id){

    }

    /**
     * Queries the database if there's offices available for a given day
     * and the available hours
     * @param day - the day
     * @param <T> - this can be a {@code String, Timestamp, LocalDate..}
     * @return {@code true} if there's available hours, {@code false} if there isn't
     */
    public <T> boolean selectedDay(T day){
        // Fazer logo aqui query das horas que se pode ter
        return false;
    }

    /**
     * Stores the hours selected from the user
     * @param selectedFromUser - {@code List}
     * @param <T> - type from the {@code List}
     * @return - decide the type of return
     */
    public <T> boolean selectedHours(T selectedFromUser){
        return true;
    }

    /**
     * Makes a new reservation on database, using the information
     * gathered on {@code selectedDay()} and {@code selectedHours()}
     * @param students - a list of the students that are members of this
     *                 reservation
     * @param <T> - type of {@code param} received
     * @return - decide the type of return
     */
    public <T> boolean newReserve(T students){
        return true;
    }

    /**
     * Gets the list of reserves queried from this user
     * @return a list of all reserves of this user
     */
    public List<?> getReserves(){
        List<Timestamp> debug = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            Timestamp ts = Timestamp.from(Instant.now());
            debug.add(ts);
        }
        return debug;
    }

    /**
     * Gets the list of reserves for the admin
     * @return a list of reserves from
     * AQUI NAO SEI SE QUEREM METER DESTE DIA OU O QUE
     */
    public List<?> getAdminReserves(){
        List<Timestamp> debug = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            Timestamp ts = Timestamp.from(Instant.now());
            debug.add(ts);
        }
        return debug;
    }

    /**
     * Gets the actual capacity that was queried from the database
     * @return an {@code int} that represent the actual capacity
     */
    public int getCapacity(){
        return capacity;
    }
    public boolean getItworked(){
        return itworked;
    }

    /**
     * Gets the hours that an office is available
     * @return a {@code List} with all the available hours
     */
    public List<?> getHours(){
        List<Integer> debug = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            debug.add(10 + i);
        }
        return debug;
    }

    /**
     * How much penalties a user have
     * @return {@code int} - the number of penalties
     */
    public int getPenalties(){
        return 2;
    }
}
