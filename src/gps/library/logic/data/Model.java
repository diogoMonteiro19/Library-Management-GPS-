package gps.library.logic.data;

import logIn.LogIn;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class Model {
    int capacity = 19;
    LogIn novoLogin;

    public Model(){
        novoLogin = new LogIn();
    }
    /**
     * @return {@code true} if user is logged, {@code false} if
     * he isn't
     */


    public boolean isLogged(){
        return novoLogin.isLogged();
    }
    public boolean isAdmin(){
        return novoLogin.isAdmin();
    }
    /**
     * Login a user querying the database
     * @param mail - user mail
     * @param password - user password
     * @return {@code true} if success, {@code false} if failed.
     */
    public boolean login(String mail, String password){
        novoLogin = new LogIn(mail,password);
        return novoLogin.verifyLogInExists();
    }

    /**
     * Register a user on the database
     * @param number - user number
     * @param mail - user mail
     * @param password - user password
     * @param confPassword - same password checker
     * @return {@code true} if success, {@code false} if failed.
     */
    public boolean register(String number, String mail, String password,String confPassword){
        if(password.equals(confPassword)){
            novoLogin = new LogIn(mail,password,Integer.parseInt(number));
            return novoLogin.createNewUser();
        }
        return false;
    }

    /**
     * Logout a user...Nothing to do with database
     */
    public void logout(){
        novoLogin.logout();
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
    public void queryCapacity(){

    }

    /**
     * Updates the capacity on the database
     * @param capacity - percentage of capacity
     */
    public void updateCapacity(int capacity){

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
