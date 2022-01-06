package gps.library.logic.states;

import gps.library.logic.States;
import gps.library.logic.data.Model;

public class AdminState extends StateAdapter {

    public AdminState(Model model){
        super(model);
    }

    @Override
    public States getAtualState(){
        return States.ADMIN;
    }
}
