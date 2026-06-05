package fr.utc.sr03.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="channel_table")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_channel")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User owner;

    @Column(name="title_channel", length = 50, nullable = false, unique = true)
    private String title;

    @Column(name="description_channel", columnDefinition = "TEXT")
    private String description;

    @Column(name="start_channel", nullable = false)
    private LocalDateTime startChannel;

    @Column(name="end_channel", nullable = false)
    private LocalDateTime endChannel;

    @Column(name="creation_date_channel", nullable = false)
    private LocalDateTime creationDate;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}