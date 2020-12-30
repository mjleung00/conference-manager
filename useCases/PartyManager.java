package useCases;

import entities.Event;
import entities.Party;
import globallyAccessible.EventType;
import java.util.UUID;

/**
 * This manager manages all of the Party events
 * Not much added functionality to be honest
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class PartyManager extends EventManager {

    /**
     * Creates a Party Manager that exclusively have Party Events in it
     * @param eventManager the original event manager
     */
    public PartyManager(EventManager eventManager){
        super(EventType.PARTY, eventManager.upcomingEvents.get(EventType.PARTY), eventManager.archivedEvents.get(EventType.PARTY));
    }

    /**
     * Return the type this manager is managing in String
     * @return the String "Party"
     */
    public String getTypeStr(){
        return "Party";
    }

    /**
     * Find the event this ID is representing
     * @param actID the ID of event we want to find
     * @return the Party event
     */
    @Override
    protected Party findEvent(UUID actID) {
        Party targetAct =  null;
        for(Event i: this.upcomingEvents.get(EventType.PARTY)){
            if(i.getIdentity().equals(actID)){
                targetAct = (Party) i;
            }
        }
        return targetAct;
    }
}
