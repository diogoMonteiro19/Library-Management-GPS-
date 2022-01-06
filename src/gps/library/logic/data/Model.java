package gps.library.logic.data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Model {
    int capacity = 50;


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
     * Queries the capacity from the database
     * and sets {@code capacity} to that value
     */
    public void queryCapacity(){

    }

    /**
     * Cancels a reservation by it's id on the list?
     * Mudar a forma como é cancelada e o tipo de retorno caso
     * acham que vale a pena
     * @param id - the reservation {@code id} on the reserves list
     */
    public void cancelReserve(int id){

    }

    public List<?> getReserves(){
        List<Timestamp> debug = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            Timestamp ts = Timestamp.from(Instant.now());
            debug.add(ts);
        }
        return debug;
    }

    public int getCapacity(){
        return capacity;
    }

}
