import java.time.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.mongodb.client.*;
import com.mongodb.client.model.*;

import database.checkin.*;
import database.doctor.Doctor;
import database.doctor.DoctorDAO;
import database.doctor.DoctorDAO_Mongo;
import database.Patient.*;
import database.Patient.Patient.Chart;
import database.schedule.*;
import util.Period;

import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * PatientManager
 */
public class PatientManager {

    // Database 名稱
    // private static final String DB_NAME = "PatientSystem";

    // private static final String FIELD_NAME_SCHEDULE_MORNING = "morning";
    // private static final String FIELD_NAME_SCHEDULE_AFTERNOON = "afternoon";
    // private static final String FIELD_NAME_SCHEDULE_NIGHT = "night";
    
    // private static final String FIELD_NAME_DATE = "date";

    private ScheduleDAO mScheduleDAO;
    private PatientDAO mPatientDAO;
    private DoctorDAO mDoctorDAO;
    private CheckInDAO mCheckInDAO;

    // private MongoClient mMongoClient;

    public PatientManager(String mongoDbUri) {
        mScheduleDAO = new ScheduleDAO_Mongo(mongoDbUri);
        mPatientDAO = new PatientDAO_Mongo(mongoDbUri);
        mDoctorDAO = new DoctorDAO_Mongo(mongoDbUri);
        mCheckInDAO = new CheckInDAO_Mongo(mongoDbUri);
    }

    /**
     * 取得『醫生排班』，可以實作『門診時刻』
     * 
     * @param doctorName
     * @param date 指定的 某一天
     * @return 這個醫生，這一天的三班班表 (e.g., {true, true, false} = (o)早班 (o)中班 (x)晚班)
     */
    public ArrayList<Boolean> getDoctorSchedule(String doctorName, LocalDate date) {
        return null;
        // Schedule schedule = mScheduleDAO.CRUD_read(date);

        // if (schedule == null) {
        //     return null;
        // }

        // ArrayList<Boolean> doctorSchedule = new ArrayList<Boolean>();

        // doctorSchedule.add(
        //     schedule.getMorning().stream()
        //                 .anyMatch(eachDoctorName -> eachDoctorName.equals(doctorName)));

        // doctorSchedule.add(
        //     schedule.getAfternoon().stream()
        //                 .anyMatch(eachDoctorName -> eachDoctorName.equals(doctorName)));

        // doctorSchedule.add(
        //     schedule.getNight().stream()
        //                 .anyMatch(eachDoctorName -> eachDoctorName.equals(doctorName)));

        // return doctorSchedule;

        // ========================== 以上是用 DAO 實作 ==========================

        // ArrayList<Boolean> doctorSchedule = new ArrayList<Boolean>();

        // MongoCollection<Document> collection = getCollection(COLLECTION_NAME_SCHEDULE);
        
        // FindIterable<Document> findIterable = collection.find(eq(FIELD_NAME_DATE, date.toString())); 
        // Document doc = findIterable.first();
        
        // if (doc != null) {
        //     List<String> morningList = doc.getList(FIELD_NAME_SCHEDULE_MORNING, String.class);
        //     List<String> afternoonList = doc.getList(FIELD_NAME_SCHEDULE_AFTERNOON, String.class);
        //     List<String> nightList = doc.getList(FIELD_NAME_SCHEDULE_NIGHT, String.class);

        //     doctorSchedule.add(
        //         morningList.stream()
        //                     .anyMatch(eachDoctorName -> eachDoctorName.equals(doctorName)));

        //     doctorSchedule.add(
        //         afternoonList.stream()
        //                     .anyMatch(eachDoctorName -> eachDoctorName.equals(doctorName)));

        //     doctorSchedule.add(
        //         nightList.stream()
        //                     .anyMatch(eachDoctorName -> eachDoctorName.equals(doctorName)));
        // }

        // return doctorSchedule;
    }

    /**
     * 設定『醫生排班』，可以實作『門診時刻』
     * 
     * @param doctorName 
     * @param date 指定的 某一天
     * @param scheduleOfThisDay 一天三班，一定要給3個
     */
    public void setDoctorSchedule(String doctorName, LocalDate date, ArrayList<Boolean> scheduleOfThisDay) {
        // if ((scheduleOfThisDay == null) || (scheduleOfThisDay.size() < 3)) {
        //     return;
        // }

        // Schedule schedule = mScheduleDAO.CRUD_read(date);
        
        // if (schedule == null) {
        //     schedule = createSchedule(date);
        //     mScheduleDAO.CRUD_create(schedule);
        // }

        // Boolean workInMorning = scheduleOfThisDay.get(0);
        // Boolean workInAfternoon = scheduleOfThisDay.get(1);
        // Boolean workInNight = scheduleOfThisDay.get(2);

        // schedule.updatePeriodSchedule(Period.MORNING, doctorName, workInMorning);
        // schedule.updatePeriodSchedule(Period.AFTERNOON, doctorName, workInAfternoon);
        // schedule.updatePeriodSchedule(Period.NIGHT, doctorName, workInNight);

        // mScheduleDAO.CRUD_update(schedule);

        // ========================== 以上是用 DAO 實作 ==========================

        // MongoCollection<Document> collection = getCollection(COLLECTION_NAME_SCHEDULE);
        
        // FindIterable<Document> findIterable = collection.find(eq(FIELD_NAME_DATE, date.toString()));
        // Document doc = findIterable.first();

        // // 原本還沒有這一天的 Document，就先 insert 一筆空的到 DB
        // if (doc == null) {
        //     doc = createDocument_Schedule(date);
        //     collection.insertOne(doc);
        // }

        // Boolean workInMorning = scheduleOfThisDay.get(0);
        // Boolean workInAfternoon = scheduleOfThisDay.get(1);
        // Boolean workInNight = scheduleOfThisDay.get(2);
        
        // Bson updates = null;
        // updates = appendUpdate(updates, createUpdateForSchedule(FIELD_NAME_SCHEDULE_MORNING, doctorName, workInMorning));
        // updates = appendUpdate(updates, createUpdateForSchedule(FIELD_NAME_SCHEDULE_AFTERNOON, doctorName, workInAfternoon));
        // updates = appendUpdate(updates, createUpdateForSchedule(FIELD_NAME_SCHEDULE_NIGHT, doctorName, workInNight));

        // // UpdateOptions options = new UpdateOptions().upsert(false);

        // collection.updateOne(eq(FIELD_NAME_DATE, date.toString()), updates);
    }

    /**
     * 預約掛號，可以實作『門診預約』
     * 
     * @param doctorId 醫生編號
     * @param date 指定的 某一天
     * @param period 哪一個時段 (早中晚)
     * @return 掛號號碼
     */
    public int reserve(String doctorId, LocalDate date, Period period) {
        if (true)
        // 如果這個醫生，在這一天，的這個時間，已經有 3 個病人掛號，那就給 4 號
        return mPatientDAO.CRUD_read_GetPatientsOfAMoment(doctorId, date, period).size() + 1;

        ArrayList<Chart> chartList = new ArrayList<>();
        chartList.add(new Chart()
                                .setIdCard("D123456789")
                                .setDate(LocalDate.of(2022, 9, 3).toString())
                                .setAmPm("下午")
                                .setReservationNum(3)
                                .setDoctorId("doctor_id_12345")
                                .setDoctorName("葉花花")
                                .setNote("植牙"));
        chartList.add(new Chart()
                                .setIdCard("D123456789")
                                .setDate(LocalDate.of(2022, 10, 3).toString())
                                .setAmPm("晚上")
                                .setReservationNum(2)
                                .setDoctorId("doctor_id_12345")
                                .setDoctorName("葉花花")
                                .setNote("植牙"));

        Patient patient = new Patient()
                                .setMemberId("member_id_12345")
                                .setIdCard("D123456789")
                                .setName("陳小春")
                                .setBirthday(LocalDate.of(1987, 6, 14).toString())
                                .setPhone("0987654321")
                                .setMail("chen@gmail.com")
                                .setCharts(chartList);
        
        // mPatientDAO_Mongo.CRUD_create(patient);
        
        // Patient read = mPatientDAO_Mongo.CRUD_read("D123456789");
        // System.out.println(read.toString());

        mPatientDAO.CRUD_update(patient.setName("陳小小春"));

        return 0;
    }

    /**
     * 查詢指定病人的所有預約，可以實作『預約查詢』
     * 
     * @param patientId 病人的『身分證字號』
     * @return 此病人的所有預約
     */
    public List<Reservation> queryReservation(String patientId) {
        LocalDate now_Date = LocalDate.now();
        LocalDateTime now_Time = LocalDateTime.now();

        Predicate<Chart> laterThanNow = chart -> {
            int dateDiff = chart.getDate().compareTo(now_Date.toString());
            int periodDiff = Period.of(chart.getAmPm()).compareTo(Period.of(now_Time));

            return (dateDiff > 0) ||  // 日期比現在晚
                   (dateDiff == 0) && (periodDiff >= 0);  // 日期同一天 && 時段比現在晚(包含現在時段)
        };

        return getCharts(patientId)  // optional<  List<Chart>  >
                    .map(charts -> charts.stream()
                                        .filter(laterThanNow)
                                        .filter(chart -> chart.getReservationNum() > 0)  // 取消掛號時，會簡單把掛預約號碼設0
                                        .map(chart -> new Reservation()
                                                            .setDate(LocalDate.parse(chart.getDate()))
                                                            .setPeriod(Period.of(chart.getAmPm()))
                                                            .setDoctorName(chart.getDoctorName())
                                                            .setDoctorId(chart.getDoctorId())
                                                            .setReservationNum(chart.getReservationNum()))
                                        .collect(Collectors.toList()))
                    .orElse(null);
    }
    
    public boolean cancelReservation(String patientId, LocalDate date, Period period, String doctorId, int reservationNum) {
        if (reservationNum <= 0) {
            return false;
        }

        Patient patient = mPatientDAO.CRUD_read_GetPatient(patientId);

        if (patient != null) {
            List<Patient.Chart> chartList = patient.getCharts();

            if (chartList != null) {

                for (Patient.Chart chart : chartList) {
                    if (chart.getDate().equals(date.toString()) &&
                        Period.of(chart.getAmPm()) == period &&
                        chart.getDoctorId().equals(doctorId) && chart.getReservationNum() == reservationNum) {

                            chart.setReservationNum(0);  // [簡單實作] 取消掛號就把原本掛號號碼設為 0
                            mPatientDAO.CRUD_update(patient);

                            return true;
                    }
                }
            }
        }

        return false;
    }

    public static class MedicalRecord {
        public String date;
        public String period;
        public String doctorName;
        public String note;

        public MedicalRecord(String date, String period, String doctorName, String note) {
            this.date = date;
            this.period = period;
            this.doctorName = doctorName;
            this.note = note;
        }
    }
    
    /**
     * 查詢指定病人的所有預約，可以實作『預約查詢』
     * 
     * @param patientId 病人的『身分證字號』
     * @return 就診紀錄
     */
    public List<MedicalRecord> queryMedicalRecords(String patientId) {
        return getCharts(patientId)
                    .map(charts -> charts.stream()
                                         .filter(chart -> chart.getCheckedIn())  // 要有『報到』的才算有『就診』
                                         .map(chart -> new MedicalRecord(
                                                            chart.getDate(),
                                                            chart.getAmPm(),
                                                            chart.getDoctorName(),
                                                            chart.getNote()))
                                         .collect(Collectors.toList()))
                    .orElse(null);
    }
    
    public void resetCheckInList() {
        LocalDate now_Date = LocalDate.now();
        Period now_Period = Period.now();

        // 把原本的 『報到清單(CheckIn)』 清除
        mCheckInDAO.CRUD_delete_DeleteAll();

        // 1. 查詢『現在時段』有『哪些醫生』有班
        // 2. 把這些醫生都重新加進『報到清單(CheckIn)』
        mScheduleDAO.CRUD_read_getDoctorIds(now_Date, now_Period)
                    .stream()
                    .forEach(doctorId -> mCheckInDAO.CRUD_create(new CheckIn()
                                                                        .setDoctorId(doctorId)
                                                                        .setDoctorName(mDoctorDAO.CRUD_read_GetDoctorName(doctorId))
                                                                        .setPatientCheckIn(Collections.emptyList())));
    }

    public boolean checkIn(String patientId) {
        // ToDo: 還需想一下，怎麼讓 QR-Code 再更聰明
        System.out.println("[checkIn] 還需想一下，怎麼讓 QR-Code 再更聰明");

        LocalDate now_Date = LocalDate.now();
        Period now_Period = Period.of(LocalDateTime.now());
        // LocalDate now_Date = LocalDate.of(2022, 6, 30);
        // Period now_Period = Period.MORNING;

        Patient patient = mPatientDAO.CRUD_read_GetPatient(patientId);

        if (patient == null) {
            System.out.println("[PatientManager::checkIn] 查無此病人 (patiendId = " + patientId + ")");
            return false;
        }

        List<Patient.Chart> chartList = patient.getCharts();

        Optional<Patient.Chart> chartOpt = chartList.stream()
                                                    .filter(chart -> chart.getDate().equals(now_Date.toString()) &&
                                                                     chart.getAmPm().equals(now_Period.toString()))
                                                    .findFirst();

        if (!chartOpt.isPresent()) {
            System.out.println("[PatientManager::checkIn] 病人 (" + patientId + ") 無此預約 (" + now_Date + " " + now_Period + ")");
            return false;
        }

        Patient.Chart chart = chartOpt.get();
        if (!chart.getCheckedIn()) {
            chart.setCheckedIn(true);

            mPatientDAO.CRUD_update(patient);

            CheckIn checkIn = mCheckInDAO.CRUD_read_GetCheckIn();
            checkIn.getPatientCheckIn().add(chart.getReservationNum());
            mCheckInDAO.CRUD_update(checkIn);
        }

        return true;
    }

    public boolean createDoctor(String doctorId, String doctorName, String certificate, String mail, String password) {
        if (mDoctorDAO.CRUD_read_GetDoctorName(doctorId) != null) {
            System.out.println("[PatientManager::createDoctor] 醫生已存在 (" + doctorName + " " + doctorId + ")");
            return false;
        }

        mDoctorDAO.CRUD_create(new Doctor()
                                    .setDoctorId(doctorId)
                                    .setDoctorName(doctorName)
                                    .setCertificate(certificate)
                                    .setMail(mail)
                                    .setPassword(password));
        return true;
    }

    public boolean updateDoctor(String doctorId, String doctorName, String certificate, String mail, String password) {
        // ToDo:
        return true;
    }

    public List<String> getDoctorIds(LocalDate date, Period period) {

        // mScheduleDAO.CRUD_create(new Schedule()
        //                                 .setDoctorId("doctor_id_yeh")
        //                                 .setDate(date.toString())
        //                                 .setAmPm(period.toString()));

        return mScheduleDAO.CRUD_read_getDoctorIds(date, period);
    }

    // --------------------------------------------------

    private Optional<Patient> getPatient(String patientId) {
        return Optional.ofNullable(mPatientDAO.CRUD_read_GetPatient(patientId));
    }

    private Optional<List<Patient.Chart>> getCharts(String patientId) {
        return getPatient(patientId)
                    .map(Patient::getCharts);
    }

    // private Bson createUpdateForSchedule(String filedName, String memberName, boolean workOrNot) {
    //     if (workOrNot) {
    //         return Updates.addToSet(filedName, memberName);
    //     }
    //     else {
    //         return Updates.pull(filedName, memberName);
    //     }
    // }

    // private Bson appendUpdate(Bson updates, Bson oneUpdate) {
    //     if (updates == null) {
    //         return oneUpdate;
    //     }
    //     return Updates.combine(updates, oneUpdate);
    // }

    // private MongoDatabase getDatabase(String dbName) {
    //     return mMongoClient.getDatabase(dbName);
    // }

    // private MongoCollection<Document> getCollection(String collectionName) {
    //     return getDatabase(DB_NAME).getCollection(collectionName);
    // }
    
    /**
     * 根據『日期』，創建一個空的 Schedule
     */
    // private Schedule createSchedule(LocalDate date) {
    //     return null;
    //     // return new Schedule()
    //     //             .setDate(date);
    // }
    
    /**
     * 根據『日期』，創建一個空的 Document
     */
    // private Document createDocument_Schedule(LocalDate date) {
    //     return new Document(FIELD_NAME_DATE, date.toString())
    //                 .append(FIELD_NAME_SCHEDULE_MORNING, new ArrayList<String>())
    //                 .append(FIELD_NAME_SCHEDULE_AFTERNOON, new ArrayList<String>())
    //                 .append(FIELD_NAME_SCHEDULE_NIGHT, new ArrayList<String>());
    // }
}