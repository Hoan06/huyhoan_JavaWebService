package ra.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.RoomDTORequest;
import ra.model.dto.request.RoomDTORequestUpdate;
import ra.model.dto.response.ApiDataResponse;
import ra.model.dto.response.RoomDTOResponse;
import ra.service.RoomService;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<RoomDTOResponse>>> getAllRooms(@RequestParam(value = "page" , defaultValue = "1") Integer page) {
        int size = 3;
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách thành công",
                roomService.getAlls(page,size),
                null,
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<RoomDTOResponse>> createRoom(@Valid @RequestBody RoomDTORequest request){
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm mới phòng thành công !",
                roomService.insert(request),
                null,
                HttpStatus.CREATED
        ),HttpStatus.CREATED);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<ApiDataResponse<RoomDTOResponse>> updateFull(@PathVariable Long roomId , @Valid @RequestBody RoomDTORequest request){
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật phòng thành công !",
                roomService.updateFull(roomId,request),
                null,
                HttpStatus.OK
        ),HttpStatus.OK);
    }

    @PatchMapping("/{roomId}")
    public ResponseEntity<ApiDataResponse<RoomDTOResponse>> updatePatch(@PathVariable Long roomId , @Valid @RequestBody RoomDTORequestUpdate request){
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật thuộc tính của phòng thành công !",
                roomService.updatePatch(roomId,request),
                null,
                HttpStatus.OK
        ),HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<ApiDataResponse<Boolean>> deleteRoom(@PathVariable Long roomId ){
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Xóa phòng thành công !",
                roomService.delete(roomId),
                null,
                HttpStatus.OK
        ),HttpStatus.OK);
    }
}
