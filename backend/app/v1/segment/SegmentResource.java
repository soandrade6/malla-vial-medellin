package v1.segment;

import v1.roadway.RoadWayResource;

import java.util.List;


public class SegmentResource {
    private String id;
    private String segmentNumber;
    private double length;
    private String nomenclature;
    private List<RoadWayResource> roadways;

    public SegmentResource(){

    }

    public SegmentResource(String id,String segmentNumber, double length, String nomenclature,List<RoadWayResource> roadways ) {
        this.id = id;
        this.segmentNumber = segmentNumber;
        this.length = length;
        this.nomenclature = nomenclature;
        this.roadways = roadways;
    }

    public SegmentResource(SegmentData segmentData){
        this.id = segmentData.getId().toString();
        this.segmentNumber = segmentData.getSegmentNumber();
        this.length = segmentData.getLength();
        this.nomenclature = segmentData.getNomenclature();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}