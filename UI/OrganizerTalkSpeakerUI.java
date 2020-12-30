package UI;

import Controllers.TalkRescheduleSpeakerController;
import Controllers.UserController;
import globallyAccessible.ExceedingMaxAttemptException;
import globallyAccessible.NoEventsException;

import java.util.ArrayList;

/**
 * UI for <code>Organizer</code> reschedule speaker to certain event.
 */
public class OrganizerTalkSpeakerUI extends OrganizerRescheduleSpeakerUI {

    /**
     * An instance of <code>TalkRescheduleSpeakerController</code> being instantiated.
     */
    private TalkRescheduleSpeakerController talkRescheduleSpeaker;

    /**
     * Instantiates new <codeOrganizerTalkSpeakerUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public OrganizerTalkSpeakerUI(UserController userController) {
        super(userController);
        talkRescheduleSpeaker = new TalkRescheduleSpeakerController(userController);
    }


    /**
     * Chooses event and schedule a valid speaker to this event.
     * @throws ExceedingMaxAttemptException when user exceed max attempt.
     */
    @Override
    public void run() throws ExceedingMaxAttemptException {
        try{
            String actID = eventSelect();
            String speaker = determineSpeakerValidity(actID);
            talkRescheduleSpeaker.updateRescheduledSpeaker(speaker);
        } catch(NoEventsException e) {
            System.out.println(organizerRescheduleSpeakerPresenter.strInvalidEventID());
        }
    }

    private String determineSpeakerValidity(String actID) throws NoEventsException, ExceedingMaxAttemptException {
        ArrayList<String> availableSpeakers = talkRescheduleSpeaker.availableSpeakers(actID);
        if(availableSpeakers.size() == 0) {
            throw new NoEventsException("No available speakers for this period!!!");
        }
        return chooseSpeaker(availableSpeakers, "assign");
    }

    private String eventSelect() throws NoEventsException {
        ArrayList<String[]> allActivities = talkRescheduleSpeaker.getAllEvents();
        System.out.println(organizerRescheduleSpeakerPresenter.strAllEventMenuDes());
        System.out.println(organizerRescheduleSpeakerPresenter.strSchedule(allActivities));
        return inputSelection(allActivities, talkRescheduleSpeaker);
    }
}
