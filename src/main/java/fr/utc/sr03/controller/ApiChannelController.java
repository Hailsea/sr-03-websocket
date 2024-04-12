package fr.utc.sr03.controller;

import fr.utc.sr03.model.Channel;
import fr.utc.sr03.model.Invitation;
import fr.utc.sr03.model.User;
import fr.utc.sr03.services.ChannelService;
import fr.utc.sr03.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiChannelController {
    @Resource
    private ChannelService channelService;
    @Resource
    private UserService userService;


    //Channels
    @PostMapping(value="/api/channels/createChannel/{userId}")
    public void createChannel(@RequestBody Channel entity,
                              @PathVariable(name = "userId") int userId){
        //Retrieve the user from the DB
        User creator = userService.getUser(userId);
        //Create the channel setting the creator
        Channel c = new Channel(entity.getTitle(), entity.getDate(), entity.getDuration(), entity.getDescription());
        c.setCreator(creator);
        c.addMember(creator);
        //Persist the channel
        channelService.addChannel(c);
    }

    @GetMapping(value="api/channels/listChannels")
    public List<Channel> getChannels(){
        return channelService.getChannels();
    }


    //Membership
    @PostMapping(value = "api/channels/addMember")
    public String addMember(@PathVariable(name = "userId") int userId, @PathVariable(name = "channelId") int channelId){
        //Retrieve the channel
        Channel channel = channelService.getChannel(channelId);
        User user = userService.getUser(userId);
        channel.addMember(user);
        channelService.updateChannel(channel);
        return "Member added !";
    }

    @PostMapping(value = "api/channels/inviteUser")
    public String createInvitation(@RequestParam(name = "inviter") int inviter_id, @RequestParam(name = "invited") int invited_id, @RequestParam(name = "channel") int channel_id){
        //Admitting that the HTML form is able to give directly the invited, inviter and channel IDs
        //Here we retrieve the information from the request parameter
        User inviter = userService.getUser(inviter_id);
        User invited = userService.getUser(invited_id);
        Channel channel = channelService.getChannel(channel_id);
        Invitation invitation = new Invitation(inviter, invited, channel);
        channelService.addInvitation(invitation);
        return "Invitation created !";
    }


    //Invitations
    @GetMapping(value="api/channels/invitationsListForUser/{userId}")
    public List<Invitation> getInvitationsForUser(@PathVariable(name= "userId") int userId){
        User userInvited = userService.getUser(userId);
        return channelService.getInvitationsForUserInvited(userInvited);
    }






    @PostMapping(value = "api/channels/createUserForChannel/{channelId}")
    public String createUserForChannel(@RequestBody User entity,
                                       @PathVariable(name = "channelId") int channelId) {
        System.out.println("\nCreate a new User and assign to an existing Channel." + "\n");

        User user = new User(entity.getFirstName(), entity.getLastName(), entity.getEmail(),
                entity.getPassword(), entity.isAdmin());

        userService.addUser(user);
        System.out.println("\nSaved user :: " + user + "\n");

        Channel channel = channelService.getChannel(channelId);
        System.out.println("\nChannel details :: " + channel.toString() + "\n");

        channel.getMembers().add(user);
        channelService.updateChannel(channel);

        System.out.println("\nUser assigned to the Channel." + "\n");

        return "User saved and assigned to the Channel!!!";
    }
}
