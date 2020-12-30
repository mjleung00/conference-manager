package entities;

import globallyAccessible.UserType;

import java.io.Serializable;

/**
 * Represents a <code>Organizer</code>.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class Organizer extends User implements Serializable {

    /**
     * Creates a new entities.User with given name, password, and type.
     *
     * @param name     the valid name of this user, which help to identify.
     * @param password the valid password of this user.
     */
    public Organizer(String name, String password) {
        super(name, password, UserType.ORGANIZER);
    }
}
