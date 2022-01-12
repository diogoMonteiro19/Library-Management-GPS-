package gps.library.logic;

import gps.library.logic.data.Model;
import gps.library.logic.states.*;

import java.util.HashMap;
import java.util.List;

public class Library {
    private Model model;
    private IState state;

    public Library(){
        model = new Model();
        state = new InitialState(model);
//        state = new ReservationState(model);
//       state = new AdminState(model);
        state.capacity();
    }

    private void setState(IState state){ this.state = state; }

    // states methods

    public void capacity(){ setState(state.capacity() );}

    public void login(){ setState(state.login()); }

    public void logout(){ setState(state.logout()); }

    public void reserves(){ setState(state.reserves()); }

    public void login(String mail, String password){
        setState(state.login(mail, password));
    }

    public void register(String number, String mail, String password, String confPassword){
        setState(state.register(number, mail, password, confPassword));
    }

    public void reserveOffice(){
        setState(state.reserveOffice());
    }

    public void updateCapacity(int capacity){
        setState(state.updateCapacity(capacity));
    }

    public void confirmReserve(int id){
        setState(state.confirmReserve(id));
    }

    public void cancelReserve(int id){
        setState(state.cancelReserve(id));
    }

    public void backToUser(){
        setState(state.backToUser());
    }

    // method called directly to model

    public <T> boolean selectedDay(T day){
        return model.selectedDay(day);
    }

    public <T> boolean selectedHours(T selected, T office_id){
        return model.selectedHours(selected, office_id);
    }

    public <T> boolean newReserve(T students){
        return model.newReserve(students);
    }

    // getters from model

    public int getCapacity(){ return model.getCapacity(); }

    public boolean getItworked(){
        return model.getItworked();
    }

    public HashMap<Integer, String[]> getReserves(){ return model.getReserves(); }

    public HashMap<Integer, String[]> getAdminReserves() { return model.getAdminReserves(); }

    public List<?> getHours() { return model.getHours(); }

    public int getPenalties(){ return model.getPenalties(); }

    public States getAtualState(){ return state.getAtualState(); }
}
