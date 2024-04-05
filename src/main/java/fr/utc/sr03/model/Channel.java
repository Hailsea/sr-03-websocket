package fr.utc.sr03.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;

@Entity
@Table(name = "channels")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "activated")
    private boolean activated;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date date;

    @Column(name = "duration")
    private Date duration;

    @OneToOne(mappedBy = "id") //To verify
    private User creator;

    @OneToMany(mappedBy = "id")
    private ArrayList<User> members = new ArrayList<>();
    
    // Constructors
    public Channel(String newTitle, Date newdate, Date newDuration) {
        this.title = newTitle;
        this.date = newdate ;
        this.duration = newDuration ;
        this.activated = true ;
    }

    public Channel(String newTitle, Date newdate, Date newDuration, String newDescription) {
        this.title = newTitle;
        this.date = newdate ;
        this.duration = newDuration ;
        this.activated = true ;
        this.description = newDescription;
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

    public void setId(int id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Hashtable<Integer,User> getMembers() {
        return members;
    }

    public void setMembers(Hashtable<Integer, User> members) {
        this.members = members;
    }

    // Manage members of the channel, addMember and removeMember

    // Boiler plate
    @Override
    public String toString() {
        return "Channel id : " + id + ", title: " + title + " activated: " + activated + " Date :" + date + " Duration: " + duration;
    }
}
