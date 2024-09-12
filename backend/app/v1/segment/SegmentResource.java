package v1.segment;

public class SegmentResource {
    private String id;
    private String link;
    public String segmentNumber;
    public double length;
    public String nomenclature;

    public SegmentResource(){

    }

    public SegmentResource(String id, String link, String segmentNumber, double length, String nomenclature) {
        this.id = id;
        this.link = link;
        this.segmentNumber = segmentNumber;
        this.length = length;
        this.nomenclature = nomenclature;
    }

    public SegmentResource(SegmentData segmentData, String link){
        this.id = segmentData.id.toString();
        this.link = link;
        this.segmentNumber = segmentData.segmentNumber;
        this.length = segmentData.length;
        this.nomenclature = segmentData.nomenclature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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