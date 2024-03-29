package fr.utc.sr03.model;

import java.util.ArrayList;
import java.util.Date;

public class Channel {
    private int id;
    private boolean activated;
    private String title, description;
    private Date date, duration;
    private User creator;
    private ArrayList<User> members;
    private static int autoIncrement = 0;
    public static int getAutoIncrement() {
        return autoIncrement;
    }
    
    // Constructors
    public Channel(String newTitle, Date newdate, Date newDuration) {
        autoIncrement ++;
        this.title = newTitle;
        this.date = newdate ;
        this.duration = newDuration ;
        this.activated = true ;
        this.id = autoIncrement ;
    }

    public Channel(String newTitle, Date newdate, Date newDuration, String newDescription) {
        autoIncrement ++;
        this.title = newTitle;
        this.date = newdate ;
        this.duration = newDuration ;
        this.activated = true ;
        this.description = newDescription;
        this.id = autoIncrement ;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public static void setAutoIncrement(int autoIncrement) {
        Channel.autoIncrement = autoIncrement;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    // Manage members of the channel, addMember and removeMember

    // Boiler plate
    @Override
    public String toString() {
        return "Channel id : " + id + ", title: " + title + " activated: " + activated + " Date :" + date + " Duration: " + duration;
    }
}
