package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.model.dto.request.ArtworkDTO;
import ra.service.ArtworkService;

import java.util.List;

@RestController
@RequestMapping("/api/gallery/artworks")
@RequiredArgsConstructor
public class ArtworkController {
    private final ArtworkService artworkService;

    @GetMapping
    public ResponseEntity<List<ArtworkDTO>> getArtworks() {
        return ResponseEntity.ok(artworkService.getAllArtworksForCurrentUser());
    }
}