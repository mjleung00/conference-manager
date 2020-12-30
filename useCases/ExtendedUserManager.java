package useCases;

import entities.User;
import globallyAccessible.UserNotFoundException;
import globallyAccessible.UserType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ExtendedUserManager implements Serializable {
    /**
     * A list contains all Users with all types <code>User</code>
     * */
    protected HashMap<UserType, ArrayList<User>> allUsers;

    /**
     * A integer representing the number of users who are currently using the system.
     * */
    protected int num_user;

    /**
     * The user who is currently using the program.
     * */
    protected User currentUser;


    public ExtendedUserManager(){
        allUsers = new HashMap<>();
        for(UserType type: UserType.values()){
            allUsers.put(type, new ArrayList<>());
        }
        num_user = 1;
        currentUser = null;
    }

    /**
     * Creates a <code>addUser</code> and add Users to the corresponding TypeList if the Users are not inside and
     * update the num_user.
     * @param users is the user that needs to be added in the list.
     * @param type is the UserType for the user that needs to be added in the list.
     */
    public void addUser(User users, UserType type){
        if (!allUsers.containsKey(type)){
            allUsers.put(type, new ArrayList<>());
        }
        allUsers.get(type).add(users);
        num_user += 1;
    }

    /**
     * Creates a <code>isUser</code> and check the index of the User, if not all Users contain key, return 0.
     * @param username is the username of user that needs to be checked.
     * @return the index of the User, if not all Users contain key, return 0.
     */
    public int isUser(String username) {
        if (findUser(username) == null){
            return 0;
        }
        return 1;
    }

    /**
     * Creates a <code>findUser</code> and get the user.
     * @param userName is the name of the other user that we want to find.
     * @return the user that we want to find.
     */
    protected User findUser(String userName){
        for (User users: getAllUsers()){
            if (users.getUsername().equals(userName)){
                return users;
            }
        }
        return null;
    }

    /** check whether the password is correct
     @return type of user if password is correct, "invalid" otherwise.
      * @param username the name of user currently logging in.
     * @param passcode the password user has input to login.
     */
    public UserType loginCheck(String username, String passcode) throws UserNotFoundException {
        ArrayList<User> allExistingUser = getAllUsers();
        for (User currUser : allExistingUser) {
            if (currUser.getUsername().equals(username) &&
                    currUser.getPassword().equals(passcode)) {
                currentUser = currUser;
                return currUser.getUserType();
            }
        }
        throw new UserNotFoundException("Wrong login info.");
    }

    /**
     * Creates a <code>currentUsername</code> and get the users' current Username.
     * @return current UserName of the user.
     */
    public String currentUsername(){
        return currentUser.getUsername();
    }

    /**
     * Creates a <code>getAllUsers</code> and get all Users in all the UserType.
     * @return returns all the users in all the UserTypes.
     */
    private ArrayList<User> getAllUsers(){
        ArrayList<User> allUser = new ArrayList<>();
        for (UserType userType : allUsers.keySet()){
            allUser.addAll(allUsers.get(userType));
        }
        return allUser;

    }

    /**
     * Creates a <code>setPassword</code> and set the password of the user's account.
     * @param newPassword is the new password the user want to set.
     */
    public void setPassword(String newPassword){
        currentUser.setPassword(newPassword);}

    /**
     * Creates a <code>logout</code> and set the status of user's account to be logout.
     */
    public void logout(){
        currentUser = null;
    }

}
