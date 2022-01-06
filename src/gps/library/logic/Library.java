package gps.library.logic;

import gps.library.logic.data.Model;
import gps.library.logic.states.IState;
import gps.library.logic.states.InitialState;
import gps.library.logic.states.UserState;
import gps.library.logic.states.AdminState;
import gps.library.ui.graphic.states.LoginStatePane;

import java.util.List;

public class Library {
    private Model model;
    private IState state;

    public Library(){
        model = new Model();
//        state = new InitialState(model);
//        state = new UserState(model);
        state = new AdminState(model);
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

    public void cancelReserve(int id){
        setState(state.cancelReserve(id));
    }

    // getters from model

    public int getCapacity(){ return model.getCapacity(); }

    public List<?> getReserves(){ return model.getReserves(); }

    public States getAtualState(){ return state.getAtualState(); }
}
