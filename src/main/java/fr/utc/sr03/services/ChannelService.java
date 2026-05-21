package fr.utc.sr03.services;

import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.User;
import fr.utc.sr03.repository.ChannelRepository;
import fr.utc.sr03.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChannelService {

    @Resource
    private ChannelRepository channelRepository;

    @Resource
    private UserRepository userRepository;

    public Channel createChannel(Channel channel) {
        if (channel.getCreationDate() == null) {
            channel.setCreationDate(LocalDateTime.now());
        }

        if (channel.getOwner() != null && channel.getOwner().getId() > 0) {
            User fullUser = userRepository.findById(channel.getOwner().getId()).orElse(null);
            channel.setOwner(fullUser);
        }

        return channelRepository.save(channel);
    }

    public Channel updateChannel(Channel channel) {
        if (channel.getCreationDate() == null) {
            channel.setCreationDate(LocalDateTime.now());
        }

        if (channelRepository.existsById(channel.getId())) {
            return channelRepository.save(channel);
        }
        return null;
    }

    public Channel deleteChannel(int id) {
        Channel channelToDelete = channelRepository.findById(id).orElse(null);
        if (channelToDelete != null) {
            channelRepository.deleteById(id);
        }
        return channelToDelete;
    }
}