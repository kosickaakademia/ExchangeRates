package sk.kosickaakademia.onofrej.exchange.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DatabaseMongo {
    private static MongoClient client =
            MongoClients.create();

    public void test() {
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> toys = database.getCollection("car");
        Document toy = new Document("name", "yoyo").append("ages", new Document("min", 5));
        toys.insertOne(toy);
    }

    public void writeData(double eur, String[] ratesGui){
        MongoDatabase database = client.getDatabase("exchangerates");
        MongoCollection<Document> collection = database.getCollection("history");
        Date currentDate = new Date();
        List<String> list = Arrays.asList(ratesGui.clone());
        Document doc = new Document("date",currentDate.toString()).append("value",eur).append("rates", list);
        System.out.println(doc.toString());
        collection.insertOne(doc);
    }


}
