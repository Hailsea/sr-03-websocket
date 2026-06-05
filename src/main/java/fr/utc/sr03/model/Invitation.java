package fr.utc.sr03.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="invitation")
public class Invitation {

    @EmbeddedId
    private InvitationId id;

    @Column(name="date_invitation", nullable = false)
    private LocalDateTime dateInvitation;

    @Column(name="state_invitation")
    private Boolean stateInitation;

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name="id_user")
    private User owner;

    @ManyToOne
    @MapsId("idUserTarget")
    @JoinColumn(name="id_user_1")
    private User userTarget;

    @ManyToOne
    @MapsId("idChannel")
    @JoinColumn(name="id_channel")
    private Channel channel;

    public InvitationId getId() {
        return id;
    }

    public void setId(InvitationId id) {
        this.id = id;
    }

    public LocalDateTime getDateInvitation() {
        return dateInvitation;
    }

    public void setDateInvitation(LocalDateTime dateInvitation) {
        this.dateInvitation = dateInvitation;
    }

    public Boolean getStateInitation() {
        return stateInitation;
    }

    public void setStateInitation(Boolean stateInitation) {
        this.stateInitation = stateInitation;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getUserTarget() {
        return userTarget;
    }

    public void setUserTarget(User userTarget) {
        this.userTarget = userTarget;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    @Override
    public String toString() {
        return "Invitation{" +
                "id=" + id +
                ", dateInvitation=" + dateInvitation +
                ", stateInitation=" + stateInitation +
                ", owner=" + owner +
                ", userTarget=" + userTarget +
                ", channel=" + channel +
                '}';
    }

}