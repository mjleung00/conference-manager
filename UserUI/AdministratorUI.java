package UserUI;

import Controllers.UserController;
import UI.AdminCancelEventUI;
import UI.AdminDeleteConversationMessageUI;
import globallyAccessible.ExceedingMaxAttemptException;
import menuPresenter.AdminPresenter;

import java.util.Scanner;

/**
 * Represents a <code>AdministratorUI</code> extends from <code>OrganizerUI</code>.
 * Is specific for <code>Administrator</code> type usage.
 */
public class AdministratorUI extends OrganizerUI {

    private final AdminPresenter presenter = new AdminPresenter();

    public AdministratorUI(UserController userController) {
        super(userController);
    }

    @Override

    public void run(){
        availableAction = presenter.optionMenu();
        int action = 0;
        // replace with try-catch in while loop
        boolean enterAction = true;
        while(enterAction){
            Scanner scan = new Scanner(System.in);
            System.out.println(presenter.strAvailableActions(availableAction));
            if (scan.hasNextInt()) action = scan.nextInt();
            if (0 < action && action <= availableAction.size()) {
                runMethod(action);
            }
            else{
                System.out.println(presenter.strInvalidInput());
            }
            enterAction = continuing();
        }
        userController.logout();

    }

    private void runMethod (int action){
        try{
            switch (action) {
                case 1 : sendPrivateMessage(); break;
                case 2 : viewPrivateMessage(); break;
                case 3 : sendCoopMessage(); break;
                case 4 : viewCoopChat(); break;
                case 5 : deleteMessage(); break;
                case 6 : deleteEvent(); break;
            }
        }
        catch (ExceedingMaxAttemptException e){
            System.out.println("Exceeded maximum attempts allowed!!!");
        }
    }




    private void deleteMessage() throws ExceedingMaxAttemptException {
        new AdminDeleteConversationMessageUI(userController).run();
    }

    private void deleteEvent() throws ExceedingMaxAttemptException {

        new AdminCancelEventUI(userController).run();

    }

}
