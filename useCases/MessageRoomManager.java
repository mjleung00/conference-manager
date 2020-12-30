package useCases;

import entities.MessageRoom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Represents a <code>MessageRoomManager</code> that is responsible for any interactions with <code>MessageRoom</code> entities.
 * Also contains an instance of <code>MessageRoom</code> that contains all speakers and organizers at a event.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class MessageRoomManager extends AbstractSerializableManager implements java.io.Serializable{

    /**
     * A <code>MessageRoom</code> with all speakers and organizers at a event.
     */
    private MessageRoom coopRoom;

    /**
     * ArrayList of all instances of <code>Chatroom</code> associated with activities happening.
     */
    private ArrayList<MessageRoom> eventChats;

    /**
     * ArrayList of all instances of private <code>Chatroom</code> (i.e. <code>Chatroom</code> with two participants).
     */
    private ArrayList<MessageRoom> privateChats;


    /**
     * Creates <code>ChatroomManager</code> with a blank list of conference messageRooms, blank list of private messageRooms, and an empty <code>MessageRoom</code> (i.e. no organizers or speakers yet).
     */
    public MessageRoomManager(){
        coopRoom = new MessageRoom();
        eventChats = new ArrayList<>();
        privateChats = new ArrayList<>();

    }

    /**
     * Creates <code>ChatroomManager</code> with a blank list of conference messageRooms, blank list of private messageRooms, and a <code>Chatroom</code> with the specified organizers and speakers as participants.
     * @param organizerSpeakers ArrayList of usernames of organizers and speakers to be put into the coopRoom <code>Chatroom</code>.
     */
    public MessageRoomManager(ArrayList<String> organizerSpeakers) {
        coopRoom = new MessageRoom(organizerSpeakers); //initialize instance of coopRoom with all Speakers and Organizers
        eventChats = new ArrayList<>();
        privateChats = new ArrayList<>();
    }

    /**
     * Creates a new <code>Chatroom</code> instance with the specified users as participants. If there are 2 participants
     * in this <code>Chatroom</code>, add it to <code>privateChats</code> of this <code>ChatroomManager</code>. Otherwise,
     * there are 3 or more chat participants and this <code>Chatroom</code> is added to <code>conferenceChats</code> of
     * this <code>ChatroomManager</code>.
     *
     * @param users ArrayList of usernames of users to be put into this <code>Chatroom</code> as participants.
     * @return the UUID of the newly created <code>Chatroom</code>.
     */
    public UUID createChatroom(ArrayList<String> users){
        // precondition: length(users) > 1

        MessageRoom newRoom= new MessageRoom(users); // create new room with given list of users
        if (users.size() == 2){
            privateChats.add(newRoom);  // add to privateChats list if there's only 2 users in the room (i.e. its private)
        }
        else{
            eventChats.add(newRoom); // add chatroom to conferenceChats !!!!!!!      This is NOT how conferenceChats should be used but this is until we can guarantee we won't try to create private group chats
        }
        return newRoom.getId();
    }

    /**
     * Gets the unique identifier for the <code>Chatroom</code> object, coopRoom, in this <code>ChatroomManager</code>.
     * @return The UUID corresponding to the coopRoom in this <code>ChatroomManager</code>.
     */
    public UUID getCoopId(){
        return coopRoom.getId();
    }

    /**
     * Adds the specified users to an existing <code>Chatroom</code> as participants.
     * @param users ArrayList of usernames of users to be added to the specified <code>Chatroom</code>.
     * @param chat UUID of the <code>Chatroom</code> we want to add the specified users to.
     */
    public void addUser(ArrayList<String> users, UUID chat) {
        ArrayList<MessageRoom> tmp = new ArrayList<>(eventChats);
        tmp.add(coopRoom);
        for (MessageRoom room : tmp){
            if (room.getId().equals(chat)){               // check the UUID to make sure we have the right entities.Chatroom
                for(String userID : users){
                    room.getUsersInvolved().add(userID);  // add users to the usersInvolved in this chat
                }
            }
        }
    }

    /**
     * Adds the specified users to an existing <code>Chatroom</code> as participants.
     * @param username  username of a single user to be added to the specified <code>Chatroom</code>.
     * @param chat UUID of the <code>Chatroom</code> we want to add the specified users to.
     */

    public void addUser(String username, UUID chat) {
        ArrayList<MessageRoom> tmp = new ArrayList<>(eventChats);
        tmp.add(coopRoom);
        for (MessageRoom room : tmp){
            if (room.getId().equals(chat)){               // check the UUID to make sure we have the right entities.Chatroom
                    room.getUsersInvolved().add(username);  // add users to the usersInvolved in this chat

            }
        }
    }

    /**
     * Removes the specified users from an existing <code>Chatroom</code>.
     *
     * @param users ArrayList of usernames of users to be removed from the specified <code>Chatroom</code>.
     * @param chat UUID of the <code>Chatroom</code> we want to remove the specified users from.
     */
    public void removeUser(ArrayList<String> users, UUID chat) {
        for (MessageRoom room : eventChats) {
            if (room.getId().equals(chat)) {               // check the UUID to make sure we have the right entities.Chatroom
                for (String userID : users) {
                    room.getUsersInvolved().remove(userID);  // remove users to the usersInvolved in this chat
                }
            }
        }
    }

    /**
     * Delete this private conversation
     * @param conversationID UUID of the <code>Chatroom</code> we want to delete
     */
    public void deletePrivateConversation(UUID conversationID){
        privateChats.removeIf(conversation -> conversation.getId().equals(conversationID));
    }

    /**
     * Sends a message to the specified conference chat, adding it to the <code>historicalChats</code> of the <code>Chatroom</code>.
     *
     * @param message String representing the message to be sent.
     * @param chat UUID of the <code>Chatroom</code> we want to send the message to.
     */
    public void sendMessage(String message, UUID chat){
        ArrayList<MessageRoom> groupChat = new ArrayList<>(eventChats);
        groupChat.add(coopRoom);
        for (MessageRoom room : groupChat) {
            if (room.getId().equals(chat)) {               // check the UUID to make sure we have the right entities.Chatroom
                room.getHistoricalChats().add(message);  // add message to historicalChats of room
                }
        }
    }

    /**
     * Sends a message to the specified private chat, adding it to the <code>historicalChats</code> of the <code>Chatroom</code>.
     *
     * @param message String representing the message to be sent.
     * @param chat UUID of the <code>Chatroom</code> we want to send the message to.
     */
    public void sendPrivateMessage(String message, UUID chat){
        for (MessageRoom room : privateChats) {
            if (room.getId().equals(chat)) {               // check the UUID to make sure we have the right entities.Chatroom
                room.getHistoricalChats().add(message);  // add message to historicalChats of room
            }
        }
    }

    /**
     * Delete this group chat
     * @param chatID UUID of the <code>Chatroom</code> we want to delete
     */
    public void deleteGroupChat(UUID chatID){
        eventChats.removeIf(groupRoom -> groupRoom.getId().equals(chatID));
    }

    /**
     * Searches for a <code>Chatroom</code> object with the specified UUID. If it exists, the <code>Chatroom</code>
     * object is returned; otherwise, <code>findChatroom</code> returns <code>null</code>.
     * @param chatID UUID of the <code>Chatroom</code> we want to search for.
     * @return The <code>Chatroom</code> object with the specified UUID if it exists; returns <code>null</code> otherwise.
     */
    private MessageRoom findChatroom(UUID chatID){
        MessageRoom returns = null;
        ArrayList<MessageRoom> allChats = new ArrayList<>(privateChats);
        allChats.addAll(eventChats);
        allChats.add(coopRoom);
        for (MessageRoom messageRooms : allChats){
            if (messageRooms.getId().equals(chatID)){
                returns = messageRooms;
            }
        }
        return returns;
    }

    /**
     * Gets the chat history of the specified <code>Chatroom</code>.
     * @param chatID UUID of the <code>Chatroom</code> we want to retrieve the chat history from.
     * @return An ArrayList containing messages sent in the specified <code>Chatroom</code>.
     */
    public ArrayList<String> getHistoricalChats(UUID chatID){
        MessageRoom targetedChat = findChatroom(chatID);
        return targetedChat.getHistoricalChats();
    }

    public void deleteMessage(UUID chatID, ArrayList<Integer> indexesDeletion){
        MessageRoom targetedConversation = findChatroom(chatID);
        indexesDeletion.sort(Collections.reverseOrder());
        ArrayList<String> history = targetedConversation.getHistoricalChats();
        for(int index: indexesDeletion){
            if(index >= history.size() || index < 0){
                continue;
            }
            history.remove(index);
        }
    }


}
