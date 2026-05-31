package ra.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.edu.exception.MedicationNotFound;
import ra.edu.mapper.MedicationMapper;
import ra.edu.model.dto.request.MedicationDTORequest;
import ra.edu.model.dto.response.MedicationDTOResponse;
import ra.edu.model.entity.Medication;
import ra.edu.repository.MedicationRepository;
import ra.edu.service.MedicationService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;
    private final ObjectMapper objectMapper;


    @Override
    public List<MedicationDTOResponse> getMedications() {
        List<Medication> list = medicationRepository.findAll();
        return list.stream().map(medicationMapper::ormToMedicationResponse).toList();
    }

    @Override
    public Page<MedicationDTOResponse> getAllMedications(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Medication> pageResult = medicationRepository.findAll(pageable);
        List<Medication> content = pageResult.getContent();
        List<MedicationDTOResponse> contentResult = content.stream().map(medicationMapper::ormToMedicationResponse).toList();
        return new PageImpl<>(
            contentResult, pageResult.getPageable(),pageResult.getTotalElements()
        );
    }

    @Override
    public MedicationDTOResponse insertMedication(MedicationDTORequest medicationDTORequest) {
        Medication medication = medicationMapper.dtoToMadication(medicationDTORequest);
        Medication result = medicationRepository.save(medication);
        return medicationMapper.ormToMedicationResponse(result);
    }

    @Override
    public MedicationDTOResponse updateMedication(Long id, MedicationDTORequest medicationDTORequest) {
        medicationRepository.findById(id).orElseThrow(()-> new MedicationNotFound("Không tìm thấy thuốc có mã: "+id));
        Medication medication = medicationMapper.dtoToMadication(medicationDTORequest);
        medication.setId(id);
        Medication result = medicationRepository.save(medication);
        return medicationMapper.ormToMedicationResponse(result);
    }

    @Override
    public MedicationDTOResponse updatePartialMedication(Long id, MedicationDTORequest medicationDTORequest) {
        Medication medication = medicationRepository.findById(id).orElseThrow(() -> new MedicationNotFound("Không tìm thấy thuốc có mã: " + id));
        if(medicationDTORequest.getIsDelete()!=null){
            medication.setIsDelete(medicationDTORequest.getIsDelete());
        }
        if(medicationDTORequest.getName()!=null && !medicationDTORequest.getName().isBlank()){
            medication.setName(medicationDTORequest.getName());
        }
        if(medicationDTORequest.getStatus()!=null){
            medication.setStatus(medicationDTORequest.getStatus());
        }
        if(medicationDTORequest.getManufacturer()!=null && !medicationDTORequest.getManufacturer().isBlank()){
            medication.setManufacturer(medicationDTORequest.getManufacturer());
        }
        if(medicationDTORequest.getPrice()!=null && medicationDTORequest.getPrice()>0){
            medication.setPrice(medicationDTORequest.getPrice());
        }
        Medication result = medicationRepository.save(medication);
        return medicationMapper.ormToMedicationResponse(result);
    }

    @Override
    public MedicationDTOResponse deleteMedication(Long id) {
        Medication medication = medicationRepository.findById(id).orElseThrow(() -> new MedicationNotFound("Không tìm thấy thuốc có mã: " + id));
        medication.setIsDelete(false);
        Medication result = medicationRepository.save(medication);
        return medicationMapper.ormToMedicationResponse(result);
    }
}
