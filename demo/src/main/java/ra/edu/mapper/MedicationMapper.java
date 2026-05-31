package ra.edu.mapper;

import org.springframework.stereotype.Component;
import ra.edu.model.dto.request.MedicationDTORequest;
import ra.edu.model.dto.response.MedicationDTOResponse;
import ra.edu.model.entity.Medication;

@Component
public class MedicationMapper {
    public Medication dtoToMadication(MedicationDTORequest medicationDTORequest){
        return Medication.builder()
                .price(medicationDTORequest.getPrice())
                .name(medicationDTORequest.getName())
                .isDelete(medicationDTORequest.getIsDelete())
                .manufacturer(medicationDTORequest.getManufacturer())
                .status(medicationDTORequest.getStatus())
                .build();
    }

    public MedicationDTOResponse ormToMedicationResponse(Medication medication){
        return MedicationDTOResponse.builder()
                .id(medication.getId())
                .price(medication.getPrice())
                .name(medication.getName())
                .isDelete(medication.getIsDelete())
                .manufacturer(medication.getManufacturer())
                .status(medication.getStatus())
                .build();
    }
}
