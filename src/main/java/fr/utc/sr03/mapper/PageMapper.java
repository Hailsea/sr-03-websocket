package fr.utc.sr03.mapper;

import fr.utc.sr03.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * Mapper generique : transforme une page Spring (Page&lt;E&gt;) en PageResponse&lt;D&gt;
 * en appliquant une fonction de mapping element par element.
 * Evite de reecrire la conversion contenu + metadonnees dans chaque controleur.
 *
 * Exemple : pageMapper.toPageResponse(channels, c -&gt; modelMapper.map(c, ChannelDTO.class));
 */
@Component
public class PageMapper {

    public <E, D> PageResponse<D> toPageResponse(Page<E> page, Function<E, D> mapper) {
        List<D> content = page.getContent().stream()
                .map(mapper)
                .toList();

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }
}
