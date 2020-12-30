package UI;

import Controllers.SendEventMessageController;
import Controllers.UserController;
import menuPresenter.SpeakerPresenter;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * UI for <code>Speaker<code/> send event message.
 */
public class SpeSendEventMessagesUI extends AbstractUI {
    /**
     * An instance of <code>SpeakerPresenter</code> being instantiated.
     */
    private SpeakerPresenter speakerPresenter = new SpeakerPresenter();

    /**
     * An instance of <code>SendEventMessageController</code> being instantiated.
     */
    private SendEventMessageController eventMessager;

    /**
     * Instantiates new <code>SpeSendEventMessagesUI</code>.
     * @param userController: An instance of <>UserController</>.
     */
    public SpeSendEventMessagesUI(UserController userController) {
        super(userController);
        eventMessager = new SendEventMessageController(userController);
    }

    /**
     * Gets event the <code>Speaker<code/> and sends the message for people attending this event.
     */
    @Override
    public void run() {
        ArrayList<String[]> info = presentEnrolledActivities(eventMessager);
        if (info == null) return;
        majorProcessor(info);
    }

    private void majorProcessor(ArrayList<String[]> info) {
        for(int i = 0; i < 3; i++){
            try{
                findAndSendMessage(eventMessager, info);
                return;
            }catch(IndexOutOfBoundsException e){
                System.out.println(speakerPresenter.strInvalidIndex());
            }
        }
        System.out.println("Run out of max attempts");
    }

    private ArrayList<String[]> presentEnrolledActivities(SendEventMessageController activityMessager) {
        ArrayList<String[]> info = activityMessager.showEnrolledSchedule();
        if (info.size() == 0){
            System.out.println(speakerPresenter.strEventNull());
            return null;
        }
        System.out.println(speakerPresenter.strEnrolledMenuDes());
        System.out.println(speakerPresenter.strSchedule(info));
        return info;
    }

    private void findAndSendMessage(SendEventMessageController activityMessager, ArrayList<String[]> info) {
        int actID = determineChatIDValidity(info);
        Scanner messageScanner = new Scanner(System.in);
        System.out.println(speakerPresenter.strMessagePrompt());
        String message = messageScanner.nextLine();
        activityMessager.sendEventMessage(actID, message);
    }

    private int determineChatIDValidity(ArrayList<String[]> info)
            throws IndexOutOfBoundsException{
        Scanner actIDScanner = new Scanner(System.in);
        System.out.println(speakerPresenter.strEventMessagePrompt());
        int actID = actIDScanner.nextInt();
        if (actID < 1 || actID > info.size()){
            throw new IndexOutOfBoundsException("invalid index for chat");
        }
        return actID;
    }
}
