package fr.utc.sr03.services;

import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.User;
import fr.utc.sr03.repository.ChannelRepository;
import fr.utc.sr03.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChannelService {

    @Resource
    private ChannelRepository channelRepository;

    @Resource
    private UserService userService;

    public List<Channel> getChannelsByUserPaginated(int idUser, int page, int size) {
        User user = userService.getUserById(idUser);
        Page<Channel> pageResult = channelRepository.findByUserTarget(user, PageRequest.of(page, size));
        return pageResult.getContent();
    }

    public List<Channel> getChannelsByOwnerPaginated(int idOwner, int page, int size) {
        User user = userService.getUserById(idOwner);
        Page<Channel> pageResult = channelRepository.findByOwner(user, PageRequest.of(page, size));
        return pageResult.getContent();
    }

    public List<Channel> getChannelsByInvitationPaginated(int idUser, int page, int size) {
        User user = userService.getUserById(idUser);
        Page<Channel> pageResult = channelRepository.findByInvitation(user, PageRequest.of(page, size));
        return pageResult.getContent();
    }

    public Channel getOwnerChannelById(Integer idChannel, Integer idOwner) {
        Channel channel = channelRepository.findById(idChannel).orElse(null);

        if (channel != null && channel.getOwner() != null && channel.getOwner().getId() == idOwner) {
            return channel;
        }
        return null;
    }

    public Channel createChannel(Channel channel, int ownerId) {
        if (channelRepository.existsByTitle(channel.getTitle())) {
            throw new IllegalArgumentException("Channel title already use");
        }

        User owner = userService.getUserById(ownerId);
        channel.setOwner(owner);
        channel.setCreationDate(LocalDateTime.now());
        return channelRepository.save(channel);
    }

    public Channel updateChannel(Integer id, Channel channelToUpdate, Integer ownerId) {
        if (!channelRepository.existsById(id)) {
            return null;
        }
        channelToUpdate.setId(id);

        User owner = userService.getUserById(ownerId);
        channelToUpdate.setOwner(owner);

        if (channelToUpdate.getCreationDate() == null) {
            channelToUpdate.setCreationDate(LocalDateTime.now());
        }

        return channelRepository.save(channelToUpdate);
    }

    public Channel deleteChannel(int id) {
        Channel channelToDelete = channelRepository.findById(id).orElse(null);
        if (channelToDelete != null) {
            channelRepository.deleteById(id);
        }
        return channelToDelete;
    }
}