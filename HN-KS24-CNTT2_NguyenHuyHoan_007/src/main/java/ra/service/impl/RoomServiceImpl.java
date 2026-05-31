package ra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.mapper.RoomMapper;
import ra.model.dto.request.RoomDTORequest;
import ra.model.dto.request.RoomDTORequestUpdate;
import ra.model.dto.response.RoomDTOResponse;
import ra.model.entity.Room;
import ra.repository.RoomRepository;
import ra.service.RoomService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public Page<RoomDTOResponse> getAlls(Integer page, Integer size) {
        if (page == null || page < 1) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Room> pageResult = roomRepository.findAllByIsDeletedFalse(pageable);
        List<Room> content = pageResult.getContent();
        List<RoomDTOResponse> contentResult = content.stream().map(roomMapper::mapToRoomResponse).toList();
        return new PageImpl<>(
                contentResult, pageResult.getPageable(),pageResult.getTotalElements()
        );
    }

    @Override
    public RoomDTOResponse insert(RoomDTORequest request) {
        Room result = roomRepository.save(roomMapper.mapToRoom(request));
        return roomMapper.mapToRoomResponse(result);
    }

    @Override
    public RoomDTOResponse updateFull(Long meId, RoomDTORequest roomDTO) {
        Room room = roomRepository.findByIdAndIsDeletedFalse(meId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy id " + meId));
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setRoomType(roomDTO.getRoomType());
        room.setPricePerNight(roomDTO.getPricePerNight());
        room.setStatus(roomDTO.getStatus());
        if (roomDTO.getIsDeleted() != null) {
            room.setDeleted(roomDTO.getIsDeleted());
        }
        Room result =  roomRepository.save(room);
        return roomMapper.mapToRoomResponse(result);
    }

    @Override
    public RoomDTOResponse updatePatch(Long meId, RoomDTORequestUpdate dto) {
        Room room = roomRepository.findByIdAndIsDeletedFalse(meId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy id " + meId));

        if (dto.getIsDeleted() != null) {
            room.setDeleted(dto.getIsDeleted());
        }
        if (dto.getRoomNumber() != null) {
            room.setRoomNumber(dto.getRoomNumber());
        }
        if (dto.getRoomType() != null) {
            room.setRoomType(dto.getRoomType());
        }
        if (dto.getStatus() != null) {
            room.setStatus(dto.getStatus());
        }
        if (dto.getPricePerNight() != null){
            room.setPricePerNight(dto.getPricePerNight());
        }
        Room result = roomRepository.save(room);
        return roomMapper.mapToRoomResponse(result);
    }

    @Override
    public boolean delete(Long id) {
        Room room = roomRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy id " + id));
        room.setDeleted(true);
        roomRepository.save(room);
        return true;
    }
}
