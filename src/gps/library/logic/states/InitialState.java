package gps.library.logic.states;

import gps.library.logic.States;
import gps.library.logic.data.Model;

public class InitialState extends StateAdapter {
    public InitialState(Model model){
        super(model);
    }

    @Override
    public IState capacity() {
        getModel().queryCapacity();
        return new InitialState(getModel());
    }

    @Override
    public IState login() {
        return new LoginState(getModel());
    }

    @Override
    public IState logout() {
        getModel().logout();
        return new InitialState(getModel());
    }

    @Override
    public IState reserves() {
        return new UserState(getModel());
    }

    @Override
    public States getAtualState() {
        if(getModel().isLogged()){
            return States.INITIAL_LOGIN;
        }
        return States.INITIAL_LOGOUT;
    }
}
