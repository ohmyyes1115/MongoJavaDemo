package database;

import java.util.function.Consumer;
import java.util.function.Function;

import org.bson.Document;

import com.mongodb.client.*;

public abstract class AbstractMongoDAOBase {

    private String mMongoDbUri;

    public AbstractMongoDAOBase(String mongoDbUri) {
        mMongoDbUri = mongoDbUri;
    }

    protected abstract String getDbName();
    protected abstract String getCollectionName();

    public String getMongoDbUri() {
        return mMongoDbUri;
    }

    public void acquireCollection_Void(Consumer<MongoCollection<Document>> consumer) {
        try (MongoClient mongoClient = MongoClients.create(mMongoDbUri)) {
                consumer.accept(getConnection(mongoClient));
        }
    }

    public <T> T acquireCollection_Return(Function<MongoCollection<Document>, T> mapper) {
        try (MongoClient mongoClient = MongoClients.create(mMongoDbUri)) {
                return mapper.apply(getConnection(mongoClient));
        }
    }

    // --------------------------------------------------

    private MongoCollection<Document> getConnection(MongoClient mongoClient) {
        return mongoClient.getDatabase(getDbName()).getCollection(getCollectionName());
    }
}
