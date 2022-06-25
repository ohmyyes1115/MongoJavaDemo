package database.schedule;

import java.time.LocalDate;
import java.util.List;

import util.Period;

public interface ScheduleDAO {
    void CRUD_create(Schedule schedule);
    List<String> CRUD_read_getDoctorIds(LocalDate date, Period period);
    void CRUD_update(Schedule schedule);
    void CRUD_delete(Schedule schedule);
}