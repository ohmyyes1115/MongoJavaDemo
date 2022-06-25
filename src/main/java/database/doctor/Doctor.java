package database.doctor;

import database.schedule.Schedule;

public class Doctor {
    private String mDoctorId;
    private String mDoctorName;
    // private Photo mPhoto;
    private String mCertificate;
    private String mMail;
    private String mPassword;
    // private Schedule mSchedule;

    public Doctor setDoctorId(String doctorId) {
        mDoctorId = doctorId;
        return this;
    }

    public String getDoctorId() {
        return mDoctorId;
    }

    public Doctor setDoctorName(String doctorName) {
        mDoctorName = doctorName;
        return this;
    }

    public String getDoctorName() {
        return mDoctorName;
    }

    // public Doctor setPhoto(Photo photo) {
    //     mPhoto = photo;
    //     return this;
    // }

    // public Photo getPhoto() {
    //     return mPhoto;
    // }

    public Doctor setCertificate(String cert) {
        mCertificate = cert;
        return this;
    }

    public String getCertificate() {
        return mCertificate;
    }

    public Doctor setMail(String mail) {
        mMail = mail;
        return this;
    }

    public String getMail() {
        return mMail;
    }

    public String getPassword() {
        return mPassword;
    }

    public Doctor setPassword(String pw) {
        mPassword = pw;
        return this;
    }
}
