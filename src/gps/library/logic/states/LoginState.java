package gps.library.logic.states;

import gps.library.logic.States;
import gps.library.logic.data.Model;

public class LoginState extends StateAdapter {

    public LoginState(Model model){
        super(model);
    }

    @Override
    public IState capacity() {
        getModel().queryCapacity();
        return new InitialState(getModel());
    }

    @Override
    public IState login(String mail, String password) {
        if(getModel().login(mail, password)){
            if(getModel().isAdmin()){
                return new AdminState(getModel());
            }
            return new UserState(getModel());
        }
        else{
            return new LoginState(getModel());
        }

        // TODO aqui tambem pode ir para o admin state
    }

    @Override
    public IState register(String number, String mail, String password, String confPassword) {
        if(getModel().register(number, mail, password, confPassword)){
            return new UserState(getModel());
        }
        else{
            return new LoginState(getModel());
        }
    }

    @Override
    public States getAtualState() {
        return States.LOGIN_REGISTER;
    }
}
