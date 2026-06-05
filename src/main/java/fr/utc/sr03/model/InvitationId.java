package fr.utc.sr03.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InvitationId implements Serializable {

    @Column(name="id_user")
    private int idUser;

    @Column(name="id_user_1")
    private int idUserTarget;

    @Column(name="id_channel")
    private int idChannel;

    public InvitationId() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvitationId that = (InvitationId) o;
        return idUser == that.idUser && idUserTarget == that.idUserTarget && idChannel == that.idChannel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idUserTarget, idChannel);
    }

    @Override
    public String toString() {
        return "InvitationId{" +
                "idUser=" + idUser +
                ", idUserTarget=" + idUserTarget +
                ", idChannel=" + idChannel +
                '}';
    }
}