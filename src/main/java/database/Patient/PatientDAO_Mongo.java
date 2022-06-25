package database.Patient;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.*;
import com.mongodb.client.model.Projections;

import database.AbstractMongoDAOBase;
import database.Constants;
import database.Patient.Patient.Chart;

import static com.mongodb.client.model.Filters.*;

import util.Period;

public class PatientDAO_Mongo extends AbstractMongoDAOBase implements PatientDAO {

    public PatientDAO_Mongo(String mongoDbUri) {
        super(mongoDbUri);
    }

    @Override
    public void CRUD_create(Patient patient) {

        // 1. 取得 MongoClient
        // 2. 取得 MongoCollection
        // 3. 用 MongoCollection 去做事

        // try{}裡面是mongoDB的API
        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            MongoCollection<Document> collection =
                mongoClient.getDatabase(getDbName()).getCollection(getCollectionName());

                // insert一個document類別                                  // 如果要能直接 insert 一個 Patient (也就是不用先 Patient 轉 Document 才 insert)
                collection.insertOne(createDocumentFromPatient(patient)); // 請參考 CheckInDAO_Mongo.java 
        }

        // 這是自己把 『取得 MongoCollection』 的 code，統一封裝在自創的 class AbstractMongoDAOBase 裡面
        // 可以取代上面的 1. 2. 步
        // acquireCollection_Void(collection -> collection.insertOne(createDocumentFromPatient(patient)));
    }

    @Override
    public Patient CRUD_read_GetPatient(String idCard) {

        // 1. 取得 MongoClient
        // 2. 取得 MongoCollection
        // 3. 用 MongoCollection 去做事

        // try{}裡面是mongoDB的API
        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            MongoCollection<Document> collection =
                mongoClient.getDatabase(getDbName()).getCollection(getCollectionName());
                                                                    // eq方法(欄位, 值) 回傳指定欄位=指定值的Bson型別條件
            FindIterable<Document> findIterable = collection.find(eq(Constants.FIELD_NAME_PATIENT_IDCARD, idCard));
                    // 把查詢回來的Document型別 轉型為Patient
            return createPatientFromDocument(findIterable.first());
        }

        // 這是自己把 『取得 MongoCollection』 的 code，統一封裝在自創的 class AbstractMongoDAOBase 裡面
        // 可以取代上面的 1. 2. 步
        // return acquireCollection_Return(collection -> createPatientFromDocument(
        //                                         collection.find(eq(Constants.FIELD_NAME_PATIENT_IDCARD, idCard)).first()));
    }

    @Override
    public Patient CRUD_read_GetPatient(LocalDate date, Period period, String doctorId, int reservationNum) {

        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            MongoCollection<Document> collection =
                mongoClient.getDatabase(getDbName()).getCollection(getCollectionName());

            // match "chart" 這個 array 裡有沒有任何一個 element 符合指定的 "date" "ampm" "drID" "reservationNum"
            Bson chartFilter = elemMatch(
                                    Constants.FIELD_NAME_PATIENT_CHART,
                                    and(
                                        eq(Constants.FIELD_NAME_CHART_DATE, date.toString()),
                                        eq(Constants.FIELD_NAME_CHART_AMPM, period.toString()),
                                        eq(Constants.FIELD_NAME_CHART_DOCTOR_ID, doctorId),
                                        eq(Constants.FIELD_NAME_CHART_RESERVATION_NUM, reservationNum)));

            return createPatientFromDocument(
                                // find( 參數篩選條件 是Bson型別物件) 回傳FindIterable<Document> 
                                // FindIterable 有 iterator() 方法，可以取得類似 iterator 
                                //              也有 first() 方法，拿到符合條件的第一個 Document 
                    collection.find(chartFilter)
                              .projection(Projections.include(Constants.FIELD_NAME_PATIENT_CHART)).first());
        }
    }

    @Override
    public List<Patient> CRUD_read_GetPatientsOfAMoment(String doctorId, LocalDate date, Period period) {

        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            ArrayList<Patient> qualifiedPatients = new ArrayList<>();

            MongoCollection<Document> collection =
                mongoClient.getDatabase(getDbName()).getCollection(getCollectionName());
        
            // match "chart" 這個 array 裡有沒有任何一個 element 符合指定的 "drID" "date" "ampm"
            Bson chartFilter = elemMatch(
                                    Constants.FIELD_NAME_PATIENT_CHART,
                                    and(
                                        eq(Constants.FIELD_NAME_CHART_DOCTOR_ID, doctorId),
                                        eq(Constants.FIELD_NAME_CHART_DATE, date.toString()),
                                        eq(Constants.FIELD_NAME_CHART_AMPM, period.toString())));

            collection.find(chartFilter)
                        .iterator().forEachRemaining(document -> qualifiedPatients.add(createPatientFromDocument(document)));

            return qualifiedPatients;
        }
    }

    @Override
    public void CRUD_update(Patient patient) {
        if ((patient == null) || (patient.getIdCard() == null)) {
            return;
        }

        // 1. 取得 MongoClient
        // 2. 取得 MongoCollection
        // 3. 用 MongoCollection 去做事

        // try{}裡面是mongoDB的API
        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            MongoCollection<Document> collection =
                mongoClient.getDatabase(getDbName()).getCollection(getCollectionName());
                
            collection.replaceOne(eq(Constants.FIELD_NAME_PATIENT_IDCARD, patient.getIdCard()), createDocumentFromPatient(patient));
        }

        // 這是自己把 『取得 MongoCollection』 的 code，統一封裝在自創的 class AbstractMongoDAOBase 裡面
        // 可以取代上面的 1. 2. 步
        // acquireCollection_Return(collection ->
        //      collection.replaceOne(eq(Constants.FIELD_NAME_PATIENT_IDCARD, patient.getIdCard()), createDocumentFromPatient(patient)));
    }

    @Override
    public void CRUD_delete(String idCard) {
        
    }

    // --------------------------------------------------

    // template-pattern hook method
    @Override
    protected String getDbName() {
        return Constants.DB_NAME;
    }

    // template-pattern hook method
    @Override
    protected String getCollectionName() {
        return Constants.COLLECTION_NAME_PATIENT;
    }
    
    /**
     * 根據『Patient』，創建一個 Document
     */
    private Document createDocumentFromPatient(Patient patient) {
        if (patient == null) {
            return null;
        }
                // new一個document(參數key, 參數value) 用.append()接續加入欄key value
                // 遇到list 裡面的型別要是document 如果不是要轉型別
        return new Document(Constants.FIELD_NAME_PATIENT_MEMBER_ID, patient.getMemberId())
                    .append(Constants.FIELD_NAME_PATIENT_IDCARD, patient.getIdCard())
                    .append(Constants.FIELD_NAME_PATIENT_NAME, patient.getName())
                    .append(Constants.FIELD_NAME_PATIENT_BIRTHDAY, patient.getBirthday())
                    .append(Constants.FIELD_NAME_PATIENT_PHONE, patient.getPhone())
                    .append(Constants.FIELD_NAME_PATIENT_MAIL, patient.getMail())
                    .append(Constants.FIELD_NAME_PATIENT_CHART, createDocumentsFromCharts(patient.getCharts()));
    }
    
    /**
     * 根據『Document』，創建一個 Patient 把查詢回來的Document型別 轉型為Patient
     */
    private Patient createPatientFromDocument(Document document) {
        if (document == null) {
            return null;
        }

        List<Document> chartDocList = document.getList(Constants.FIELD_NAME_PATIENT_CHART, Document.class);
        List<Chart> chartList = null;

        if (chartDocList != null) {
            chartList = chartDocList.stream()
                                    .map(chartDoc -> new Chart()
                                                            .setIdCard(chartDoc.get(Constants.FIELD_NAME_CHART_IDCARD, String.class))
                                                            .setDate(chartDoc.get(Constants.FIELD_NAME_CHART_DATE, String.class))
                                                            .setAmPm(chartDoc.get(Constants.FIELD_NAME_CHART_AMPM, String.class))
                                                            .setReservationNum(chartDoc.get(Constants.FIELD_NAME_CHART_RESERVATION_NUM, Integer.class))
                                                            .setDoctorId(chartDoc.get(Constants.FIELD_NAME_CHART_DOCTOR_ID, String.class))
                                                            .setDoctorName(chartDoc.get(Constants.FIELD_NAME_CHART_DOCTOR_NAME, String.class))
                                                            .setCheckedIn(chartDoc.get(Constants.FIELD_NAME_CHART_CHECKED_IN, Boolean.class))
                                                            .setNote(chartDoc.get(Constants.FIELD_NAME_CHART_NOTE, String.class)))
                                    .collect(Collectors.toList());
        }

        return new Patient()
                    .setMemberId(document.get(Constants.FIELD_NAME_PATIENT_MEMBER_ID, String.class))
                    .setIdCard(document.get(Constants.FIELD_NAME_PATIENT_IDCARD, String.class))
                    .setName(document.get(Constants.FIELD_NAME_PATIENT_NAME, String.class))
                    .setBirthday(document.get(Constants.FIELD_NAME_PATIENT_BIRTHDAY, String.class))
                    .setPhone(document.get(Constants.FIELD_NAME_PATIENT_PHONE, String.class))
                    .setMail(document.get(Constants.FIELD_NAME_PATIENT_MAIL, String.class))
                    .setCharts(chartList);
    }

    
    /**
     * 根據『List<Chart>』，創建 List<Document> 把List中的每一個Chart型別物件轉型成Document型別
     */
    private List<Document> createDocumentsFromCharts(List<Chart> chartList) {
        return chartList.stream()
                        .map(chart -> createDocumentFromChart(chart))
                        .collect(Collectors.toList());
    }

    
    /**
     * 根據『Chart』，創建一個 Document  把chart類別的attribute 轉型成document的欄位儲存
     */
    private Document createDocumentFromChart(Chart chart) {
        return new Document(Constants.FIELD_NAME_CHART_IDCARD, chart.getIdCard())
                    .append(Constants.FIELD_NAME_CHART_DATE, chart.getDate())
                    .append(Constants.FIELD_NAME_CHART_AMPM, chart.getAmPm())
                    .append(Constants.FIELD_NAME_CHART_RESERVATION_NUM, chart.getReservationNum())
                    .append(Constants.FIELD_NAME_CHART_DOCTOR_ID, chart.getDoctorId())
                    .append(Constants.FIELD_NAME_CHART_DOCTOR_NAME, chart.getDoctorName())
                    .append(Constants.FIELD_NAME_CHART_CHECKED_IN, chart.getCheckedIn())
                    .append(Constants.FIELD_NAME_CHART_NOTE, chart.getNote());
    }
}
