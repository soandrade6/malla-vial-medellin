package v1.segment;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import v1.roadway.RoadWayData;

import java.util.List;

@Entity
@Table(name = "segment")
public class SegmentData {

    public SegmentData() {
    }

    public SegmentData(String segmentNumber, double length, String nomenclature){
        this.segmentNumber = segmentNumber;
        this.length = length;
        this.nomenclature = nomenclature;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String segmentNumber;
    public double length;
    public String nomenclature;

    @OneToMany(mappedBy = "segment", cascade = CascadeType.ALL)
    @JsonManagedReference
    public List<RoadWayData> roadways;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSegmentNumber() {
        return segmentNumber;
    }

    public void setSegmentNumber(String segmentNumber) {
        this.segmentNumber = segmentNumber;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    public List<RoadWayData> getRoadways() {
        return roadways;
    }

    public void setRoadways(List<RoadWayData> roadways) {
        this.roadways = roadways;
    }
}
