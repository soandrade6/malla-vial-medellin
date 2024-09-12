package v1.segment;

import jakarta.persistence.*;

@Entity
@Table(name = "segment")
public class Segment {

    public Segment() {
    }

    public Segment(String segmentNumber, double length, String nomenclature){
        this.segmentNumber = segmentNumber;
        this.length = length;
        this.nomenclature = nomenclature;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String segmentNumber;
    public double length;
    public String nomenclature;
}
