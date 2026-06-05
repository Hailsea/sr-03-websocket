package fr.utc.sr03.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class InvitationDTO {

    @NotNull(message = "L'identifiant de l'inviteur est obligatoire")
    private int idUser;

    @NotNull(message = "L'identifiant de l'utilisateur invité est obligatoire")
    private int idUserTarget;

    @NotNull(message = "L'identifiant du canal est obligatoire")
    private int idChannel;

    private LocalDateTime dateInvitation;
    private Boolean stateInitation;

    public InvitationDTO() {}

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUserTarget() {
        return idUserTarget;
    }

    public void setIdUserTarget(int idUserTarget) {
        this.idUserTarget = idUserTarget;
    }

    public int getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(int idChannel) {
        this.idChannel = idChannel;
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
}