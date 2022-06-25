package database.doctor;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import database.AbstractMongoDAOBase;
import database.Constants;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.Optional;

public class DoctorDAO_Mongo extends AbstractMongoDAOBase implements DoctorDAO {

    public DoctorDAO_Mongo(String mongoDbUri) {
        super(mongoDbUri);
    }

    @Override
    public void CRUD_create(Doctor doctor) {
        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            // 多了 withCodecRegistry
            MongoCollection<Doctor> collection =
                mongoClient.getDatabase(getDbName()).withCodecRegistry(pojoCodecRegistry).getCollection(getCollectionName(), Doctor.class);
            
            collection.insertOne(doctor);
        }
    }

    @Override
    public Doctor CRUD_read_GetDoctor(String doctorId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String CRUD_read_GetDoctorName(String doctorId) {
        try (MongoClient mongoClient = MongoClients.create(getMongoDbUri())) {

            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            // 多了 withCodecRegistry
            MongoCollection<Doctor> collection =
                mongoClient.getDatabase(getDbName()).withCodecRegistry(pojoCodecRegistry).getCollection(getCollectionName(), Doctor.class);
            

            Bson filter = Filters.eq(Constants.FIELD_NAME_DOCTOR_DOCTOR_ID, doctorId);
    
            return Optional.ofNullable(collection.find(filter)
                                                 .projection(Projections.include(Constants.FIELD_NAME_DOCTOR_DOCTOR_NAME))
                                                 .first())
                            .map(Doctor::getDoctorName)
                            .orElse(null);
        }
    }

    @Override
    public void CRUD_update(Doctor doctor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void CRUD_delete(String doctorId) {
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
        return Constants.COLLECTION_NAME_DOCTOR;
    }
}
