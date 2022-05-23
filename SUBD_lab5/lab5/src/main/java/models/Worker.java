package models;

import com.sun.istack.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "worker")
public class Worker {
    @Id
    @SequenceGenerator(name= "worker_id_seq", sequenceName="worker_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "worker_id_seq")
    @Column(name = "id")
    private int id;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "surname")
    private String surname;
    @NotNull
    @Column(name = "patronymic")
    private String patronymic;
    @NotNull
    @Column(name = "phone_number")
    private long phone_number;

    public Worker() {

    }
    public Worker(String name, String surname,  String patronymic, long phone_number) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phone_number = phone_number;
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

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public long getPhone_number () {
        return phone_number;
    }
    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString(){ return String.format("%-30d%-30s%-30s%-30s%-30d",
            id, name, surname, patronymic, phone_number); }
}
