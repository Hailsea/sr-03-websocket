package fr.utc.sr03.services;

import fr.utc.sr03.exception.ForbiddenException;
import fr.utc.sr03.model.Invitation;
import fr.utc.sr03.model.InvitationId;
import fr.utc.sr03.model.User;
import fr.utc.sr03.model.Channel;
import fr.utc.sr03.repository.InvitationRepository;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvitationService {

    @Resource
    private InvitationRepository invitationRepository;

    @Resource
    private UserService userService;

    @Resource
    private ChannelService channelService;

    public Invitation getInvitationById(InvitationId id) {
        return invitationRepository.findById(id).orElse(null);
    }

    public Page<Invitation> getInvitationByUserPaginated(int idUser, int page, int size) {
        return invitationRepository.findByUserTarget_Id(idUser, PageRequest.of(page, size));
    }

    public Page<Invitation> getSentInvitationsPaginated(int ownerId, int page, int size) {
        return invitationRepository.findByOwner_Id(ownerId, PageRequest.of(page, size));
    }

    public Invitation createInvitation(Invitation inv) {
        inv.setDateInvitation(LocalDateTime.now());
        inv.setStateInitation(null); // en attente

        User owner = userService.getUserById(inv.getId().getIdUser());
        User userTarget = userService.getUserById(inv.getId().getIdUserTarget());
        Channel channel = channelService.getOwnerChannelById(inv.getId().getIdChannel(), inv.getId().getIdUser());

        if (channel == null || owner == null || userTarget == null) {
            return null;
        }

        inv.setOwner(owner);
        inv.setUserTarget(userTarget);
        inv.setChannel(channel);

        return invitationRepository.save(inv);
    }

    public Invitation updateInvitation(InvitationId id, Invitation invToUpdate) {
        Invitation inv = invitationRepository.findById(id).orElse(null);

        if (inv == null) {
            return null;
        }

        if (invToUpdate.getStateInitation() != null) {
            inv.setStateInitation(invToUpdate.getStateInitation());
        }

        return invitationRepository.save(inv);
    }

    public Invitation deleteInvitation(InvitationId id) {
        Invitation invToDelete = getInvitationById(id);
        if (invToDelete != null) {
            if (Boolean.TRUE.equals(invToDelete.getStateInitation())
                    && invToDelete.getUserTarget() != null
                    && invToDelete.getChannel() != null) {
                userService.leaveChannel(invToDelete.getUserTarget(), invToDelete.getChannel().getId());
            }
            invitationRepository.deleteById(id);
        }
        return invToDelete;
    }

    public Invitation acceptInvitation(InvitationId id, int currentUserId) {
        Invitation inv = invitationRepository.findById(id).orElse(null);
        if (inv == null) {
            return null;
        }

        if (inv.getId().getIdUserTarget() != currentUserId) {
            throw new ForbiddenException("Vous ne pouvez pas accepter cette invitation");
        }

        inv.setStateInitation(true);
        Invitation savedInv = invitationRepository.save(inv);

        userService.joinChannel(inv.getUserTarget(), inv.getChannel());
        return savedInv;
    }

    public Invitation refuseInvitation(InvitationId id, int currentUserId) {
        Invitation inv = invitationRepository.findById(id).orElse(null);
        if (inv == null) {
            return null;
        }

        if (inv.getId().getIdUserTarget() != currentUserId) {
            throw new ForbiddenException("Vous ne pouvez pas refuser cette invitation");
        }

        inv.setStateInitation(false);
        return invitationRepository.save(inv);
    }

    public boolean leaveChannel(int channelId, int userId) {
        Channel channel = channelService.getChannelById(channelId);
        if (channel == null) {
            return false;
        }
        if (channel.getOwner() != null && channel.getOwner().getId() == userId) {
            throw new ForbiddenException("Le propriétaire ne peut pas partir");
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            return false;
        }

        userService.leaveChannel(user, channelId);

        List<Invitation> invitations = invitationRepository.findByUserTarget_IdAndChannel_Id(userId, channelId);
        invitationRepository.deleteAll(invitations);
        return true;
    }
}
