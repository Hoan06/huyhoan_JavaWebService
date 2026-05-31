package ra.mapper;

import org.springframework.stereotype.Component;
import ra.model.dto.request.RoomDTORequest;
import ra.model.dto.response.RoomDTOResponse;
import ra.model.entity.Room;

@Component
public class RoomMapper {
    public Room mapToRoom(RoomDTORequest request){
        return Room.builder()
                .roomNumber(request.getRoomNumber())
                .roomType(request.getRoomType())
                .pricePerNight(request.getPricePerNight())
                .status(request.getStatus())
                .isDeleted(false)
                .build();
    }

    public RoomDTOResponse mapToRoomResponse(Room room){
        return RoomDTOResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .pricePerNight(room.getPricePerNight())
                .status(room.getStatus())
                .isDeleted(room.isDeleted())
                .build();
    }
}
