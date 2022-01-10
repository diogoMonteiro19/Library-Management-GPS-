package gps.library.logic.states;

import gps.library.logic.States;
import gps.library.logic.data.Model;

public class InitialState extends StateAdapter {

    int i = 0;
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
        getModel().queryReserves();
        return new UserState(getModel());
    }

    @Override
    public States getAtualState() {
        // ir ver ao modelo se está logado e retornar consoante isso
        return States.INITIAL_LOGOUT;
    }
}
