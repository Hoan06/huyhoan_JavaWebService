package ra.service;

import org.springframework.data.domain.Page;
import ra.model.dto.request.MedicationDTO;
import ra.model.dto.request.MedicationPatchDTO;
import ra.model.dto.response.MedicationDTOResponse;
import ra.model.entity.Medication;

import java.util.List;

public interface MedicationService {
    List<MedicationDTOResponse> getAllMedications();
    Page<MedicationDTOResponse> getAlls(Integer page, Integer size);
    MedicationDTOResponse save(MedicationDTO medication);
    MedicationDTOResponse updateFull(Long meId , MedicationDTO medication);
    MedicationDTOResponse updatePart(Long meId, MedicationPatchDTO dto);
    Boolean deleteById(Long medId);
    Page<MedicationDTOResponse> search(String nameSearch , String manuSearch, Integer page, Integer size);
}
