package gps.library.logic.states;

import gps.library.logic.States;

public interface IState {

    IState capacity();

    IState login();

    IState logout();

    IState reserves();

    IState login(String mail, String password);

    IState register(String number, String mail, String password, String confPassword);

    IState cancelReserve(int id);

    public States getAtualState();
}
