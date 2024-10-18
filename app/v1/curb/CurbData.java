package v1.curb;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import v1.segment.SegmentData;

@Entity
@Table(name = "curb")
public class CurbData {

    public CurbData() {
    }

    public CurbData(double height, SegmentData segment) {
        this.height = height;
        this.segment = segment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double height;

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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public SegmentData getSegment() {
        return segment;
    }

    public void setSegment(SegmentData segment) {
        this.segment = segment;
    }
}
