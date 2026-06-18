package fr.utc.sr03.repository;

import fr.utc.sr03.model.Invitation;
import fr.utc.sr03.model.InvitationId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, InvitationId> {

    // Invitations recues par un utilisateur
    @Query(value = "SELECT i FROM Invitation i WHERE i.id.idUserTarget = ?1",
           countQuery = "SELECT COUNT(i.id.idUserTarget) FROM Invitation i WHERE i.id.idUserTarget = ?1")
    Page<Invitation> findByUserTarget_Id(Integer inviteeId, Pageable pageable);

    // Invitations envoyees par un utilisateur
    @Query(value = "SELECT i FROM Invitation i WHERE i.id.idUser = ?1",
           countQuery = "SELECT COUNT(i.id.idUser) FROM Invitation i WHERE i.id.idUser = ?1")
    Page<Invitation> findByOwner_Id(Integer ownerId, Pageable pageable);

    // Invitations d'un utilisateur cible pour un salon donne
    @Query("SELECT i FROM Invitation i WHERE i.id.idUserTarget = ?1 AND i.id.idChannel = ?2")
    List<Invitation> findByUserTarget_IdAndChannel_Id(Integer inviteeId, Integer channelId);

    // Supprime toutes les invitations d'un salon
    @Modifying
    @Query("DELETE FROM Invitation i WHERE i.id.idChannel = ?1")
    void deleteByChannelId(Integer channelId);
}
