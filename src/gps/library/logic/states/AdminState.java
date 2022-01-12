package gps.library.logic.states;

import gps.library.logic.States;
import gps.library.logic.data.Model;

public class AdminState extends StateAdapter {

    public AdminState(Model model){
        super(model);
    }

    @Override
    public IState updateCapacity(int capacity) {
        getModel().updateCapacity(capacity);
        return new AdminState(getModel());
    }

    @Override
    public IState confirmReserve(int id) {
        getModel().confirmReserve(id);
        return new AdminState(getModel());
    }

    @Override
    public IState cancelReserve(int id) {
        getModel().cancelReserve(id);
        return new AdminState(getModel());
    }

    @Override
    public IState logout() {
        getModel().logout();
        return new InitialState(getModel());
    }

    @Override
    public States getAtualState(){
        return States.ADMIN;
    }
}
