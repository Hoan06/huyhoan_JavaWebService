package ra.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.MedicationDTO;
import ra.model.dto.request.MedicationPatchDTO;
import ra.model.dto.response.ApiDataResponse;
import ra.model.dto.response.MedicationDTOResponse;
import ra.service.MedicationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medications")
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<?>> getMedications(
            @RequestParam(value = "name", required = false) String nameSearch,
            @RequestParam(value = "manufacturer", required = false) String manuSearch,
            @RequestParam(value = "page", required = false) Integer page) {

        Integer size = 3;

        if ((nameSearch != null && !nameSearch.trim().isEmpty()) ||
                (manuSearch != null && !manuSearch.trim().isEmpty())) {

            int currentPage = (page != null) ? page : 1;

            Page<MedicationDTOResponse> searchResult = medicationService.search(nameSearch, manuSearch, currentPage, size);
            return new ResponseEntity<>(new ApiDataResponse<>(
                    true, "Tìm kiếm thuốc thành công !", searchResult, null, HttpStatus.OK
            ), HttpStatus.OK);
        }

        if (page != null) {
            Page<MedicationDTOResponse> pagedResult = medicationService.getAlls(page, size);
            return new ResponseEntity<>(new ApiDataResponse<>(
                    true, "Lấy danh sách thuốc có phân trang thành công !", pagedResult, null, HttpStatus.OK
            ), HttpStatus.OK);
        }

        List<MedicationDTOResponse> allMedications = medicationService.getAllMedications();
        return new ResponseEntity<>(new ApiDataResponse<>(
                true, "Lấy danh sách thuốc thành công !", allMedications, null, HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<MedicationDTOResponse>> addMedication(@Valid @RequestBody MedicationDTO medicationDTO){
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm mới thuốc thành công !",
                medicationService.save(medicationDTO),
                null,
                HttpStatus.CREATED
        ) , HttpStatus.CREATED);
    }

    @PutMapping("/{meId}")
    public ResponseEntity<ApiDataResponse<MedicationDTOResponse>> updateMedication(@Valid @RequestBody MedicationDTO medicationDTO , @PathVariable("meId") Long medId ){
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật thuốc thành công !",
                medicationService.updateFull(medId , medicationDTO),
                null,
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @PatchMapping("/{meId}")
    public ResponseEntity<ApiDataResponse<MedicationDTOResponse>> updatePartMedication(@Valid @RequestBody MedicationPatchDTO medication , @PathVariable("meId") Long medId ){
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật một phần thuốc thành công !",
                medicationService.updatePart(medId , medication),
                null,
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @DeleteMapping("/{meId}")
    public ResponseEntity<ApiDataResponse<Boolean>> deleteMedication(@PathVariable("meId") Long medId ){
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Xóa thuốc thành công !",
                medicationService.deleteById(medId),
                null,
                HttpStatus.NO_CONTENT
        ) , HttpStatus.NO_CONTENT);
    }
}
