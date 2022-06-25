package database.Patient;

import java.util.*;

public class Patient {
    private String mMemberId;
    private String mIdCard;
    private String mName;
    private String mBirthday;
    private String mPhone;
    private String mMail;
    private List<Chart> mCharts = new ArrayList<>();

    public Patient setMemberId(String memberId) {
        mMemberId = memberId;
        return this;
    }

    public String getMemberId() {
        return mMemberId;
    }

    public Patient setIdCard(String idCard) {
        mIdCard = idCard;
        return this;
    }

    public String getIdCard() {
        return mIdCard;
    }

    public Patient setName(String name) {
        mName = name;
        return this;
    }

    public String getName() {
        return mName;
    }

    public Patient setBirthday(String birthday) {
        mBirthday = birthday;
        return this;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public Patient setPhone(String phone) {
        mPhone = phone;
        return this;
    }

    public String getPhone() {
        return mPhone;
    }

    public Patient setMail(String mail) {
        mMail = mail;
        return this;
    }

    public String getMail() {
        return mMail;
    }

    public Patient setCharts(List<Chart> charts) {
        mCharts = charts;
        return this;
    }

    public List<Chart> getCharts() {
        return mCharts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    name: ").append(mName).append("\n")
          .append("birthday: ").append(mBirthday).append("\n")
          .append("   phone: ").append(mPhone).append("\n")
          .append("    mail: ").append(mMail).append("\n")
          .append("  IDcard: ").append(mIdCard).append("\n")
          .append("   chart: ").append("\n")
                            .append(mCharts.toString());

        return sb.toString();
    }

    // --------------------------------------------------

    public static class Chart {
        private String mIdCard;
        private String mDate;
        private String mAmPm;
        private int mReservationNum;
        private String mDoctorId;
        private String mDoctorName;
        private boolean mCheckedIn;
        private String mNote;

        public Chart setIdCard(String idCard) {
            mIdCard = idCard;
            return this;
        }
    
        public String getIdCard() {
            return mIdCard;
        }

        public Chart setDate(String date) {
            mDate = date;
            return this;
        }

        public String getDate() {
            return mDate;
        }

        public Chart setAmPm(String ampm) {
            mAmPm = ampm;
            return this;
        }

        public String getAmPm() {
            return mAmPm;
        }
        
        public Chart setReservationNum(int num) {
            mReservationNum = num;
            return this;
        }

        public int getReservationNum() {
            return mReservationNum;
        }

        public Chart setDoctorId(String doctorId) {
            mDoctorId = doctorId;
            return this;
        }

        public String getDoctorId() {
            return mDoctorId;
        }

        public Chart setDoctorName(String doctorName) {
            mDoctorName = doctorName;
            return this;
        }

        public String getDoctorName() {
            return mDoctorName;
        }

        public Chart setCheckedIn(boolean checkedIn) {
            mCheckedIn = checkedIn;
            return this;
        }

        public boolean getCheckedIn() {
            return mCheckedIn;
        }

        public Chart setNote(String note) {
            mNote = note;
            return this;
        }

        public String getNote() {
            return mNote;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('\t').append("          date: ").append(mDate).append("\n")
              .append('\t').append("        IdCard: ").append(mIdCard).append("\n")
              .append('\t').append("          AmPm: ").append(mAmPm).append("\n")
              .append('\t').append("reservationNum: ").append(mReservationNum).append("\n")
              .append('\t').append("      doctorId: ").append(mDoctorId).append("\n")
              .append('\t').append("    doctorName: ").append(mDoctorName).append("\n")
              .append('\t').append("     checkedIn: ").append(mCheckedIn).append("\n")
              .append('\t').append("          note: ").append(mNote).append("\n");
    
            return sb.toString();
        }
    }
}

