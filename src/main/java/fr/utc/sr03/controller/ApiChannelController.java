package fr.utc.sr03.controller;

import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.ServicesChannelRequest;
import fr.utc.sr03.services.ServicesRequest;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ApiChannelController {
    @Resource
    private ServicesChannelRequest serviceChannelRequest;
    @Resource
    private ServicesRequest serviceRequest;

    @PostMapping(value="/createChannel/{userId}")
    public void createChannel(@RequestBody Channel entity,
                              @PathVariable(name = "userId") int userId){
        User creator = serviceRequest.getOneUser(userId);

        Channel c = new Channel(entity.getTitle(), entity.getDate(), entity.getDuration(), entity.getDescription());
        c.setCreator(creator);
        c.getMembers().add(creator);

        serviceChannelRequest.addChannel(c);
    }

    @GetMapping(value="listChannel")
    public List<Channel> getChannel(){
        return serviceChannelRequest.getChannels();
    }

    @PostMapping(value = "/createUserForChannel/{channelId}")
    public String createUserForChannel(@RequestBody User entity,
                                       @PathVariable(name = "channelId") int channelId) {
        System.out.println("\nCreate a new User and assign to an existing Channel." + "\n");

        User user = new User(entity.getFirstName(), entity.getLastName(), entity.getEmail(),
                entity.getPassword(), entity.isAdmin());

        serviceRequest.addUser(user);
        System.out.println("\nSaved user :: " + user + "\n");

        Channel channel = serviceChannelRequest.getChannelByID(channelId);
        System.out.println("\nChannel details :: " + channel.toString() + "\n");

        channel.getMembers().add(user);
        serviceChannelRequest.updateChannel(channel);

        System.out.println("\nUser assigned to the Channel." + "\n");

        return "User saved and assigned to the Channel!!!";
    }
}
