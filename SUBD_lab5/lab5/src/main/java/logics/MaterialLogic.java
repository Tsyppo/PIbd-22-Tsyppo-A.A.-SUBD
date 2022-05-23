package logics;

import models.Material;
import models.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class MaterialLogic {
    public void work(SessionFactory sessionFactory){
        System.out.println("1.Создать");
        System.out.println("2.Прочитать");
        System.out.println("3.Обновить");
        System.out.println("4.Удалить");
        System.out.println("5.Вернуться в меню");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        Session session;
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        switch (i) {
            case 1 -> create(session);
            case 2 -> read(session);
            case 3 -> update(session);
            case 4 -> delete(session);
            case 5 -> {
                session.close();
                return;
            }
            default -> System.out.println("Неверный ввод");
        }
        session.getTransaction().commit();
    }

    private void create(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите название материала:");
        String name = scanner.nextLine();
        System.out.print("Введите количество материала:");
        int quantity = scanner.nextInt();
        System.out.print("Введите единицу измерения материала:");
        String unit_measurements = scanner.nextLine();
        try {
            Material material = new Material(name, quantity, unit_measurements);
            session.save(material);
        }
        catch (Exception ex){
            System.out.println("Ошибка создания записи");
        }
    }

    private void read(Session session){
        System.out.println("1.Вывести по фильтру");
        System.out.println("2.Вывести всех");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        switch (i){
            case 1-> filter(session);
            case 2-> {
                List<Material> materialList = session.createQuery("SELECT t FROM Material t",
                        Material.class).getResultList();
                System.out.println("Материал");
                System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Название материала",
                        "Количество материала", "Единица измерения");
                materialList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }
    }

    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID материала:");
        int material_id = scanner.nextInt();
        System.out.println("Что вы хотите обновить?");
        System.out.println("1.Название материала");
        System.out.println("2.Количество материала");
        System.out.println("3.Единица измерения материала");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                System.out.print("Введите название материала:");
                scanner.nextLine();
                String material_name = scanner.nextLine();
                try {
                    Material material = session.get(Material.class, material_id);
                    material.setName(material_name);
                    session.save(material);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите количество материала:");
                scanner.nextLine();
                int quantity = scanner.nextInt();
                try {
                    Material material = session.get(Material.class, material_id);
                    material.setQuantity(quantity);
                    session.save(material);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите единицу измерения материала:");
                scanner.nextLine();
                String unit_measurements = scanner.nextLine();
                try {
                    Material material = session.get(Material.class, material_id);
                    material.setUnit_measurements(unit_measurements);
                    session.save(material);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }

    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID материала:");
        int material_id = scanner.nextInt();
        try {
            Material material = session.get(Material.class, material_id);
            session.delete(material);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.ID материала");
        System.out.println("2.Название материала");
        System.out.println("3.Количество материала");
        System.out.println("4.Единица измерения материала");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Material> materialList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID материала:");
                scanner.nextLine();
                int id = scanner.nextInt();
                try {
                    materialList = session.createQuery("SELECT c FROM Material c WHERE id =" + id,
                            Material.class).getResultList();
                    System.out.println("Материал");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Название материала",
                            "Количество материала", "Единица измерения");
                    materialList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите название:");
                scanner.nextLine();
                String name = scanner.next();
                try {
                    materialList = session.createQuery("SELECT c FROM Material c WHERE name ='" + name + "'",
                            Material.class).getResultList();
                    System.out.println("Материал");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Название материала",
                            "Количество материала", "Единица измерения");
                    materialList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите количество материала:");
                scanner.nextLine();
                int quantity = scanner.nextInt();
                try {
                    materialList = session.createQuery("SELECT c FROM Material c WHERE quantity =" + quantity,
                            Material.class).getResultList();
                    System.out.println("Материал");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Название материала",
                            "Количество материала", "Единица измерения");
                    materialList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите единицу измерения материала:");
                scanner.nextLine();
                String unit_measurements = scanner.next();
                try {
                    materialList = session.createQuery("SELECT c FROM Material c WHERE unit_measurements ='" + unit_measurements + "'",
                            Material.class).getResultList();
                    System.out.println("Материал");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Название материала",
                            "Количество материала", "Единица измерения");
                    materialList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }
}
