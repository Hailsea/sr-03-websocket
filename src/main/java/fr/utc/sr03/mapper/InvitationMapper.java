package fr.utc.sr03.mapper;

import fr.utc.sr03.dto.InvitationDTO;
import fr.utc.sr03.model.Invitation;
import fr.utc.sr03.model.InvitationId;
import org.springframework.stereotype.Component;

/**
 * Mapper dedie Invitation <-> InvitationDTO (cours REST §3.6 : le Mapper comme
 * brique d'IoC). Centralise la conversion, auparavant ecrite a la main dans le
 * controleur. Le mapping est manuel car l'entite utilise une cle composite
 * (InvitationId) + des relations (owner/channel) difficiles a aplatir automatiquement.
 */
@Component
public class InvitationMapper {

    public Invitation toEntity(InvitationDTO dto) {
        Invitation inv = new Invitation();

        InvitationId key = new InvitationId();
        key.setIdUser(dto.getIdUser());
        key.setIdUserTarget(dto.getIdUserTarget());
        key.setIdChannel(dto.getIdChannel());
        inv.setId(key);

        inv.setDateInvitation(dto.getDateInvitation());
        inv.setStateInitation(dto.getStateInitation());
        return inv;
    }

    public InvitationDTO toDTO(Invitation inv) {
        if (inv == null) {
            return null;
        }
        InvitationDTO dto = new InvitationDTO();

        dto.setIdUser(inv.getId().getIdUser());
        dto.setIdUserTarget(inv.getId().getIdUserTarget());
        dto.setIdChannel(inv.getId().getIdChannel());

        dto.setDateInvitation(inv.getDateInvitation());
        dto.setStateInitation(inv.getStateInitation());

        if (inv.getChannel() != null) {
            dto.setChannelTitle(inv.getChannel().getTitle());
        }
        if (inv.getOwner() != null) {
            dto.setOwnerFirstname(inv.getOwner().getFirstname());
            dto.setOwnerLastname(inv.getOwner().getLastname());
        }
        if (inv.getUserTarget() != null) {
            dto.setUserTargetFirstname(inv.getUserTarget().getFirstname());
            dto.setUserTargetLastname(inv.getUserTarget().getLastname());
        }
        return dto;
    }
}
