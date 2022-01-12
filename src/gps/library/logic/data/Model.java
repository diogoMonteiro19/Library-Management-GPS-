package gps.library.logic.data;

import gps.library.logic.connection.DBManager;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class Model {
    int capacity = 0;
    Timestamp day;
    int office_id;

    DBManager dbManager = new DBManager();

    /**
     * Login a user querying the database
     * @param //mail - user mail
     * @param //password - user password
     * @return {@code true} if success, {@code false} if failed.
     */
    public boolean login(String mail, String password){
        return dbManager.login(mail, password);
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
        if(!password.equals(confPassword)){
            dbManager.setItworked(false);
            return false;
        }
        return dbManager.createNewUser(mail, password, Integer.parseInt(number));
    }

    /**
     * @return {@code true} if user is logged, {@code false} if
     * he isn't
     */
    public boolean isLogged(){
        return dbManager.isLogged();
    }

    /**
     * Gets the state of the logged user.
     * @return true if it's an admin, false if it's a normal user
     */
    public boolean isAdmin(){
        return dbManager.isAdmin();
    }

    /**
     * Logout a user...Nothing to do with database
     */
    public void logout(){
        dbManager.logout();
    }


    /**
     * Queries the capacity from the database
     * and sets {@code capacity} to that value
     */
    public void queryCapacity()
    {
        dbManager.queryCapacity();
        capacity = dbManager.getCapacity();
    }

    /**
     * Updates the capacity on the database
     * @param capacity - percentage of capacity
     */
    public void updateCapacity(int capacity){
        dbManager.updateCapacity(capacity);
    }

    public void confirmReserve(int id){
        dbManager.confirmReserve(id);
    }

    /**
     * Cancels a reservation by it's id on the list?
     * Mudar a forma como é cancelada e o tipo de retorno caso
     * acham que vale a pena
     * @param id - the reservation {@code id} on the reserves list
     */
    public void cancelReserve(int id){
        dbManager.cancelReserve(id);
    }

    /**
     * Queries the database if there's offices available for a given day
     * and the available hours
     * @param day - the day
     * @param <T> - this can be a {@code String, Timestamp, LocalDate..}
     * @return {@code true} if there's available hours, {@code false} if there isn't
     */
    public <T> boolean selectedDay(T day){
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date parsedDate = (java.util.Date) dateFormat.parse((String) day);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            this.day = timestamp;
        } catch(Exception e) { //this generic but you can control another types of exception
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Stores the hours selected from the user
     * @param selectedFromUser - {@code List}
     * @param <T> - type from the {@code List}
     * @return - decide the type of return
     */
    public <T> boolean selectedHours(T selectedFromUser, T office_id){
        try {
            DateFormat dateFormat = new SimpleDateFormat("hh:mm");
            System.out.println(selectedFromUser);
            java.util.Date parsedDate = (java.util.Date) dateFormat.parse((String) selectedFromUser);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            day.setHours(timestamp.getHours());
            this.office_id = (int) office_id;

        } catch(Exception e) {
            e.printStackTrace();
        }
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
        dbManager.verifyStudents((List<Integer>) students);
        if(!dbManager.isItworked()){
            return false;
        }
        else {
            return dbManager.newReserve(day, (List<Integer>) students, office_id);
        }
    }

    /**
     * Gets the list of reserves queried from this user
     * @return a list of all reserves of this user
     */
    public HashMap<Integer, String[]> getReserves() {
        return dbManager.getReserves();
    }

    /**
     * Gets the list of reserves for the admin
     * @return a list of reserves from
     *
     */
    public HashMap<Integer, String[]> getAdminReserves() {
        return dbManager.getAdminReserves();
    }

    /**
     * Gets the actual capacity that was queried from the database
     * @return an {@code int} that represent the actual capacity
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * If everything went fine
     * @return {@code true} if everything went correctly,
     * {@code false} if something failed.
     */
    public boolean getItworked(){
        return dbManager.isItworked();
    }

    /**
     * Gets the hours that an office is available
     * @return a {@code List} with all the available hours
     */
    public List<?> getHours() {
        return dbManager.getHours(day);
    }

    /**
     * How much penalties a user have
     * @return {@code int} - the number of penalties
     */
    public int getPenalties(){
        return dbManager.getPenalties();
    }
}
