import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.*;
import com.mongodb.client.model.*;

import util.Period;

import org.bson.Document;

public class MongoTest {
	
	private static final String TABLE_NAME = "test_collection2";

    private static String mMongoDbUri = "mongodb://localhost:27017";  // "mongodb://localhost:<port>"
	
	public static void main(String[] args) {
		// new DemoAccessMongoDBWithJDBC();
		// demo_MongoDbAPI();

		PatientManager patientManager = new PatientManager(mMongoDbUri);
		// 先手動觸發 『SLF4J 的 warning log』，再畫一條線隔開
		patientManager.getDoctorIds(LocalDate.now(), Period.now());
		System.out.println("==============================\n");

		
		demo_CreateDoctor(patientManager);

		// 取得『醫生排班』
		String doctorName = "葉花花";
		// String doctorName = "陳花花";
		LocalDate date = LocalDate.of(2022, 6, 14);
		// demo_QueryDoctorSchedule(patientManager, doctorName, date);

		// 設定『醫生排班』
		// ArrayList<Boolean> schedule = new ArrayList<Boolean>(Arrays.asList(false, true, false));
		ArrayList<Boolean> schedule = new ArrayList<Boolean>(Arrays.asList(true, false, true));
		demo_SetDoctorSchedule(patientManager, doctorName, date, schedule);
		
		// demo_QueryDoctorSchedule(patientManager, doctorName, date);
		
		demo_Test(patientManager);

		demo_Reservation(patientManager);

		demo_QueryReservation(patientManager);
		demo_CancelReservation(patientManager);
		
		demo_QueryMedicalRecords(patientManager);

		demo_ResetCheckInList(patientManager);

		// demo_CheckIn(patientManager);
	}

	// demo [新增醫生]
	private static void demo_CreateDoctor(PatientManager patientManager) {
		String doctorId = "doctor_id_chen";
		String doctorName = "陳花花";
		String certificate = "fake_certificate";
		String mail = "fake_email";
		String password = "fake_password";

		boolean created = patientManager.createDoctor(doctorId, doctorName, certificate, mail, password);

		System.out.println("[新增醫生]： (" + doctorName + " " + doctorId + ")  證書字號=" + certificate + "  mail=" + mail + "  密碼=" + password);
		if (!created) {
			System.out.println("\t\t新增醫生失敗\n");
		}

		System.out.println("==============================");
	}

	// demo [預約掛號]
	private static void demo_Reservation(PatientManager patientManager) {
		String doctorId = "doctor_id_chen";
		LocalDate date = LocalDate.of(2022, 6, 30);
		Period period = Period.MORNING;

		int reservationNum = patientManager.reserve(
			doctorId, date, period);
		System.out.println("[預約掛號]： 醫生(doctorId) = " + doctorId + " " + date+ " " + period);
		System.out.println("\t\t取得號碼 = " + reservationNum + "\n");
	}

	// demo [預約查詢]
	private static void demo_QueryReservation(PatientManager patientManager) {
		// String patientId = "wrong_id";
		String patientId = "D123456789";

		List<Reservation> reservations = patientManager.queryReservation(patientId);

		System.out.println("[預約查詢]： 病人身分證字號 = " + patientId);

		if (reservations == null) {
			System.out.println("\t病人身分證字號不存在(" + patientId + ")");
			return;
		}

		System.out.println("\t\t日期        時段  醫師    號碼");
		reservations.forEach(reserv -> {
			System.out.println("\t\t" + reserv.getDate() + "  " + reserv.getPeriod() + "  " + reserv.getDoctorName() + "  " + reserv.getReservationNum());
		});

		System.out.println();
	}

	// demo [取消預約]
	private static void demo_CancelReservation(PatientManager patientManager) {
		// String patientId = "wrong_id";
		String patientId = "F123456789";

		LocalDate date = LocalDate.of(2022, 6, 30);
		Period period = Period.MORNING;
		String doctorId = "doctor_id_chen";
		int reservationNum = 1;

		boolean canceled = patientManager.cancelReservation(patientId, date, period, doctorId, reservationNum);

		System.out.println("[取消預約]： 病人身分證字號 = " + patientId + " " + date + " " + period + " " + reservationNum + "號");

		if (!canceled) {
			System.out.println("\t取消預約失敗(" + patientId + ")");
		}

		// ----------------- 取消約預後，再查詢一次，印出結果 -----------------

		List<Reservation> reservations = patientManager.queryReservation(patientId);

		if (reservations == null) {
			System.out.println();
			return;
		}

		System.out.println("[預約查詢]： 病人身分證字號 = " + patientId);
		System.out.println("\t\t日期        時段  醫師    號碼");
		reservations.forEach(reserv -> {
			System.out.println("\t\t" + reserv.getDate() + "  " + reserv.getPeriod() + "  " + reserv.getDoctorName() + "  " + reserv.getReservationNum());
		});

		System.out.println();
	}

	// demo [病歷查詢]
	private static void demo_QueryMedicalRecords(PatientManager patientManager) {
		// String patientId = "wrong_id";
		String patientId = "D123456789";

		List<PatientManager.MedicalRecord> medicalRecords = patientManager.queryMedicalRecords(patientId);

		System.out.println("[病歷查詢]： 病人身分證字號 = " + patientId);

		if (medicalRecords == null) {
			System.out.println("\t病人身分證字號不存在(" + patientId + ")");
			return;
		}

		System.out.println("\t\t日期        時段  醫師    病歷內容");
		medicalRecords.forEach(records -> {
			System.out.println("\t\t" + records.date + "  " + records.period + "  " + records.doctorName + "  " + records.note);
		});

		System.out.println();
	}

	// demo [取得『醫生排班』]
	private static void demo_QueryDoctorSchedule(PatientManager patientManager, String doctorName, LocalDate date) {
		// String doctorName = "陳花花";
		ArrayList<Boolean> doctorSchedule = 
			patientManager.getDoctorSchedule(doctorName, date);

		doctorSchedule.forEach(System.out::println);
		System.out.println("==============================");
	}

	// demo [設定『醫生排班』]
	private static void demo_SetDoctorSchedule(PatientManager patientManager, String doctorName, LocalDate date, ArrayList<Boolean> schedule) {
		patientManager.setDoctorSchedule(doctorName, date, schedule);
	}

	// demo [設定『醫生排班』]
	private static void demo_Test(PatientManager patientManager) {
        LocalDate now_Date = LocalDate.now();
        Period now_Period = Period.AFTERNOON;

		patientManager.getDoctorIds(now_Date, now_Period);
	}

	// demo [清除『報到清單』]
	private static void demo_ResetCheckInList(PatientManager patientManager) {
		patientManager.resetCheckInList();
		System.out.println("[重新設定『報到清單』]");
		System.out.println("==============================");
	}

	// demo [『就診報到』]
	private static void demo_CheckIn(PatientManager patientManager) {
		String patientId = "A123456789";
		System.out.println("[就診報到]： 病人身分證字號 = " + patientId);

		boolean checkedIn = patientManager.checkIn(patientId);

		if (!checkedIn) {
			System.out.println("\t就診報到失敗(" + patientId + ")");
		}

		System.out.println("==============================");
	}

    // --------------------------------------------------
	
	private static void demo_MongoDbAPI() {
		
		try (MongoClient mongoClient = MongoClients.create(mMongoDbUri)) {
	        MongoDatabase database = mongoClient.getDatabase("sample_mflix");
	        
	        // 拿到一個 Collection (平行SQL： table)
	        MongoCollection<Document> collection = database.getCollection(TABLE_NAME);
	        
	        // 把一筆 Document 加進 Collection (平行SQL： 把一筆資料加進 table)
//	        collection.insertOne(createDummyDocument());
	        
	        // 更新某 1 筆資料(Document)
	        collection.updateOne(
	        		Filters.eq("title", "MongoDB"),  // 找到 『第1筆』 滿足 『指定條件』 的資料(Document)
	        		new Document(                    // 對這1筆資料更新。  可以帶 2 個參數   
	        				"$set",                  // 第1個參數： 要怎麼更新這筆資料。  "$set" 就是去設定一組 (key, value)
	        				new Document("description","description22")));  // 第 2 個參數： 要更新成什麼
	        
	        // 更新某 n 筆資料(Document)   同上，只是符合的資料全部一起改，而非只改第 1 個
	        collection.updateMany(
	        		Filters.eq("title", "MongoDB"),  // 找到 『所有』 滿足 『指定條件』 的資料(Document)
	        		new Document(
	        				"$set",
	        				new Document("title", "MongoDB33")));
	        
	        // 刪除 滿足 『指定條件』 的 『第1筆』 資料(Document)
	        collection.deleteOne(Filters.eq("title", "MongoDB22"));
	        
	        // 刪除 滿足 『指定條件』 的 『所有』 資料(Document)
	        collection.deleteMany(Filters.eq("title", "MongoDB33"));
	        
	        printAllDocument(collection);
	        
	        FindIterable<Document> findIterable = collection.find(eq("title", "標題")); 
	        Document doc = findIterable.first();
	        if (doc != null) {
	        	System.out.println(doc.toJson());
	        }
			else {
				System.out.println("null");
			}
	    }
	}
	
	// 回傳假的 Document (平行SQL： table 裡的一筆資料)
	private static Document createDummyDocument() {
		return new Document("title", "中文測試").  
		             append("description", "database").  
		             append("likes", 100).  
		             append("by", "Fly");
	}
	
	private static void printAllDocument(MongoCollection<Document> collection) {
        FindIterable<Document> findIterable = collection.find();  
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        
        while(mongoCursor.hasNext()) {  
            System.out.println(mongoCursor.next());  
        }  
	}
}
