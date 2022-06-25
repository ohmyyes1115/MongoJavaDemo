package database;

public class Constants {

    // Database 名稱
    public static final String DB_NAME = "PatientSystem";
    
    // common
    public static final String IDCARD = "IDcard";  // 身分證字號
    public static final String DATE   = "date";    // 日期
    public static final String AMPM   = "ampm";    // 日期

    // Collection_Doctor 相關
    public static final String COLLECTION_NAME_DOCTOR = "Doctor";
    public static final String FIELD_NAME_DOCTOR_DOCTOR_ID       = "doctorId";     // 醫生ID
    public static final String FIELD_NAME_DOCTOR_DOCTOR_NAME     = "doctorName";   // 醫生姓名
    public static final String FIELD_NAME_DOCTOR_DOCTOR_CERT     = "certificate";  // 醫生證書字號
    public static final String FIELD_NAME_DOCTOR_DOCTOR_MAIL     = "mail";         // 信箱
    public static final String FIELD_NAME_DOCTOR_DOCTOR_PASSWORD = "password";     // 密碼

    // Collection_Schedule 相關
    public static final String COLLECTION_NAME_SCHEDULE = "Schedule";
    public static final String FIELD_NAME_SCHEDULE_DOCTOR_ID = "doctorId";  // 醫生ID
    public static final String FIELD_NAME_SCHEDULE_DATE      = DATE;        // 日期
    public static final String FIELD_NAME_SCHEDULE_AMPM      = AMPM;        // 時段 
    // public static final String FIELD_NAME_SCHEDULE_MORNING   = "morning";    // 早上
    // public static final String FIELD_NAME_SCHEDULE_AFTERNOON = "afternoon";  // 下午
    // public static final String FIELD_NAME_SCHEDULE_NIGHT     = "night";      // 晚上

    // Collection_Patient 相關
    public static final String COLLECTION_NAME_PATIENT = "Patient";
    public static final String FIELD_NAME_PATIENT_MEMBER_ID = "Member_ID";  // 會員帳號
    public static final String FIELD_NAME_PATIENT_IDCARD    = IDCARD;       // 身分證字號
    public static final String FIELD_NAME_PATIENT_NAME      = "name";       // 姓名
    public static final String FIELD_NAME_PATIENT_BIRTHDAY  = "birthday";   // 生日
    public static final String FIELD_NAME_PATIENT_PHONE     = "phone";      // 電話
    public static final String FIELD_NAME_PATIENT_MAIL      = "mail";       // 信箱
    public static final String FIELD_NAME_PATIENT_CHART     = "chart";      // 就診資訊
    // --------------------------------------------------
    public static final String FIELD_NAME_CHART_IDCARD          = IDCARD;            // 身分證字號
    public static final String FIELD_NAME_CHART_DATE            = DATE;              // 預約日期
    public static final String FIELD_NAME_CHART_AMPM            = "ampm";            // 上午下午
    public static final String FIELD_NAME_CHART_RESERVATION_NUM = "reservationNum";  // 預約號碼
    public static final String FIELD_NAME_CHART_DOCTOR_ID       = "drID";            // 醫生編號
    public static final String FIELD_NAME_CHART_DOCTOR_NAME     = "drN";             // 醫生姓名
    public static final String FIELD_NAME_CHART_CHECKED_IN      = "checkIn";         // 報到狀態
    public static final String FIELD_NAME_CHART_NOTE            = "note";            // 病歷內容

    // Collection_CheckIn 相關
    public static final String COLLECTION_NAME_CHECKIN = "CheckIn";
    public static final String FIELD_NAME_CHECKIN_DOCTOR_ID       = /*"drID"*/  "doctorId";            // 醫生編號
    public static final String FIELD_NAME_CHECKIN_DOCTOR_NAME     = /*"drN"*/   "doctorName";             // 醫生姓名
    public static final String FIELD_NAME_CHECKIN_PATIENT_CHECKIN     = "patientCheckIn";      // 報到號碼
    
    public static final String FIELD_NAME_DATE = "date";
}
