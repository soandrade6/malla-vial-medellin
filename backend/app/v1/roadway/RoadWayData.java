package v1.roadway;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import v1.segment.SegmentData;

@Entity
@Table(name = "roadway")
public class RoadWayData {

    public RoadWayData() {
    }

    public RoadWayData( double width, SegmentData segment) {
        this.width = width;
        this.segment = segment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public double width;

    @ManyToOne
    @JoinColumn(name = "segment_id")
    @JsonBackReference
    private SegmentData segment;

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

    public SegmentData getSegment() {
        return segment;
    }

    public void setSegment(SegmentData segment) {
        this.segment = segment;
    }


}
