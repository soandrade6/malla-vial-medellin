package v1.curb;

public class CurbResource {

    private Long id;
    private double height;
    private  Long segment_id;

    public CurbResource() {
    }

    public CurbResource(Long id, double height, Long segment_id) {
        this.id = id;
        this.height = height;
        this.segment_id = segment_id;
    }

    public CurbResource(CurbData curbData, String link){
        this.id = curbData.getId();
        this.height = curbData.getHeight();
        this.segment_id = curbData.getSegment().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Long getSegment_id() {
        return segment_id;
    }

    public void setSegment_id(Long segment_id) {
        this.segment_id = segment_id;
    }
}
