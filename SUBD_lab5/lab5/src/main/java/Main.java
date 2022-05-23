import models.*;
import logics.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    public static void main(String[] arg) {
        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(Material.class)
                .addAnnotatedClass(Orders.class)
                .addAnnotatedClass(Worker.class)
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();
    boolean working = true;
        while (working) {
        System.out.println("\n\t\tМеню");
        System.out.println("1.Материал");
        System.out.println("2.Заказы");
        System.out.println("3.Работник");
        System.out.println("4.Продукт");
        System.out.println("5.Запросы");
        System.out.println("6.Выход");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                MaterialLogic materialLogic = new MaterialLogic();
                materialLogic.work(sessionFactory);
            }
            case 2 -> {
                OrdersLogic ordersLogic = new OrdersLogic();
                ordersLogic.work(sessionFactory);
            }
            case 3 -> {
                WorkerLogic workerLogic = new WorkerLogic();
                workerLogic.work(sessionFactory);
            }
            case 4 -> {
                ProductLogic productLogic = new ProductLogic();
                productLogic.work(sessionFactory);
            }
            case 5 -> {
                Requests requests = new Requests();
                requests.work(sessionFactory);
            }
            case 6 -> {
                System.out.println("Exit...");
                working = false;
            }
            default -> System.out.println("Invalid input");
        }
        }
        sessionFactory.close();
    }
}
