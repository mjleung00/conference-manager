package useCases;

import entities.EventRoom;
import eventRoomRequirementBuilder.*;

import java.time.LocalDateTime;
import java.util.*;


/**
 * Represents a <code>RoomManager</code> that is responsible for any interactions with <code>Room</code> entities.
 * Contains instance of entities.Room: room, a room for some activities;
 *
 * Own constructor
 * CheckRoomFullness: Check the Fullness for the room;
 * BookRoom: Add an activity in the room schedule given a time period;
 * bookingAvailable: Check if there are activities using this room at a given time;
 * CancelRoomActivity: Remove an activity in the room schedule;
 *
 */

public class EventRoomManager extends AbstractSerializableManager implements java.io.Serializable {


    /**
     * a Arraylist of <code>EventRoom</code>
     */
    private ArrayList<EventRoom> eventRooms;

    private ItemFactory itemFactory;

    /**
     * Creates <code>EventRoomManager</code> with a blank list of event rooms.
     */
    public EventRoomManager(){
        eventRooms = new ArrayList<>();
    }

    /**
     * Creates a <code>EventRoom</code> and add it to the list of event rooms of the <code>EventRoomManager</code>
     * @param capacity is the capacity for the newly constructed <code>EventRoom</code>.
     * @return the id of the newly constructed <code>EventRoom</code>.
     */
    public UUID addRoom(int capacity, List<Integer> roomItems){
        EventRoomBuilder builder = new EventRoomBuilder(capacity);
        builder.buildMicrophone(roomItems.get(0));
        builder.buildProjector(roomItems.get(1));
        builder.buildPartyAudio(roomItems.get(2));
        EventRoom newEventRoom = builder.getNewRoom();
        eventRooms.add(newEventRoom);
        return newEventRoom.getId();
    }


    /**
     * Check whether a <code>Room</code> have the capacity to hold a given <code>Activity</code>
     * @param UserNum is the number of <code>User</code> attending the activity.
     * @param roomID is the id of the room we want to check.
     * @return True if the room have the ability to hold this activity.
     */
    public boolean CheckRoomFullness(Integer UserNum, UUID roomID){
        EventRoom eventRoom = findRoom(roomID);
        assert eventRoom != null;
        return eventRoom.getCapacity() >= UserNum;
    }

    /**
     * find the <code>Room</code> by given room id.
     * @param roomID is the id of the room we are searching for.
     * @return a room or nothing if it is not in the list of rooms of our <code>RoomManager</code>
     */
    public EventRoom findRoom(UUID roomID){
        for (EventRoom eventRoom : eventRooms){
            if (eventRoom.getId().equals(roomID)){
                return eventRoom;
            }
        }
        return null;
    }


    /**
     * book a <code>Room</code> by given time, room, and <code>Activity</code>.
     * @param time is the time we want to book.
     * @param activityID is the id of the activity.
     * @param roomID is the id of the room.
     */
    public void BookRoom(LocalDateTime[] time, UUID activityID, UUID roomID){
        EventRoom eventRoom = findRoom(roomID);
        assert eventRoom != null;
        if (!eventRoom.getSchedule().containsKey(time)){
            eventRoom.getSchedule().put(time, activityID);
        }
    }

    /**
     * Returns the capacity of the entities.Room with the given ID
     * @param roomId of the <code>EventRoom</code>
     * @return the capacity of the entities.Room
     */
    public int getRoomCapacity(UUID roomId){
        return findRoom(roomId).getCapacity();
    }

    /**
     * Remove an event in the entities.Room schedule
     * @param time of the <code>Event</code>
     * @param eventID id of the <code>Activity</code>
     * @param roomID of the <code>EventRoom</code>
     */
    public void CancelRoomEvent(LocalDateTime[] time, UUID eventID, UUID roomID){
        EventRoom eventRoom = findRoom(roomID);
        assert eventRoom != null;
        Set<LocalDateTime[]> schedule = eventRoom.getSchedule().keySet();
        LocalDateTime[] deletePeriod = null;
        for (LocalDateTime[] period : schedule){
            if (period[0].equals(time[0]) && period[1].equals(time[1])){
                deletePeriod = period;
            }
        }
        eventRoom.getSchedule().remove(deletePeriod, eventID);
    }

    /**
     * get list of available <code>EventRoom</code> in given time period.
     * @param targetPeriod is the time period we want to check.
     * @return a list of <>String[]</> of available room ids and info.
     */
    public ArrayList<String[]> bookingAvailable(LocalDateTime[] targetPeriod){
        ArrayList<String[]> possibleRooms = new ArrayList<>();
        for (EventRoom eventRoom : eventRooms){
            if (checkSingleRoomOK(targetPeriod, eventRoom)){
                possibleRooms.add(new String[]{eventRoom.getId().toString(), eventRoom.toString()});
            }
        }
        return possibleRooms;
    }

    /**
     * check if a specific <code>Room</code> is available in a time period.
     * @param targetPeriod is the target time period.
     * @param eventRoom is the target room
     * @return True if the room is not used by any activity in this time period.
     */
    private boolean checkSingleRoomOK(LocalDateTime[] targetPeriod, EventRoom eventRoom){
        HashMap<LocalDateTime[], UUID> roomBooked = eventRoom.getSchedule();
        for(LocalDateTime[] interval: roomBooked.keySet()){
            LocalDateTime start = interval[0];
            LocalDateTime end = interval[1];
            if (start.isBefore(targetPeriod[0]) && end.isAfter(targetPeriod[1])){
                return false;
            }
            if (start.isAfter(targetPeriod[0]) && start.isBefore(targetPeriod[1])){
                return false;
            }
            if (end.isAfter(targetPeriod[0]) && end.isBefore(targetPeriod[1])){
                return false;
            }
            if(start.isEqual(targetPeriod[0]) && end.isEqual(targetPeriod[1])){
                return false;
            }
        }
        return true;
    }

    /**
     * Get <code>EventRoomItems</code> of a event room by given room id.
     * @param id the event room id of the target room.
     * @return an instance of <code>EventRoomItems</code>
     */
    public EventRoomItems getEventRoomItems(UUID id){
        return findRoom(id).getRoomItems();
    }

    /**
     * Check whether this room meets all the technical requirement of the event.
     * @param roomID is <>UUID</> of this room.
     * @param itemChoice is the event's technical requirement for possible rooms.
     * @return True if this room meets the technical requirement.
     */
    public boolean checkEventRoomItems(UUID roomID, Boolean[] itemChoice){
        return findRoom(roomID).checkItemRequirement(itemChoice);
    }


}
