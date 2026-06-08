package fr.utc.sr03.controller.api;

import fr.utc.sr03.dto.ChannelDTO;
import fr.utc.sr03.dto.PageResponse;
import fr.utc.sr03.model.Channel;
import fr.utc.sr03.services.ChannelService;
import fr.utc.sr03.services.InvitationService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
    private InvitationService invitationService;

    @Resource
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<PageResponse<ChannelDTO>> getChannels(
            @RequestParam int userId,
            @RequestParam(value = "state", defaultValue = "all") String state,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "4") int size) {

        Page<Channel> channels = switch (state) {
            case "owner" -> channelService.getChannelsByOwnerPaginated(userId, page, size);
            case "usertarget" -> channelService.getChannelsByInvitationPaginated(userId, page, size);
            default -> channelService.getChannelsByUserPaginated(userId, page, size);
        };

        List<ChannelDTO> channelsDto = new ArrayList<>();
        for (Channel chan : channels.getContent()) {
            channelsDto.add(modelMapper.map(chan, ChannelDTO.class));
        }
        // TODO
        PageResponse<ChannelDTO> body = new PageResponse<>(
                channelsDto,
                channels.getNumber(),
                channels.getSize(),
                channels.getTotalElements(),
                channels.getTotalPages(),
                channels.isFirst(),
                channels.isLast());

        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<ChannelDTO> createChannel(@Valid @RequestBody ChannelDTO channelDTO) {
        Channel chan = modelMapper.map(channelDTO, Channel.class);

        chan.setId(null);
        Channel savedChan = channelService.createChannel(chan, channelDTO.getOwnerId());

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(savedChan, ChannelDTO.class));
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<ChannelDTO> updateChannel(@PathVariable int id,@Valid @RequestBody ChannelDTO channelDTO) {
        Channel updatedChan = channelService.updateChannel(id, modelMapper.map(channelDTO, Channel.class), channelDTO.getOwnerId());
        if (updatedChan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modelMapper.map(updatedChan, ChannelDTO.class));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable int id) {
        Channel deletedChan = channelService.deleteChannel(id);
        if (deletedChan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value="/{channelId}/members/{userId}")
    public ResponseEntity<Void> leaveChannel(@PathVariable int channelId, @PathVariable int userId) {
        boolean left = invitationService.leaveChannel(channelId, userId);
        if (!left) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}