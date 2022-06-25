package database.checkin;

public interface CheckInDAO {
    void CRUD_create(CheckIn checkIn);
    CheckIn CRUD_read_GetCheckIn();
    void CRUD_update(CheckIn checkIn);
    void CRUD_delete_DeleteAll();
}
