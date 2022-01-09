package gps.library.logic.states;

import gps.library.logic.States;
import gps.library.logic.data.Model;

public class ReservationState extends StateAdapter{

    public ReservationState(Model model){
        super(model);
    }

    public States getAtualState(){
        return States.RESERVATION;
    }
}
