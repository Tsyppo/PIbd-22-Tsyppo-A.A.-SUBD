package logics;

import models.Product;
import models.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class ProductLogic {
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
        System.out.print("Введите ID работника:");
        int worker_id = scanner.nextInt();
        System.out.print("Введите тип продукта:");
        scanner.nextLine();
        String product_type = scanner.nextLine();
        System.out.print("Введите количество продукции:");
        int quantity = scanner.nextInt();
        System.out.print("Введите стоимость продукта:");
        int product_cost = scanner.nextInt();
        try {
            Product product = new Product(session.get(Worker.class, worker_id), product_type, quantity, product_cost);
            session.save(product);
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
                List<Product> productList = session.createQuery("SELECT f FROM Product f",
                        Product.class).getResultList();
                System.out.println("Продукт");
                System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID", "Тип","Количество",
                        "Стоимость", "ID работника");
                productList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }
    }

    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID аккаунта:");
        int product_id = scanner.nextInt();
        System.out.println("Что вы хотите обновить?");
        System.out.println("1.ID работника");
        System.out.println("2.Тип продукта");
        System.out.println("3.Количетсво продукта");
        System.out.println("4.Стоимость продукта");
        System.out.print(">");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID работника:");
                scanner.nextLine();
                int worker_id = scanner.nextInt();
                try {
                    Product product = session.get(Product.class, product_id);
                    product.setWorker(session.get(Worker.class, worker_id));
                    session.save(product);
                    session.getTransaction().commit();
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите тип продукта:");
                scanner.nextLine();
                String product_type = scanner.nextLine();
                try {
                    Product product = session.get(Product.class, product_id);
                    product.setType(product_type);
                    session.save(product);
                    session.getTransaction().commit();
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите количество продукта:");
                scanner.nextLine();
                int product_quantity = scanner.nextInt();
                try {
                    Product product = session.get(Product.class, product_id);
                    product.setQuantity(product_quantity);
                    session.save(product);
                    session.getTransaction().commit();
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите стоимость продукта:");
                scanner.nextLine();
                int product_cost = scanner.nextInt();
                try {
                    Product product = session.get(Product.class, product_id);
                    product.setCost(product_cost);
                    session.save(product);
                    session.getTransaction().commit();
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
        System.out.print("Введите ID продукта:");
        int account_id = scanner.nextInt();
        try {
            Product product = session.get(Product.class, account_id);
            session.delete(product);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.ID продукта");
        System.out.println("2.ID работника");
        System.out.println("3.Тип продукта");
        System.out.println("4.Количество продукта");
        System.out.println("5.Стоимость продукта");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Product> productList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID продукта:");
                scanner.nextLine();
                int id = scanner.nextInt();
                try {
                    productList = session.createQuery("SELECT f FROM Product f WHERE id =" + id,
                            Product.class).getResultList();
                    System.out.println("Продукт");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID", "Тип","Количество",
                            "Стоимость", "ID работника");
                    productList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите ID работника:");
                scanner.nextLine();
                int worker_id = scanner.nextInt();
                try {
                    productList = session.createQuery("SELECT f FROM Product f WHERE worker_id =" + worker_id,
                            Product.class).getResultList();
                    System.out.println("Продукт");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID", "Тип","Количество",
                            "Стоимость", "ID работника");
                    productList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите тип:");
                scanner.nextLine();
                String type = scanner.nextLine();
                try {
                    productList = session.createQuery("SELECT f FROM Product f WHERE type ='" + type + "'",
                            Product.class).getResultList();
                    System.out.println("Продукт");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID", "Тип","Количество",
                            "Стоимость", "ID работника");
                    productList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите количество продукта:");
                scanner.nextLine();
                int quantity = scanner.nextInt();
                try {
                    productList = session.createQuery("SELECT f FROM Product f WHERE quantity =" + quantity,
                            Product.class).getResultList();
                    System.out.println("Продукт");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID", "Тип","Количество",
                            "Стоимость", "ID работника");
                    productList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 5 -> {
                System.out.print("Введите стоимость продукта:");
                scanner.nextLine();
                int cost = scanner.nextInt();
                try {
                    productList = session.createQuery("SELECT f FROM Product f WHERE cost =" + cost,
                            Product.class).getResultList();
                    System.out.println("Продукт");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID", "Тип","Количество",
                            "Стоимость", "ID работника");
                    productList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }
}
