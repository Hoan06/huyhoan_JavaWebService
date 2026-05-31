package ra.mapper;

import org.springframework.stereotype.Component;
import ra.model.dto.request.MedicationDTO;
import ra.model.dto.response.MedicationDTOResponse;
import ra.model.entity.Medication;

@Component
public class MedicationMapper {
    public Medication mapToMedication(MedicationDTO medicationDTO) {
        return Medication.builder()
                .name(medicationDTO.getName())
                .status(medicationDTO.getStatus())
                .price(medicationDTO.getPrice())
                .manufacturer(medicationDTO.getManufacturer())
                .is_deleted(medicationDTO.getDelete())
                .build();
    }

    public MedicationDTOResponse mapToMedicationDTOResponse(Medication medication) {
        return MedicationDTOResponse.builder()
                .id(medication.getId())
                .name(medication.getName())
                .status(medication.getStatus())
                .price(medication.getPrice())
                .manufacturer(medication.getManufacturer())
                .is_deleted(medication.getIs_deleted())
                .build();
    }
}
