package ra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.mapper.MedicationMapper;
import ra.model.dto.request.MedicationDTO;
import ra.model.dto.request.MedicationPatchDTO;
import ra.model.dto.response.MedicationDTOResponse;
import ra.model.entity.Medication;
import ra.repository.MedicationRepository;
import ra.service.MedicationService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    @Override
    public List<MedicationDTOResponse> getAllMedications() {
        List<Medication> list = medicationRepository.findAll();
        return list.stream().map(medicationMapper::mapToMedicationDTOResponse).toList();
    }

    @Override
    public Page<MedicationDTOResponse> getAlls(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Medication> list =  medicationRepository.findAll(pageable);
        List<Medication> pageList = list.getContent();
        List<MedicationDTOResponse> listResult = pageList.stream().map(medicationMapper::mapToMedicationDTOResponse).toList();
        return new PageImpl<>(
                listResult,list.getPageable(),list.getTotalElements()
                );
    }

    @Override
    public MedicationDTOResponse save(MedicationDTO medication) {
        Medication medicationEntity = medicationMapper.mapToMedication(medication);
        Medication result = medicationRepository.save(medicationEntity);
        return medicationMapper.mapToMedicationDTOResponse(result);
    }

    @Override
    public MedicationDTOResponse updateFull(Long meId, MedicationDTO medication) {
        medicationRepository.findById(meId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy id " + meId));
        Medication medicationEntity =  medicationMapper.mapToMedication(medication);
        medicationEntity.setId(meId);
        Medication result =  medicationRepository.save(medicationEntity);
        return medicationMapper.mapToMedicationDTOResponse(result);
    }

    @Override
    public MedicationDTOResponse updatePart(Long meId, MedicationPatchDTO dto) {
        Medication medicationIn = medicationRepository.findById(meId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy id " + meId));

        if (dto.getManufacturer() != null) {
            medicationIn.setManufacturer(dto.getManufacturer());
        }
        if (dto.getName() != null) {
            medicationIn.setName(dto.getName());
        }
        if (dto.getPrice() != null) {
            medicationIn.setPrice(dto.getPrice());
        }
        if (dto.getStatus() != null) {
            medicationIn.setStatus(dto.getStatus());
        }

        Medication result = medicationRepository.save(medicationIn);
        return medicationMapper.mapToMedicationDTOResponse(result);
    }

    @Override
    public Boolean deleteById(Long medId) {
        Medication medication = medicationRepository.findById(medId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy id " + medId));
        medication.setIs_deleted(true);
        return true;
    }

    @Override
    public Page<MedicationDTOResponse> search(String nameSearch , String manuSearch , Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        if (nameSearch == null || nameSearch.isEmpty()) {
            Page<Medication> list = medicationRepository.findByManufacturerContaining(manuSearch,pageable);
            List<Medication> pageList = list.getContent();
            List<MedicationDTOResponse> listResult = pageList.stream().map(medicationMapper::mapToMedicationDTOResponse).toList();
            return new PageImpl<>(listResult,list.getPageable(),list.getTotalElements());
        } else if (manuSearch == null || manuSearch.isEmpty()) {
            Page<Medication> list = medicationRepository.findByNameContaining(manuSearch,pageable);
            List<Medication> pageList = list.getContent();
            List<MedicationDTOResponse> listResult = pageList.stream().map(medicationMapper::mapToMedicationDTOResponse).toList();
            return new PageImpl<>(listResult,list.getPageable(),list.getTotalElements());
        }
        Page<Medication> list = medicationRepository.findByNameContainingAndManufacturerContaining(nameSearch,manuSearch,pageable);
        List<Medication> pageList = list.getContent();
        List<MedicationDTOResponse> listResult = pageList.stream().map(medicationMapper::mapToMedicationDTOResponse).toList();
        return new PageImpl<>(listResult,list.getPageable(),list.getTotalElements());    }
}
