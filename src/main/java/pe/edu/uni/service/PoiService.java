package pe.edu.uni.service;

import pe.edu.uni.dto.PointOfInterest;
import java.util.List;

public interface PoiService {
    List<PointOfInterest> getAllPois();
    PointOfInterest getPoiById(String id);
}
