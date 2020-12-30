package entities;

import globallyAccessible.UserType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a <code>Attendee</code>.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class Attendee extends User implements Serializable {

    /**
     * List of <code>UUID</code> corresponding to requests submitted by this user.
     */
    private ArrayList<UUID> requests;

    /**
     * Creates a new entities.Attendee with given name, password, and type.
     *
     * @param name     the valid name of this user, which helps to identify.
     * @param password the valid password of this user.
     */
    public Attendee(String name, String password) {
        super(name, password, UserType.ATTENDEE);
        requests = new ArrayList<>();
    }

    /**
     * Getter for list of <code>UUID</code>s corresponding to requests created by this user.
     * @return ArrayList of <code>UUID</code> of requests created by this user.
     */
    public ArrayList<UUID> getRequests() {
        return requests;
    }

    /**
     * Changes the list of <code>Request</code>s made by this user to be the new input list.
     * @param requests New list with which the existing list of <code>Request</code>s will be replaced with.
     */
    public void setRequests(ArrayList<UUID> requests) {
        this.requests = requests;
    }
}
