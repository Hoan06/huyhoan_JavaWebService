package ra.edu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.edu.model.dto.request.MedicationDTORequest;
import ra.edu.model.dto.response.ApiDataResponse;
import ra.edu.model.dto.response.MedicationDTOResponse;
import ra.edu.model.entity.Medication;
import ra.edu.service.MedicationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medications")
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<MedicationDTOResponse>>> getMedications() {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách thuốc thành công",
                medicationService.getMedications(),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<ApiDataResponse<Page<MedicationDTOResponse>>> getMadicationPaging(@RequestParam(name = "page", defaultValue = "1") Integer page) {
        Integer pageSize = 5;
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách thuốc trang " + page + " thành công",
                medicationService.getAllMedications(page - 1, pageSize),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<MedicationDTOResponse>> insertMedication(@Valid @RequestBody MedicationDTORequest medicationDTORequest) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm mới thuốc thành công",
                medicationService.insertMedication(medicationDTORequest),
                null,
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<MedicationDTOResponse>> updateMedication(@PathVariable Long id, @Valid @RequestBody MedicationDTORequest medicationDTORequest) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật thông tin thuốc thành công",
                medicationService.updateMedication(id, medicationDTORequest),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiDataResponse<MedicationDTOResponse>> updatePartialMedication(@PathVariable Long id, @RequestBody MedicationDTORequest medicationDTORequest) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật thông tin thuốc thành công",
                medicationService.updatePartialMedication(id, medicationDTORequest),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<MedicationDTOResponse>> deleteMedication(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật thông tin thuốc thành công",
                medicationService.deleteMedication(id),
                null,
                HttpStatus.OK //xóa mềm nên trạng thái trả về là OK
        ), HttpStatus.OK);
    }
}
