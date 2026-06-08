package fr.utc.sr03.services;

import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.User;
import fr.utc.sr03.repository.ChannelRepository;
import fr.utc.sr03.repository.InvitationRepository;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ChannelService {

    @Resource
    private ChannelRepository channelRepository;

    @Resource
    private InvitationRepository invitationRepository;

    @Resource
    private UserService userService;

    // TODO
    public Page<Channel> getChannelsByUserPaginated(int idUser, int page, int size) {
        User user = userService.getUserById(idUser);
        return user.getChannels();
    }

    public Page<Channel> getChannelsByOwnerPaginated(int idOwner, int page, int size) {
        User user = userService.getUserById(idOwner);
        return channelRepository.findByOwner(user, PageRequest.of(page, size));
    }

    public Page<Channel> getChannelsByInvitationPaginated(int idUser, int page, int size) {
        User user = userService.getUserById(idUser);
        return channelRepository.findByInvitation(user, PageRequest.of(page, size));
    }

    public Channel getChannelById(int id) {
        return channelRepository.findById(id).orElse(null);
    }

    public Channel getOwnerChannelById(Integer idChannel, Integer idOwner) {
        Channel chan = channelRepository.findById(idChannel).orElse(null);

        if (chan != null && chan.getOwner() != null && chan.getOwner().getId() == idOwner) {
            return chan;
        }
        return null;
    }

    public Channel createChannel(Channel channel, int ownerId) {
        if (channelRepository.existsByTitle(channel.getTitle())) {
            throw new IllegalArgumentException("This title is already use");
        }

        User owner = userService.getUserById(ownerId);
        channel.setOwner(owner);
        channel.setCreationDate(LocalDateTime.now());

        Channel savedChannel = channelRepository.save(channel);
        userService.joinChannel(owner, savedChannel);
        return savedChannel;
    }

    public Channel updateChannel(Integer id, Channel channelToUpdate, Integer ownerId) {
        Channel existing = channelRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (channelRepository.existsByTitleAndIdNot(channelToUpdate.getTitle(), id)) {
            throw new IllegalArgumentException("This title is already use");
        }

        channelToUpdate.setId(id);

        User owner = userService.getUserById(ownerId);
        channelToUpdate.setOwner(owner);

        if (channelToUpdate.getCreationDate() == null) {
            channelToUpdate.setCreationDate(existing.getCreationDate());
        }

        return channelRepository.save(channelToUpdate);
    }

    @Transactional
    public Channel deleteChannel(int id) {
        Channel channelToDelete = channelRepository.findById(id).orElse(null);
        if (channelToDelete != null) {
            invitationRepository.deleteByChannelId(id); // supprime les invitations
            channelRepository.deleteById(id);
        }
        return channelToDelete;
    }
}