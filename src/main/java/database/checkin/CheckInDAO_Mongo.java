package database.checkin;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import database.AbstractMongoDAOBase;
import database.Constants;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class CheckInDAO_Mongo extends AbstractMongoDAOBase implements CheckInDAO {

    public CheckInDAO_Mongo(String mongoDbUri) {
        super(mongoDbUri);
    }

    @Override
    public void CRUD_create(CheckIn checkIn) {
        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            // 多了 withCodecRegistry
            MongoCollection<CheckIn> collection =
                mongoClient.getDatabase(getDbName()).withCodecRegistry(pojoCodecRegistry).getCollection(getCollectionName(), CheckIn.class);
                
            collection.insertOne(checkIn);
        }
    }

    @Override
    public CheckIn CRUD_read_GetCheckIn() {
        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            // 多了 withCodecRegistry
            MongoCollection<CheckIn> collection =
                mongoClient.getDatabase(getDbName()).withCodecRegistry(pojoCodecRegistry).getCollection(getCollectionName(), CheckIn.class);
                
                                    // 因為 database 裡的 CheckIn-Collection 裡面只有一筆 Document
                                    // 所以直接用『drID』欄位的存在性 當作過濾條件，就一定可以 match 到唯一的那一筆
            return collection.find().first();
        }
    }

    @Override
    public void CRUD_update(CheckIn checkIn) {
        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            // 多了 withCodecRegistry
            MongoCollection<CheckIn> collection =
                mongoClient.getDatabase(getDbName()).withCodecRegistry(pojoCodecRegistry).getCollection(getCollectionName(), CheckIn.class);
                
                                    // 因為 database 裡的 CheckIn-Collection 裡面只有一筆 Document
                                    // 所以直接用『drID』欄位的存在性 當作過濾條件，就一定可以 match 到唯一的那一筆
            collection.replaceOne(Filters.exists(Constants.FIELD_NAME_CHECKIN_DOCTOR_ID), checkIn);
        }
    }

    @Override
    public void CRUD_delete_DeleteAll() {
        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            // 多了 withCodecRegistry
            MongoCollection<CheckIn> collection =
                mongoClient.getDatabase(getDbName()).withCodecRegistry(pojoCodecRegistry).getCollection(getCollectionName(), CheckIn.class);

            
            collection.deleteMany(Filters.exists(Constants.FIELD_NAME_CHECKIN_DOCTOR_ID));
        }
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
        return Constants.COLLECTION_NAME_CHECKIN;
    }
    
    /**
     * 根據『CheckIn』，創建一個 Document
     */
    private Document createDocumentFromCheckIn(CheckIn checkIn) {
        if (checkIn == null) {
            return null;
        }

        return new Document(Constants.FIELD_NAME_CHECKIN_DOCTOR_ID, checkIn.getDoctorId())
                    .append(Constants.FIELD_NAME_CHECKIN_DOCTOR_NAME, checkIn.getDoctorName())
                    .append(Constants.FIELD_NAME_CHECKIN_PATIENT_CHECKIN, checkIn.getPatientCheckIn());
    }
    
}
