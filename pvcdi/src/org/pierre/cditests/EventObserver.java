package org.pierre.cditests;

import javax.enterprise.event.Observes;
import javax.inject.Named;

@Named
public class EventObserver {

    public void observeEvent(@Observes String message){
    	System.out.println("EVENT " + message);
    }

}