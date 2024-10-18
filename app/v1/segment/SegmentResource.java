package v1.segment;

import v1.curb.CurbResource;
import v1.roadway.RoadWayResource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;


public class SegmentResource {

    private Long id;
    @NotNull(message = "Segment number cannot be null")
    @Positive(message = "Segment must be greater than 0")
    private Integer segmentNumber;

    @NotNull(message = "length number cannot be null")
    @Positive(message = "Segment must be greater than 0")
    private double length;

    @NotNull(message = "Nomenclature cannot be null")
    @Size(min = 1, message = "Nomenclature must not be empty")
    private String nomenclature;
    private List<RoadWayResource> roadways;
    private List<CurbResource> curbs;

    public SegmentResource(){

    }

    public SegmentResource(Long id, int segmentNumber, double length, String nomenclature,List<RoadWayResource> roadways, List<CurbResource> curbs ) {
        this.id = id;
        this.segmentNumber = segmentNumber;
        this.length = length;
        this.nomenclature = nomenclature;
        this.roadways = roadways;
        this.curbs = curbs;
    }

    public SegmentResource(SegmentData segmentData, String link){
        this.id = segmentData.getId();
        this.segmentNumber = segmentData.getSegmentNumber();
        this.length = segmentData.getLength();
        this.nomenclature = segmentData.getNomenclature();
    }

    public SegmentResource(SegmentData segmentData){
        this.id = segmentData.getId();
        this.segmentNumber = segmentData.getSegmentNumber();
        this.length = segmentData.getLength();
        this.nomenclature = segmentData.getNomenclature();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSegmentNumber() {
        return segmentNumber;
    }

    public void setSegmentNumber(Integer segmentNumber) {
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
}