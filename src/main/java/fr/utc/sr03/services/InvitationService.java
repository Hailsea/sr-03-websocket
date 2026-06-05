package fr.utc.sr03.services;

import fr.utc.sr03.model.Invitation;
import fr.utc.sr03.model.InvitationId;
import fr.utc.sr03.model.User;
import fr.utc.sr03.model.Channel;
import fr.utc.sr03.repository.InvitationRepository;
import fr.utc.sr03.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
    @Autowired
    private UserRepository userRepository;

    public Invitation getInvitationById(InvitationId id) {
        return invitationRepository.findById(id).orElse(null);
    }

    public List<Invitation> getInvitationByUserPaginated(int idUser, int page, int size) {
        Page<Invitation> pageResult = invitationRepository.findByUserTarget_Id(idUser, PageRequest.of(page, size));
        return pageResult.getContent();
    }

    public Invitation createInvitation(Invitation inv) {
        inv.setDateInvitation(LocalDateTime.now());
        inv.setStateInitation(false);

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

        if (invToUpdate.getDateInvitation() == null) {
            invToUpdate.setDateInvitation(LocalDateTime.now());
        }

        return invitationRepository.save(inv);
    }

    public Invitation deleteInvitation(InvitationId id) {
        Invitation invToDelete = getInvitationById(id);
        if (invToDelete != null) {
            invitationRepository.deleteById(id);
        }
        return invToDelete;
    }

}