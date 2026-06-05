package fr.utc.sr03.controller.api;

import fr.utc.sr03.dto.InvitationDTO;
import fr.utc.sr03.model.Invitation;
import fr.utc.sr03.model.InvitationId;
import fr.utc.sr03.services.InvitationService;
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
@RequestMapping(path = "/api/v1/invitations")
public class InvitationController {

    @Resource
    private InvitationService invitationService;

    @Resource
    private ModelMapper modelMapper;

    @GetMapping(value="/userTarget/{idUserTarget}/{start}/{end}")
    public ResponseEntity<List<InvitationDTO>> getInvitations(
            @PathVariable int idUserTarget,
            @PathVariable int start,
            @PathVariable int end) {

        List<Invitation> invitations = invitationService.getInvitationByUserPaginated(idUserTarget, start, end);

        if (invitations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<InvitationDTO> invitationsDto = new ArrayList<>();
        for (Invitation inv : invitations) {
            invitationsDto.add(mapToDTO(inv));
        }

        return ResponseEntity.ok(invitationsDto);
    }

    @GetMapping(value="/{idUser}/{idUserTarget}/{idChannel}")
    public ResponseEntity<InvitationDTO> getInvitation(
            @PathVariable int idUser,
            @PathVariable int idUserTarget,
            @PathVariable int idChannel) {

        InvitationId key = new InvitationId();
        key.setIdUser(idUser);
        key.setIdUserTarget(idUserTarget);
        key.setIdChannel(idChannel);

        Invitation inv = invitationService.getInvitationById(key);

        if (inv == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDTO(inv));
    }

    @PostMapping
    public ResponseEntity<InvitationDTO> createInvitation(@Valid @RequestBody InvitationDTO invitationDTO) {
        Invitation inv = mapToEntity(invitationDTO);
        System.out.println(inv);
        Invitation savedInvitation = invitationService.createInvitation(inv);
        if (savedInvitation != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(mapToDTO(savedInvitation));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value="/{idUser}/{idUserTarget}/{idChannel}")
    public ResponseEntity<InvitationDTO> updateInvitation(
            @PathVariable int idUser,
            @PathVariable int idUserTarget,
            @PathVariable int idChannel,
            @Valid @RequestBody InvitationDTO invitationDTO) {

        InvitationId key = new InvitationId();
        key.setIdUser(idUser);
        key.setIdUserTarget(idUserTarget);
        key.setIdChannel(idChannel);

        Invitation inv = invitationService.updateInvitation(key,  mapToEntity(invitationDTO));

        if (inv == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDTO(inv));
    }

    @DeleteMapping(value = "/{idUser}/{idUserTarget}/{idChannel}")
    public ResponseEntity<Void> deleteInvitation(
            @PathVariable int idUser,
            @PathVariable int idUserTarget,
            @PathVariable int idChannel) {

        InvitationId key = new InvitationId();
        key.setIdUser(idUser);
        key.setIdUserTarget(idUserTarget);
        key.setIdChannel(idChannel);

        Invitation deletedInvitation = invitationService.deleteInvitation(key);

        if (deletedInvitation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private Invitation mapToEntity(InvitationDTO dto) {
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

    private InvitationDTO mapToDTO(Invitation inv) {
        if (inv == null) return null;
        InvitationDTO dto = new InvitationDTO();

        dto.setIdUser(inv.getId().getIdUser());
        dto.setIdUserTarget(inv.getId().getIdUserTarget());
        dto.setIdChannel(inv.getId().getIdChannel());

        dto.setDateInvitation(inv.getDateInvitation());
        dto.setStateInitation(inv.getStateInitation());
        return dto;
    }
}