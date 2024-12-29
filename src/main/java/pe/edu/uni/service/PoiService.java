package pe.edu.uni.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import jakarta.enterprise.context.ApplicationScoped;
import pe.edu.uni.dto.PointOfInterest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class PoiService {

    private final MongoCollection<Document> poiCollection;

    public PoiService(MongoClient mongoClient) {
        this.poiCollection = mongoClient.getDatabase("catalog")
            .getCollection("points_of_interest");
    }

    public List<PointOfInterest> getAllPois() {
        return StreamSupport.stream(poiCollection.find().spliterator(), false)
                .map(this::toPoi)
                .collect(Collectors.toList());
    }

    public PointOfInterest getPoiById(String id) {
        Document document = poiCollection.find(Filters.eq("_id", id)).first();
        if (document != null) {
            return toPoi(document);
        }
        return null;
    }

    private PointOfInterest toPoi(Document document) {
        return new PointOfInterest(
                document.getObjectId("_id").toString(),
                document.getString("name"),
                document.getDouble("latitude"),
                document.getDouble("longitude"));
    }
}
