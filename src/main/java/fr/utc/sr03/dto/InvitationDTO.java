package fr.utc.sr03.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class InvitationDTO {

    @NotNull(message = "owner's id is required")
    private Integer idUser;

    @NotNull(message = "user target's id is required")
    private Integer idUserTarget;

    @NotNull(message = "channel's id is required")
    private Integer idChannel;

    private LocalDateTime dateInvitation;
    private Boolean stateInitation;

    private String channelTitle;
    private String ownerFirstname;
    private String ownerLastname;
    private String userTargetFirstname;
    private String userTargetLastname;

    public InvitationDTO() {}

    public @NotNull(message = "owner's id is required") Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(@NotNull(message = "owner's id is required") Integer idUser) {
        this.idUser = idUser;
    }

    public @NotNull(message = "user target's id is required") Integer getIdUserTarget() {
        return idUserTarget;
    }

    public void setIdUserTarget(@NotNull(message = "user target's id is required") Integer idUserTarget) {
        this.idUserTarget = idUserTarget;
    }

    public @NotNull(message = "channel's id is required") Integer getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(@NotNull(message = "channel's id is required") Integer idChannel) {
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

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getOwnerFirstname() {
        return ownerFirstname;
    }

    public void setOwnerFirstname(String ownerFirstname) {
        this.ownerFirstname = ownerFirstname;
    }

    public String getOwnerLastname() {
        return ownerLastname;
    }

    public void setOwnerLastname(String ownerLastname) {
        this.ownerLastname = ownerLastname;
    }

    public String getUserTargetFirstname() {
        return userTargetFirstname;
    }

    public void setUserTargetFirstname(String userTargetFirstname) {
        this.userTargetFirstname = userTargetFirstname;
    }

    public String getUserTargetLastname() {
        return userTargetLastname;
    }

    public void setUserTargetLastname(String userTargetLastname) {
        this.userTargetLastname = userTargetLastname;
    }
}