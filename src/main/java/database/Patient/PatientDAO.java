package database.Patient;

import java.time.LocalDate;
import java.util.List;

import util.Period;

public interface PatientDAO {
    void CRUD_create(Patient patient);

    Patient CRUD_read_GetPatient(String idCard);

    //回傳 某醫師在某天某時段 掛號X號的病人
    Patient CRUD_read_GetPatient(LocalDate date, Period period, String doctorId, int reservationNum);
    
    // 回傳 某醫師在某天某時段的 所有病人
    List<Patient> CRUD_read_GetPatientsOfAMoment(String doctorId, LocalDate date, Period period);
    
    void CRUD_update(Patient patient);
    void CRUD_delete(String idCard);
}
