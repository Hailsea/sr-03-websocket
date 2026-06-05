package fr.utc.sr03.controller.api;

import fr.utc.sr03.dto.ChannelDTO;
import fr.utc.sr03.model.Channel;
import fr.utc.sr03.services.ChannelService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/api/v1/channels")
public class ChannelController {

    @Resource
    private ChannelService channelService;

    @Resource
    private ModelMapper modelMapper;

    @GetMapping(value="/user/{idUser}/{start}/{end}")
    public ResponseEntity<List<ChannelDTO>> getChannels(
            @PathVariable int idUser,
            @PathVariable int start,
            @PathVariable int end,
            @RequestParam(value = "state", required = false, defaultValue = "all") String state) {

        List<Channel> channels;
        switch (state){
            case "owner":
                channels = channelService.getChannelsByOwnerPaginated(idUser, start, end);
                break;
            case "usertarget":
                channels = channelService.getChannelsByInvitationPaginated(idUser, start, end);
                break;
            default:
                channels = channelService.getChannelsByUserPaginated(idUser, start, end);
                break;
        }

        if (channels.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ChannelDTO> channelsDto = new ArrayList<>();
        for (Channel channel : channels) {
            ChannelDTO dto = modelMapper.map(channel, ChannelDTO.class);
            channelsDto.add(dto);
        }

        return ResponseEntity.ok(channelsDto);
    }

    /*@GetMapping(value = "/{idUser}/{id}")
    public ResponseEntity<ChannelDTO> getChannelById(@PathVariable int idUser, @PathVariable int id) {
        Channel channel = channelService.getChannelById(id, idUser);

        if (channel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelMapper.map(channel, ChannelDTO.class));
    }*/

    @PostMapping
    public ResponseEntity<?> createChannel(@Valid @RequestBody ChannelDTO channelDTO) {
        try {
            Channel chan = modelMapper.map(channelDTO, Channel.class);
            chan.setId(null);
            Channel savedChan = channelService.createChannel(chan, channelDTO.getOwnerId());
            ChannelDTO savedDTO = modelMapper.map(savedChan, ChannelDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<ChannelDTO> updateChannel(@PathVariable int id,@Valid @RequestBody ChannelDTO channelDTO) {
        Channel updatedChannel = channelService.updateChannel(id, modelMapper.map(channelDTO, Channel.class), channelDTO.getOwnerId());

        if (updatedChannel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelMapper.map(updatedChannel, ChannelDTO.class));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable int id) {
        Channel deletedChannel = channelService.deleteChannel(id);

        if (deletedChannel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}