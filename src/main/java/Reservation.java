import java.time.LocalDate;

import util.Period;

public class Reservation {
    private LocalDate mDate;
    private Period mPeriod;
    private String mDoctorName;
    private String mDoctorId;
    private int mReservationNum;

    public Reservation setDate(LocalDate date) {
        mDate = date;
        return this;
    }

    public LocalDate getDate() {
        return mDate;
    }

    public Reservation setPeriod(Period period) {
        mPeriod = period;
        return this;
    }

    public Period getPeriod() {
        return mPeriod;
    }

    public Reservation setDoctorName(String doctorName) {
        mDoctorName = doctorName;
        return this;
    }

    public String getDoctorName() {
        return mDoctorName;
    }

    public Reservation setDoctorId(String doctorId) {
        mDoctorId = doctorId;
        return this;
    }

    public String getDoctorId() {
        return mDoctorId;
    }
        
    public Reservation setReservationNum(int num) {
        mReservationNum = num;
        return this;
    }

    public int getReservationNum() {
        return mReservationNum;
    }
}
