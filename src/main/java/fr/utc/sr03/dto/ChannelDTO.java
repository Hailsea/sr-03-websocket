package fr.utc.sr03.dto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ChannelDTO {

    private Integer id;

    @NotBlank(message = "Channel title is required")
    private String title;

    private String description;

    @NotNull(message = "begin date is required")
    @FutureOrPresent(message = "begin date has to be in present or future")
    private LocalDateTime startChannel;

    @NotNull(message = "end date is required ")
    @Future(message = "end date has to be in future")
    private LocalDateTime endChannel;

    private LocalDateTime creationDate;

    @NotNull(message = "owner's id is required")
    private Integer ownerId;

    public ChannelDTO(){}

    public ChannelDTO(Integer id, String title, String description, LocalDateTime startChannel, LocalDateTime endChannel, LocalDateTime creationDate, Integer ownerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startChannel = startChannel;
        this.endChannel = endChannel;
        this.creationDate = creationDate;
        this.ownerId = ownerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "Channel title is required") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Channel title is required") String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotNull(message = "begin date is required") @FutureOrPresent(message = "begin date has to be in present or future") LocalDateTime getStartChannel() {
        return startChannel;
    }

    public void setStartChannel(@NotNull(message = "begin date is required") @FutureOrPresent(message = "begin date has to be in present or future") LocalDateTime startChannel) {
        this.startChannel = startChannel;
    }

    public @NotNull(message = "end date is required ") @Future(message = "end date has to be in future") LocalDateTime getEndChannel() {
        return endChannel;
    }

    public void setEndChannel(@NotNull(message = "end date is required ") @Future(message = "end date has to be in future") LocalDateTime endChannel) {
        this.endChannel = endChannel;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public @NotNull(message = "owner's id is required") Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(@NotNull(message = "owner's id is required") Integer ownerId) {
        this.ownerId = ownerId;
    }
}