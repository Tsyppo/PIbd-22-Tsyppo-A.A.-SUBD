package models;


import com.sun.istack.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "material")
public class Material {
    @Id
    @SequenceGenerator(name = "material_id_seq", sequenceName = "material_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="material_id_seq")
    @Column(name = "id")
    private int id;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @NotNull
    @Column(name = "unit_measurements")
    private String unit_measurements;

    @ManyToMany(mappedBy = "materials")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> products = new ArrayList<>();

    public Material() {
    }

    public Material(String name, int quantity, String unit_measurements) {
        this.name = name;
        this.quantity = quantity;
        this.unit_measurements = unit_measurements;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit_measurements() {
        return unit_measurements;
    }
    public void setUnit_measurements(String unit_measurements) {
        this.unit_measurements = unit_measurements;
    }

    @Override
    public String toString(){ return String.format("%-30d%-30s%-30d%-30s", id, name, quantity, unit_measurements);}
}
