package database.schedule;

import java.time.LocalDate;
import java.util.*;

import util.Period;

public class Schedule {

    // private LocalDate date;
    // private List<String> mMorning = new ArrayList<String>();
    // private List<String> mAfternoon = new ArrayList<String>();
    // private List<String> mNight = new ArrayList<String>();

    // 新版
    private String mDoctorId;
    private String mDate;
    private String mAmpm;

    // public void updatePeriodSchedule(Period period, String memberName, boolean workOrNot) {
    //     List<String> periodSchedule = getPeriodSchedule(period);

    //     if (workOrNot) {
    //         if (periodSchedule.stream().noneMatch(name -> name.equals(memberName))) {
    //             periodSchedule.add(memberName);
    //         }
    //     }
    //     else {
    //         periodSchedule.remove(memberName);
    //     }
    // }

    // private List<String> getPeriodSchedule(Period period) {
    //     if (period == Period.MORNING) {
    //         return mMorning;
    //     }
    //     else if (period == Period.AFTERNOON) {
    //         return mAfternoon;
    //     }
    //     else if (period == Period.NIGHT) {
    //         return mNight;
    //     }

    //     return null;
    // }

    // public Schedule setDate(LocalDate date) {
    //     this.date = date;
    //     return this;
    // }

    // public LocalDate getDate() {
    //     return date;
    // }
    
    // public Schedule setMorning(List<String> morning) {
    //     this.mMorning = morning;
    //     return this;
    // }

    // public List<String> getMorning() {
    //     return mMorning;
    // }
    
    // public Schedule setAfternoon(List<String> afternoon) {
    //     this.mAfternoon = afternoon;
    //     return this;
    // }

    // public List<String> getAfternoon() {
    //     return mAfternoon;
    // }
    
    // public Schedule setNight(List<String> night) {
    //     this.mNight = night;
    //     return this;
    // }

    // public List<String> getNight() {
    //     return mNight;
    // }

    // 新版 ==========================================
    public Schedule setDoctorId(String doctorId) {
        mDoctorId = doctorId;
        return this;
    }

    public String getDoctorId() {
        return mDoctorId;
    }

    public Schedule setDate(String date) {
        mDate = date;
        return this;
    }

    public String getDate() {
        return mDate;
    }

    public Schedule setAmPm(String ampm) {
        mAmpm = ampm;
        return this;
    }

    public String getAmPm() {
        return mAmpm;
    }
}
