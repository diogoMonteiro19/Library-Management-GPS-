package gps.library.logic;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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

    public void cancelReserve(int id){
        lib.cancelReserve(id);
        forceUpdate();
    }

    // getters from model

    public int getCapacity(){ return lib.getCapacity(); }

    public List<?> getReserves(){ return lib.getReserves(); }

    public States getAtualState(){ return lib.getAtualState(); }
}
