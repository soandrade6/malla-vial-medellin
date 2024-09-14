package v1.segment;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import v1.roadway.RoadWayData;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "segment")
public class SegmentData {

    public SegmentData() {
    }

    public SegmentData(int segmentNumber, double length, String nomenclature){
        this.segmentNumber = segmentNumber;
        this.length = length;
        this.nomenclature = nomenclature;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Segment number cannot be null")
    @Positive(message = "Segment must be greater than 0")
    private int segmentNumber;

    @NotNull(message = "length number cannot be null")
    @Positive(message = "Segment must be greater than 0")
    private double length;

    @NotNull(message = "Nomenclature cannot be null")
    @Size(min = 1, message = "Nomenclature must not be empty")
    private String nomenclature;

    @OneToMany(mappedBy = "segment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<RoadWayData> roadways;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSegmentNumber() {
        return segmentNumber;
    }

    public void setSegmentNumber(int segmentNumber) {
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
