package database.checkin;

import java.util.List;

public class CheckIn {
    private String mDoctorId;
    private String mDoctorName;
    private List<Integer> mPatientCheckIn;

    public CheckIn setDoctorId(String doctorId) {
        mDoctorId = doctorId;
        return this;
    }

    public String getDoctorId() {
        return mDoctorId;
    }

    public CheckIn setDoctorName(String doctorName) {
        mDoctorName = doctorName;
        return this;
    }

    public String getDoctorName() {
        return mDoctorName;
    }

    public CheckIn setPatientCheckIn(List<Integer> list) {
        mPatientCheckIn = list;
        return this;
    }

    public List<Integer> getPatientCheckIn() {
        return mPatientCheckIn;
    }
}
