package ra.edu.service;

import org.springframework.data.domain.Page;
import ra.edu.model.dto.request.MedicationDTORequest;
import ra.edu.model.dto.response.MedicationDTOResponse;
import ra.edu.model.entity.Medication;

import java.util.List;

public interface MedicationService {
    //Không phân trang
    List<MedicationDTOResponse> getMedications();
    Page<MedicationDTOResponse> getAllMedications(Integer page, Integer pageSize);
    MedicationDTOResponse insertMedication(MedicationDTORequest medicationDTORequest);
    MedicationDTOResponse updateMedication(Long id, MedicationDTORequest medicationDTORequest);
    MedicationDTOResponse updatePartialMedication(Long id, MedicationDTORequest medicationDTORequest);
    MedicationDTOResponse deleteMedication(Long id);
}
