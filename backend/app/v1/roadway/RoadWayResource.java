package v1.roadway;

public class RoadWayResource {

    private String id;
    private String link;
    public double width;

    public RoadWayResource() {
    }

    public RoadWayResource(String id, String link, double width) {
        this.id = id;
        this.link = link;
        this.width = width;
    }

    public RoadWayResource(RoadWayData roadWayData, String link){
        this.id = roadWayData.id.toString();
        this.link = link;
        this.width = roadWayData.width;
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
}
