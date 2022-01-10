package gps.library.logic;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LibraryObservable {

    private Library lib;
    private final PropertyChangeSupport propertyChangeSupport;

    public LibraryObservable(Library lib){
        this.lib = lib;
        propertyChangeSupport = new PropertyChangeSupport(lib);
        propertyChangeSupport.firePropertyChange("UPDATE", null, null);
    }

    public void addProperty(String propertyName, PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void forceUpdate(){ propertyChangeSupport.firePropertyChange("UPDATE", null, null);}

    // states methods

    public void capacity(){
        lib.capacity();
        forceUpdate();
    }

    public void login(){
        lib.login();
        forceUpdate();
    }

    public void logout(){
        lib.logout();
        forceUpdate();
    }

    public void reserves(){
        lib.reserves();
        forceUpdate();
    }

    public void login(String mail, String password){
        lib.login(mail, password);
        forceUpdate();
    }

    public void register(String number, String mail, String password, String confPassword){
        lib.register(number, mail, password, confPassword);
        forceUpdate();
    }

    public void reserveOffice(){
        lib.reserveOffice();
        forceUpdate();
    }

    public void updateCapacity(int capacity){
        lib.updateCapacity(capacity);
        forceUpdate();
    }

    public void confirmReserve(int id){
        lib.confirmReserve(id);
        forceUpdate();
    }

    public void cancelReserve(int id){
        lib.cancelReserve(id);
        forceUpdate();
    }

    public void backToUser(){
        lib.backToUser();
        forceUpdate();
    }

    // method called directly to model

    public <T> boolean selectedDay(T day){
        return lib.selectedDay(day);
    }

    public <T> boolean selectedHours(T selected){
        return lib.selectedHours(selected);
    }

    public <T> boolean newReserve(T students){
        return lib.newReserve(students);
    }

    // getters from model

    public int getCapacity(){ return lib.getCapacity(); }

    public boolean getItworked(){
        return lib.getItworked();
    }

    public List<?> getReserves(){ return lib.getReserves(); }

    public HashMap<Integer, String[]> getAdminReserves() { return lib.getAdminReserves(); }

    public List<?> getHours(){ return lib.getHours(); }

    public int getPenalties(){ return lib.getPenalties(); }

    public States getAtualState(){ return lib.getAtualState(); }

}
