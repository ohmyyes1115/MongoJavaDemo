package database.schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;

import database.AbstractMongoDAOBase;
import database.Constants;
import util.Period;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ScheduleDAO_Mongo extends AbstractMongoDAOBase implements ScheduleDAO {

    public ScheduleDAO_Mongo(String mongoDbUri) {
        super(mongoDbUri);
    }

    @Override
    public void CRUD_create(Schedule schedule) {
        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            // 多了 withCodecRegistry
            MongoCollection<Schedule> collection =
                mongoClient.getDatabase(getDbName()).withCodecRegistry(pojoCodecRegistry).getCollection(getCollectionName(), Schedule.class);
            
            collection.insertOne(schedule);
        }
    }

    // @Override
    // public Schedule CRUD_read(LocalDate date) {
    //     try (MongoClient mongoClient = MongoClients.create(mMongoDbUri)) {

    //         MongoCollection<Document> collection =
    //             mongoClient.getDatabase(Constants.DB_NAME).getCollection(Constants.COLLECTION_NAME_SCHEDULE);
        
    //         FindIterable<Document> findIterable = collection.find(eq(Constants.FIELD_NAME_DATE, date.toString())); 
    //         Document doc = findIterable.first();

    //         if (doc == null) {
    //             return null;
    //         }

    //         return new Schedule()
    //                     .setDate(
    //                         LocalDate.parse(doc.get(Constants.FIELD_NAME_DATE, String.class)))
    //                     .setMorning(
    //                         doc.getList(Constants.FIELD_NAME_SCHEDULE_MORNING, String.class))
    //                     .setAfternoon(
    //                         doc.getList(Constants.FIELD_NAME_SCHEDULE_AFTERNOON, String.class))
    //                     .setNight(
    //                         doc.getList(Constants.FIELD_NAME_SCHEDULE_NIGHT, String.class));
    //     }
    //     return null;
    // }

    @Override
    public List<String> CRUD_read_getDoctorIds(LocalDate date, Period period) {
        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            // 多了 withCodecRegistry
            MongoCollection<Schedule> collection =
                mongoClient.getDatabase(getDbName()).withCodecRegistry(pojoCodecRegistry).getCollection(getCollectionName(), Schedule.class);
            
            List<String> doctorIdList = new ArrayList<>();

            Bson filter = and(
                            Filters.eq(Constants.FIELD_NAME_SCHEDULE_DATE, date.toString()),
                            Filters.eq(Constants.FIELD_NAME_SCHEDULE_AMPM, period.toString()));
            
            collection.find(filter)
                      .map(Schedule::getDoctorId)
                      .into(doctorIdList);

            return doctorIdList;
        }
    }

    @Override
    public void CRUD_update(Schedule schedule) {
        if ((schedule == null) || (schedule.getDate() == null)) {
            return;
        }

        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            MongoCollection<Document> collection =
                mongoClient.getDatabase(Constants.DB_NAME).getCollection(Constants.COLLECTION_NAME_SCHEDULE);
                
            Document document = createDocumentFromSchedule(schedule);

            collection.replaceOne(eq(Constants.FIELD_NAME_DATE, schedule.getDate().toString()), document);

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
    }

    @Override
    public void CRUD_delete(Schedule schedule) {
        // TODO Auto-generated method stub
        
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
        return Constants.COLLECTION_NAME_SCHEDULE;
    }
    
    /**
     * 根據『Schedule』，創建一個 Document
     */
    private Document createDocumentFromSchedule(Schedule schedule) {
        // return new Document(Constants.FIELD_NAME_DATE, schedule.getDate().toString())
        //             .append(Constants.FIELD_NAME_SCHEDULE_MORNING, schedule.getMorning())
        //             .append(Constants.FIELD_NAME_SCHEDULE_AFTERNOON, schedule.getAfternoon())
        //             .append(Constants.FIELD_NAME_SCHEDULE_NIGHT, schedule.getNight());
        return null;
    }
    
}
