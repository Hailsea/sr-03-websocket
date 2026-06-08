package fr.utc.sr03.controller.api;

import fr.utc.sr03.dto.InvitationDTO;
import fr.utc.sr03.dto.PageResponse;
import fr.utc.sr03.mapper.InvitationMapper;
import fr.utc.sr03.model.Invitation;
import fr.utc.sr03.model.InvitationId;
import fr.utc.sr03.services.InvitationService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    private InvitationMapper invitationMapper;

    @GetMapping
    public ResponseEntity<PageResponse<InvitationDTO>> getInvitations(
            @RequestParam int userTarget,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "4") int size) {

        Page<Invitation> invitations = invitationService.getInvitationByUserPaginated(userTarget, page, size);
        return ResponseEntity.ok(toPageResponse(invitations));
    }

    @GetMapping(value="/sent")
    public ResponseEntity<PageResponse<InvitationDTO>> getSentInvitations(
            @RequestParam int owner,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "4") int size) {

        Page<Invitation> invitations = invitationService.getSentInvitationsPaginated(owner, page, size);
        return ResponseEntity.ok(toPageResponse(invitations));
    }

    @GetMapping(value="/{idUser}/{idUserTarget}/{idChannel}")
    public ResponseEntity<InvitationDTO> getInvitation(
            @PathVariable int idUser,
            @PathVariable int idUserTarget,
            @PathVariable int idChannel) {

        Invitation inv = invitationService.getInvitationById(buildKey(idUser, idUserTarget, idChannel));
        if (inv == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invitationMapper.toDTO(inv));
    }

    @PostMapping
    public ResponseEntity<InvitationDTO> createInvitation(@Valid @RequestBody InvitationDTO invitationDTO) {
        Invitation savedInvitation = invitationService.createInvitation(invitationMapper.toEntity(invitationDTO));
        if (savedInvitation != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(invitationMapper.toDTO(savedInvitation));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value="/{idUser}/{idUserTarget}/{idChannel}/accept")
    public ResponseEntity<InvitationDTO> acceptInvitation(
            @PathVariable int idUser,
            @PathVariable int idUserTarget,
            @PathVariable int idChannel) {

        // passe l'etat a true et ajoute le userTarget au chanel
        Invitation inv = invitationService.acceptInvitation(buildKey(idUser, idUserTarget, idChannel), idUserTarget);
        if (inv == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invitationMapper.toDTO(inv));
    }

    @PutMapping(value="/{idUser}/{idUserTarget}/{idChannel}/refuse")
    public ResponseEntity<InvitationDTO> refuseInvitation(
            @PathVariable int idUser,
            @PathVariable int idUserTarget,
            @PathVariable int idChannel) {

        // passe l'etat a false mais garde l'invitation dans mes envois
        Invitation inv = invitationService.refuseInvitation(buildKey(idUser, idUserTarget, idChannel), idUserTarget);
        if (inv == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invitationMapper.toDTO(inv));
    }

    @PutMapping(value="/{idUser}/{idUserTarget}/{idChannel}")
    public ResponseEntity<InvitationDTO> updateInvitation(
            @PathVariable int idUser,
            @PathVariable int idUserTarget,
            @PathVariable int idChannel,
            @Valid @RequestBody InvitationDTO invitationDTO) {

        Invitation inv = invitationService.updateInvitation(buildKey(idUser, idUserTarget, idChannel), invitationMapper.toEntity(invitationDTO));
        if (inv == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invitationMapper.toDTO(inv));
    }

    @DeleteMapping(value = "/{idUser}/{idUserTarget}/{idChannel}")
    public ResponseEntity<Void> deleteInvitation(
            @PathVariable int idUser,
            @PathVariable int idUserTarget,
            @PathVariable int idChannel) {

        Invitation deletedInvitation = invitationService.deleteInvitation(buildKey(idUser, idUserTarget, idChannel));
        if (deletedInvitation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private InvitationId buildKey(int idUser, int idUserTarget, int idChannel) {
        InvitationId key = new InvitationId();
        key.setIdUser(idUser);
        key.setIdUserTarget(idUserTarget);
        key.setIdChannel(idChannel);
        return key;
    }

    private PageResponse<InvitationDTO> toPageResponse(Page<Invitation> invitations) {
        List<InvitationDTO> content = new ArrayList<>();
        for (Invitation inv : invitations.getContent()) {
            content.add(invitationMapper.toDTO(inv));
        }
        return new PageResponse<>(
                content,
                invitations.getNumber(),
                invitations.getSize(),
                invitations.getTotalElements(),
                invitations.getTotalPages(),
                invitations.isFirst(),
                invitations.isLast());
    }
}
