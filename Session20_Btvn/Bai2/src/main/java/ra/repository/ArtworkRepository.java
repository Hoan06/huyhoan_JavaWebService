package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Artwork;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> { }