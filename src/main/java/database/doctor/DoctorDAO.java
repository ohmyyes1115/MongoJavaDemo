package database.doctor;

public interface DoctorDAO {
    void CRUD_create(Doctor doctor);

    Doctor CRUD_read_GetDoctor(String doctorId);
    String CRUD_read_GetDoctorName(String doctorId);
    
    void CRUD_update(Doctor doctor);
    void CRUD_delete(String doctorId);
}
