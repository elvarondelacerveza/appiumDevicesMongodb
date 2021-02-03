package com.mongodb.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.starter.models.Device;
//import com.mongodb.starter.models.Person;
import org.bson.BsonDocument;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class MongoDBDeviceRepository implements DeviceRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                           .readPreference(ReadPreference.primary())
                                                                           .readConcern(ReadConcern.MAJORITY)
                                                                           .writeConcern(WriteConcern.MAJORITY)
                                                                           .build();
    private final MongoClient client;
    private MongoCollection<Device> deviceCollection;

    public MongoDBDeviceRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        deviceCollection = client.getDatabase("ITA").getCollection("ita-devices", Device.class);
    }

    @Override
    public Device save(Device device) {
    	device.setId(new ObjectId());
    	deviceCollection.insertOne(device);
    	return device;
    }

//    @Override
//    public List<Person> saveAll(List<Person> persons) {
//        try (ClientSession clientSession = client.startSession()) {
//            return clientSession.withTransaction(() -> {
//                persons.forEach(p -> p.setId(new ObjectId()));
//                personCollection.insertMany(clientSession, persons);
//                return persons;
//            }, txnOptions);
//        }
//    }

    @Override
    public List<Device> findAll() {
        return deviceCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<Device> findAll(List<String> ids) {
        return deviceCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
    }

    @Override
    public Device findOne(String id) {
        return deviceCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public long count() {
        return deviceCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return deviceCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long delete(List<String> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> deviceCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> deviceCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public Device update(Device device) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return deviceCollection.findOneAndReplace(eq("_id", device.getId()), device, options);       
    }

//    @Override
//    public long update(List<Device> devices) {
//        List<WriteModel<Device>> writes = devices.stream()
//                                                 .map(p -> new ReplaceOneModel<>(eq("_id", p.getId()), p))
//                                                 .collect(Collectors.toList());
//        try (ClientSession clientSession = client.startSession()) {
//            return clientSession.withTransaction(
//                    () -> deviceCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
//        }
//    }

//    @Override
//    public double getAverageAge() {
//        List<Bson> pipeline = asList(group(new BsonNull(), avg("averageAge", "$age")), project(excludeId()));
//        return deviceCollection.aggregate(pipeline, AverageAgeDTO.class).first().getAverageAge();
//    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }
}
