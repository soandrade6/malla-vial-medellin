package v1.roadway;

import jakarta.persistence.*;

@Entity
@Table(name = "roadway")
public class RoadWayData {

    public RoadWayData() {
    }

    public RoadWayData( double width) {
        this.width = width;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public double width;

}
