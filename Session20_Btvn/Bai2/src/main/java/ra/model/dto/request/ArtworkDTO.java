package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data @AllArgsConstructor
public class ArtworkDTO { private Long id; private String title; private String description; private boolean isPublished; private Long ownerId; }