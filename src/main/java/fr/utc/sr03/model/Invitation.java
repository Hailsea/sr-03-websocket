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
    private User inviter;

    @ManyToOne
    @MapsId("idUserTarget")
    @JoinColumn(name="id_user_1")
    private User invitee;

    @ManyToOne
    @MapsId("idChannel")
    @JoinColumn(name="id_channel")
    private Channel channel;
}