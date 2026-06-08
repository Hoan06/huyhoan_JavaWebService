package ra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ra.model.dto.request.ArtworkDTO;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private List<ArtworkDTO> getMockArtworks() {
        return List.of(
                new ArtworkDTO(1L, "Tranh Phong Cảnh Đã Xuất Bản", "Mô tả 1", true, 102L),
                new ArtworkDTO(2L, "Tranh Trừu Tượng Đã Xuất Bản", "Mô tả 2", true, 103L),
                new ArtworkDTO(3L, "Tranh Sơn Dầu Chưa Xuất Bản của Artist", "Mô tả 3", false, 2L),
                new ArtworkDTO(4L, "Tranh Điêu Khắc Chưa Xuất Bản của Người Khác", "Mô tả 4", false, 999L)
        );
    }

    public List<ArtworkDTO> getAllArtworksForCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isArtist = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ARTIST"));

        Long currentUserId = "artist_user".equals(username) ? 2L : 1L;

        List<ArtworkDTO> allArtworks = getMockArtworks();

        return allArtworks.stream()
                .filter(artwork -> {
                    if (isAdmin) {
                        return true;
                    }
                    if (isArtist) {
                        return artwork.isPublished() || artwork.getOwnerId().equals(currentUserId);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}