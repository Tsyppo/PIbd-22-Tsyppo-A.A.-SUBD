package logics;

import models.Orders;
import models.Product;
import models.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OrdersLogic {
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
        System.out.print("Введите ID продукта:");
        int product_id = scanner.nextInt();
        System.out.print("Введите количество продукта:");
        int quantity_product = scanner.nextInt();
        System.out.print("Введите дату заказа:");
        Date date = java.sql.Date.valueOf(scanner.next());
        System.out.print("Введите стоимость заказа:");
        int cost_order = scanner.nextInt();
        try {
            Orders orders = new Orders(session.get(Product.class, product_id), quantity_product, date, cost_order);
            session.save(orders);
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
                List<Orders> ordersList = session.createQuery("SELECT p FROM Orders p",
                        Orders.class).getResultList();
                System.out.println("Заказы");
                System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID","Количество продуктов",
                        "Дата заказа", "Стоимость заказа", "ID продукта");
                ordersList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }
    }

    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID заказа:");
        int order_id = scanner.nextInt();
        System.out.println("Что вы хотите обновить?");
        System.out.println("1.Количество продукта");
        System.out.println("2.Дата заказа");
        System.out.println("3.Стоимость заказа");
        System.out.println("4.ID продукта");
        System.out.print(">");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                System.out.print("Введите количество продукта:");
                scanner.nextLine();
                int quantity_product = scanner.nextInt();
                try {
                    Orders orders = session.get(Orders.class, order_id);
                    orders.setQuantity_product(quantity_product);
                    session.save(orders);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите дату заказа:");
                scanner.nextLine();
                Date date = java.sql.Date.valueOf(scanner.next());
                try {
                    Orders orders = session.get(Orders.class, order_id);
                    orders.setDate(date);
                    session.save(orders);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите стоимость заказа:");
                scanner.nextLine();
                int cost_order = scanner.nextInt();
                try {
                    Orders orders = session.get(Orders.class, order_id);
                    orders.setCost_order(cost_order);
                    session.save(orders);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите ID продукта:");
                scanner.nextLine();
                int product_id = scanner.nextInt();
                try {
                    Orders orders = session.get(Orders.class, order_id);
                    orders.setProduct(session.get(Product.class, product_id));
                    session.save(orders);
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
        System.out.print("Введите ID заказа:");
        int order_id = scanner.nextInt();
        try {
            Orders orders = session.get(Orders.class, order_id);
            session.delete(orders);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.ID заказа");
        System.out.println("2.Количество продукта");
        System.out.println("3.Дата заказа");
        System.out.println("4.Стоимость заказа");
        System.out.println("5.ID продукта");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Orders> ordersList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID заказа:");
                scanner.nextLine();
                int id = scanner.nextInt();
                try {
                    ordersList = session.createQuery("SELECT p FROM Orders p WHERE id =" + id,
                            Orders.class).getResultList();
                    System.out.println("Заказы");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID","Количество продуктов",
                            "Дата заказа", "Стоимость заказа", "ID продукта");
                    ordersList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите количество продукта:");
                scanner.nextLine();
                int quantity_product = scanner.nextInt();
                try {
                    ordersList = session.createQuery("SELECT p FROM Orders p WHERE quantity_product =" + quantity_product,
                            Orders.class).getResultList();
                    System.out.println("Заказы");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID","Количество продуктов",
                            "Дата заказа", "Стоимость заказа", "ID продукта");
                    ordersList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите дату заказа:");
                scanner.nextLine();
                Date date = java.sql.Date.valueOf(scanner.next());
                try {
                    ordersList = session.createQuery("SELECT p FROM Orders p WHERE date ='" + date + "'",
                            Orders.class).getResultList();
                    System.out.println("Заказы");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID","Количество продуктов",
                            "Дата заказа", "Стоимость заказа", "ID продукта");
                    ordersList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите стоимость заказа:");
                scanner.nextLine();
                int cost_order = scanner.nextInt();
                try {
                    ordersList = session.createQuery("SELECT p FROM Orders p WHERE cost_order =" + cost_order,
                            Orders.class).getResultList();
                    System.out.println("Заказы");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID","Количество продуктов",
                            "Дата заказа", "Стоимость заказа", "ID продукта");
                    ordersList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 5 -> {
                System.out.print("Введите ID продукта:");
                scanner.nextLine();
                int product_id = scanner.nextInt();
                try {
                    ordersList = session.createQuery("SELECT p FROM Orders p WHERE product_id =" + product_id,
                            Orders.class).getResultList();
                    System.out.println("Заказы");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID","Количество продуктов",
                            "Дата заказа", "Стоимость заказа", "ID продукта");
                    ordersList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }
}
