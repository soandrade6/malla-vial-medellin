package v1.roadway;

public class RoadWayResource {

    private String id;
    private String link;
    public double width;
    public Long segment_id;

    public RoadWayResource() {
    }

    public RoadWayResource(String id, String link, double width, Long segment_id) {
        this.id = id;
        this.link = link;
        this.width = width;
        this.segment_id = segment_id;

    }

    public RoadWayResource(RoadWayData roadWayData, String link){
        this.id = roadWayData.id.toString();
        this.link = link;
        this.width = roadWayData.width;
        this.segment_id = roadWayData.getSegment().getId();

    }

    public RoadWayResource(RoadWayData data) {
        this.id = data.getId().toString();
        this.width = data.getWidth();
        this.segment_id = data.getSegment().getId(); // Asumiendo que segmentId es String
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Long getSegment_id() {
        return segment_id;
    }

    public void setSegment_id(Long segment_id) {
        this.segment_id = segment_id;
    }
}
