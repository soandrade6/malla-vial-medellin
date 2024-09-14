package v1.roadway;

public class RoadWayResource {

    private Long id;
    private double width;
    private  Long segment_id;

    public RoadWayResource() {
    }

    public RoadWayResource(Long id, double width, Long segment_id) {
        this.id = id;
        this.width = width;
        this.segment_id = segment_id;

    }

    public RoadWayResource(RoadWayData data) {
        this.id = data.getId();
        this.width = data.getWidth();
        this.segment_id = data.getSegment().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
