package entities;

import globallyAccessible.EventType;


import java.time.*;
import java.util.*;

/**
 * Event Class, abstract entity with getters and setters that all types of event entities requires.
 * All events have an ID, topic, max attendees restriction, event room location,
 * Also includes a list of attendees
 * Start and End time, with the duration that is updated everytime the start/end time changes
 * and finally a corresponding chatroom ID.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public abstract class Event implements java.io.Serializable {

    private ArrayList<String> attendeeList;    // do not set this list as final, since this makes adding impossible
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final UUID identity;
    private final UUID eventChat;
    private UUID eventRoomNum; //will adjust accordingly when they actually implements it.
    private String topic;
    private Integer maxNumAttendee;
    protected EventType type;


    /**
     * Creates <code>Event</code> with specified start time, end time, event chat id, event room number,
     * max attendees, and topic.
     * @param period LocalDateTime of start & end time in <code>Event</code>.
     * @param roomID UUID of assigned message room ID and event room ID <code>Event</code>.
     * @param topic the topic of the activity in <code>Event</code>.
     * @param MaxNum the maximum number of attendees for this event
     */
    public Event(LocalDateTime[] period, UUID[] roomID,
                 String topic, Integer MaxNum){
        this.attendeeList = new ArrayList<>();
        this.startTime = period[0];
        this.endTime = period[1];
        this.duration = Duration.between(startTime, endTime);
        this.eventChat = roomID[0];
        this.identity = UUID.randomUUID();
        this.eventRoomNum = roomID[1];
        this.topic = topic;
        this.maxNumAttendee = MaxNum;
    }

    /**
     * Determine whether this attendee should be added.
     * @param attendee the attendee to be added to this event
     * @return <CODE>true</CODE> if this attendee not in the Arraylist of attendees,
     * <CODE>false</CODE> otherwise
     */
    public boolean addAttendeesToList(String attendee){
        if(!this.attendeeList.contains(attendee)){
            this.attendeeList.add(attendee);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Determine whether this list of attendee should be added, and then adds is to the attendee list
     * @param attendeeList list of attendees to be added to this event
     * @return <CODE>true</CODE> if all attendee are not already in the Arraylist of attendees,
     * <CODE>false</CODE> otherwise
     */
    public boolean addAttendeesToList(ArrayList<String> attendeeList){
        boolean addedAll = true;
        for(String i: attendeeList){
            if(this.attendeeList.contains(i)) {
                this.attendeeList.add(i);
            }else{
                addedAll = false;
            }
        }
        return addedAll;
    }

    /**
     * Determine whether this attendee has been removed.
     * @return <CODE>true</CODE> if this attendee has been removed form the Arraylist of attendees.
     * <CODE>false</CODE> otherwise
     */
    public boolean removeAttendee(String attendee){return this.attendeeList.remove(attendee);}

    /**
     * Changes the topic of this event with the given new topic.
     * @param  topic the new topic of this event.
     */
    public void setTopic(String topic){this.topic = topic;}

    /**
     * Changes the event room number of this event with the given new event room number.
     * @param  eventRoomNum the new event room number of this event.
     */
    public void setRoom(UUID eventRoomNum){this.eventRoomNum = eventRoomNum;}

    /**
     * Changes the start time of this event with the given new start time.
     * @param  startTime the new start time of this event.
     */
    public boolean setStartTime(LocalDateTime startTime){
        if(startTime.isAfter(this.endTime)){
            return false;
        }else{
            this.startTime = startTime;
            changeDuration(Duration.between(this.startTime, this.endTime));
            return true;
        }
    }

    /**
     * Changes the end time of this event with the given new end time.
     * @param  endTime the new start time of this event.
     */
    public boolean setEndTime(LocalDateTime endTime){
        if(endTime.isBefore(this.startTime)){
            return false;
        }else{
            this.endTime = endTime;
            changeDuration(Duration.between(this.startTime, this.endTime));
            return true;
        }
    }

    /**
     * Changes the duration of this event with the given new duration.
     * @param  duration the new duration of this event.
     */
    public void setDuration(Duration duration){
        changeDuration(duration);
        this.endTime = this.startTime.plus(duration);
    }

    /**
     * Changes the Maximum Number of attendees with the given new integer.
     * @param  MaxNum the new Maximum number of attendees of this event.
     */
    public void setMaxNumAttendee(int MaxNum){
        this.maxNumAttendee = MaxNum;
    }

    /**
     * Changes the duration of this event.
     * @param duration the duration that will be given to this event
     */
    private void changeDuration(Duration duration){
        this.duration = duration;
    }

    /**
     * Gets an attendees list in this <code>Event</code>.
     * @return A list of strings corresponding to the attendees of <code>attendees</code> in this
     * <code>Activity</code>.
     */
    public ArrayList<String> getAttendeeList(){return this.attendeeList;}

    /**
     * Gets the start time for this <code>Event</code>.
     * @return The LocalDateTime corresponding to the start time of this <code>Event</code>.
     */
    public LocalDateTime getStartTime(){return this.startTime;}

    /**
     * Gets the end time for this <code>Event</code>.
     * @return The LocalDateTime corresponding to the end time of this <code>Event</code>.
     */
    public LocalDateTime getEndTime(){return this.endTime;}

    /**
     * Gets the duration for this <code>Event</code>.
     * @return The Duration corresponding to the duration of this <code>Event</code>.
     */
    public Duration getDuration(){return this.duration;}

    /**
     * Gets the unique identifier for this <code>Event</code>.
     * @return The UUID corresponding to this <code>Event</code>.
     */
    public UUID getChatID(){return this.eventChat;}

    /**
     * Gets the Maximum Number of attendees for this <code>Event</code>.
     * @return The integer corresponding to this <code>Event</code>.
     */
    public Integer getMaxNumAttendee(){
        return this.maxNumAttendee;
    }

    /**
     * Gets an identity in this <code>Event</code>.
     * @return A UUID corresponding to the identity in this <code>Event</code>.
     */
    public UUID getIdentity(){return this.identity;}

    /**
     * Gets a event room number in this <code>Event</code>.
     * @return A UUID corresponding to the number of the event room in this <code>Event</code>.
     */
    public UUID getEventRoomNum(){return this.eventRoomNum;}

    /**
     * Gets a topic in this <code>Event</code>.
     * @return A string corresponding to the topic in this <code>Event</code>.
     */
    public String getTopic(){return this.topic;}

    /**
     * Gets a event type in this <code>Event</code>.
     * @return A event type corresponding to the type if the event in this <code>Event</code>.
     */
    public EventType getEventType(){return this.type;}

    /**
     * This method show the info of event in string.
     * @return the string of the class event.
     */
    public String toString(){
        String description = ("Topic: [" + this.topic + "]\nSpeakers: [ " + speakerToString() + " ]");
        description += ("\nEvent Room: [ " + this.eventRoomNum + " ]");
        description += ("\nFrom: [ " + startTime + " ]  to  [" + endTime + " ]");
        description += ("\nEvent ID: [ " + this.identity + " ]");
        description += ("\nChat ID: [ " + this.eventChat + " ]\n ");
        description += speakerToString();
        return description;
    }

    /**
     * Outputs a string representing the speakers for this <code>Event</code> .
     * @return String representing this event's speakers.
     */
    public String speakerToString(){
        return "This event has no speakers.";
    }

}
