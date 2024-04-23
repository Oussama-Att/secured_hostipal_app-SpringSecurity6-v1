package att.g5.hopital_app_attouch.service;

import jakarta.transaction.Transactional;
import att.g5.hopital_app_attouch.entities.Patient;
import att.g5.hopital_app_attouch.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class HospitalServiceImpl implements IHospitalService {
    private PatientRepository patientRepository;

    public HospitalServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }
}
