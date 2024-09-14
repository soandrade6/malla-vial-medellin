package v1.curb;

import jakarta.persistence.*;

@Entity
@Table(name = "curb")
public class CurbData {

    public CurbData() {
    }

    public CurbData(Long id, double height) {
        this.id = id;
        this.height = height;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double height;

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
}
