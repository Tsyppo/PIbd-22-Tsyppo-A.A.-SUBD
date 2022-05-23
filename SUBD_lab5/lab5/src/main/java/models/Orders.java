package models;


import com.sun.istack.NotNull;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="orders_id_seq")
    @Column(name = "id")
    private int id;
    @NotNull
    @Column(name = "quantity_product")
    private int quantity_product;
    @NotNull
    @Column(name = "date")
    private Date date;
    @NotNull
    @Column(name = "cost_order")
    private int cost_order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Orders() {
    }

    public Orders(Product product, int quantity_product, Date date, int cost_order) {
        this.quantity_product = quantity_product;
        this.date = date;
        this.cost_order = cost_order;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public int getQuantity_product() {
        return quantity_product;
    }
    public void setQuantity_product(int quantity_product) {
        this.quantity_product = quantity_product;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public int getCost_order() {
        return cost_order;
    }
    public void setCost_order(int cost_order) {
        this.cost_order = cost_order;
    }

    public Product getProduct(){return product;}
    public void setProduct(Product product){this.product = product;}

    @Override
    public String toString(){ return String.format("%-30d%-30d%-30tF%-30d%-30d", id, quantity_product,
            date, cost_order, product.getId()); }
}
