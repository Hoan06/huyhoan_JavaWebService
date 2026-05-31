package ra.service;

import org.springframework.data.domain.Page;
import ra.model.dto.request.RoomDTORequest;
import ra.model.dto.request.RoomDTORequestUpdate;
import ra.model.dto.response.RoomDTOResponse;

public interface RoomService {
    Page<RoomDTOResponse> getAlls(Integer page, Integer size);
    RoomDTOResponse insert(RoomDTORequest request);
    RoomDTOResponse updateFull(Long roomId , RoomDTORequest request);
    RoomDTOResponse updatePatch(Long roomId , RoomDTORequestUpdate request);
    boolean delete(Long id);
}
