package gps.library.logic.states;

import gps.library.logic.States;
import gps.library.logic.data.Model;

public abstract class StateAdapter implements IState {
    private final Model model;

    public StateAdapter(Model model){ this.model = model; }

    @Override
    public IState capacity() {
        return this;
    }

    @Override
    public IState login() {
        return this;
    }

    @Override
    public IState logout() {
        return this;
    }

    @Override
    public IState reserves() {
        return this;
    }

    @Override
    public IState login(String mail, String password) {
        return this;
    }

    @Override
    public IState register(String number, String mail, String password, String confPassword) {
        return this;
    }

    @Override
    public IState reserveOffice() {
        return new ReservationState(getModel());
    }

    @Override
    public IState updateCapacity(int capacity) {
        return this;
    }

    @Override
    public IState confirmReserve(int id) {
        return this;
    }

    @Override
    public IState cancelReserve(int id) {
        return this;
    }

    @Override
    public IState backToUser() {
        return new UserState(getModel());
    }

    public final Model getModel(){ return model; }

}
