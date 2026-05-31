package ra.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ra.model.entity.Room;
import ra.model.entity.RoomStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomDTOResponse {
    private Long id;
    private Integer roomNumber;
    private String roomType;
    private double pricePerNight;
    private RoomStatus status;
    private boolean isDeleted;
}
