package fr.utc.sr03.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "channels")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="channel_id")
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(
            name = "membership",
            joinColumns = { @JoinColumn(name = "channel_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> members = new HashSet<>();
    
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

    public Channel() {

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

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    // Manage members of the channel, addMember and removeMember

    // Boiler plate
    @Override
    public String toString() {
        return "Channel id : " + id + ", title: " + title + " activated: " + activated + " Date :" + date + " Duration: " + duration;
    }
}
