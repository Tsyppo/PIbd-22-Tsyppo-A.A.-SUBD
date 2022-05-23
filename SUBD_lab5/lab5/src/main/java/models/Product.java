package models;


import com.sun.istack.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="product_id_seq")
    @Column(name = "id")
    private int id;
    @NotNull
    @Column(name = "type")
    private String type;
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @NotNull
    @Column(name = "cost")
    private int cost;
    @OneToMany
    private List<Orders> orders;
    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "product_material",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<Material> materials = new ArrayList<>();

    public Product() {
    }

    public Product(Worker worker, String type, int quantity, int cost) {
        this.worker = worker;
        this.type = type;
        this.quantity = quantity;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public Worker getWorker() {
        return worker;
    }
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public String toString(){ return String.format("%-30d%-30s%-30d%-30d%-30d", id, type, quantity, cost, worker.getId()); }
}
