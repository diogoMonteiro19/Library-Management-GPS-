package gps.library.logic.states;

import gps.library.logic.States;
import gps.library.logic.data.Model;

public class UserState extends StateAdapter{

    public UserState(Model model){
        super(model);
    }

    @Override
    public IState logout() {
        getModel().logout();
        return new InitialState(getModel());
    }

    @Override
    public IState capacity() {
        getModel().queryCapacity();
        return new InitialState(getModel());
    }

    @Override
    public IState cancelReserve(int id) {
        getModel().cancelReserve(id);
        return new UserState(getModel());
    }

    @Override
    public States getAtualState() {
        return States.USER;
    }
}
