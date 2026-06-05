package fr.utc.sr03.dto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ChannelDTO {
    @NotBlank(message = "Le titre du canal est obligatoire")
    private String title;

    private String description;

    @NotNull(message = "La date de début est obligatoire")
    @FutureOrPresent(message = "La date de début doit être dans le présent ou le futur")
    private LocalDateTime startChannel;

    @NotNull(message = "La date de fin est obligatoire")
    @Future(message = "La date de fin doit être dans le futur")
    private LocalDateTime endChannel;

    private LocalDateTime creationDate;

    @NotNull(message = "L'identifiant du propriétaire est obligatoire")
    private Integer ownerId;

    public ChannelDTO(){}

    public ChannelDTO(String title, String description, LocalDateTime startChannel, LocalDateTime endChannel, LocalDateTime creationDate, int ownerId) {
        this.title = title;
        this.description = description;
        this.startChannel = startChannel;
        this.endChannel = endChannel;
        this.creationDate = creationDate;
        this.ownerId = ownerId;
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

    public LocalDateTime getStartChannel() {
        return startChannel;
    }

    public void setStartChannel(LocalDateTime startChannel) {
        this.startChannel = startChannel;
    }

    public LocalDateTime getEndChannel() {
        return endChannel;
    }

    public void setEndChannel(LocalDateTime endChannel) {
        this.endChannel = endChannel;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}