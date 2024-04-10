package fr.utc.sr03.model;

import jakarta.persistence.*;

@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="invitation_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "inviter_id", referencedColumnName = "user_id", nullable = false)
    private User inviter;

    @ManyToOne
    @JoinColumn(name = "invited_id", referencedColumnName = "user_id", nullable = false)
    private User invited;

    @ManyToOne
    @JoinColumn(name = "channel_id", referencedColumnName = "channel_id", nullable = false)
    private Channel channel;

    // Constructors, getters, setters


    public Invitation(int id, User inviter, User invited, Channel channel) {
        this.id = id;
        this.inviter = inviter;
        this.invited = invited;
        this.channel = channel;
    }

    public Invitation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getInviter() {
        return inviter;
    }

    public void setInviter(User inviter) {
        this.inviter = inviter;
    }

    public User getInvited() {
        return invited;
    }

    public void setInvited(User invited) {
        this.invited = invited;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }


    //Equals & Hash

    //Methods
}