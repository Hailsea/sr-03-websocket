package fr.utc.sr03.repository;

import fr.utc.sr03.model.Invitation;
import fr.utc.sr03.model.InvitationId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, InvitationId> {

    Page<Invitation> findByUserTarget_Id(Integer inviteeId, Pageable pageable);
}