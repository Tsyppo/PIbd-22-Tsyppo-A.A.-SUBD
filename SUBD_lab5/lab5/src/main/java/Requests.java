import models.Material;
import models.Orders;
import models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class Requests {
    public void work(SessionFactory sessionFactory){
        System.out.println("1.Первый запрос");
        System.out.println("2.Второй запрос");
        System.out.println("3.Третий запрос");
        System.out.println("4.Вернуться в меню");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        Session session;
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        switch (i) {
             case 1 -> firstRequest(session);
             case 2 -> secondRequest(session);
             case 3 -> thirdRequest(session);
             case 4 -> {
                 session.close();
                 return;
             }
             default -> System.out.println("Неверный ввод");
        }
        session.getTransaction().commit();
    }

    private void firstRequest(Session session) {
        List<Product> products = session.createQuery("SELECT p FROM Product p",
                Product.class).getResultList();
        System.out.printf("%-25s%-25s\n", "Имя работника", "Стоимость");
        for (Product product : products)
            System.out.printf("%-25s%-20d%-20s\n", product.getWorker().getName(),
                    product.getCost(), product.getType());
    }

    private void secondRequest(Session session){
        List<Material> materialList = session.createQuery("SELECT m FROM Material m", Material.class).getResultList();
        System.out.printf("%-25s%-25s\n", "Название", "Возраст");
        for (Material material : materialList)
            if (material.getId()>1 && material.getId()<4)
            System.out.printf("%-25d%-25s \n",material.getId(),material.getName());
    }

    private void thirdRequest(Session session){
        List<Orders> ordersList = session.createQuery("SELECT o FROM Orders o", Orders.class).getResultList();
        System.out.printf("%-25s%-25s \n", "Поставка", "Дата");
        for (Orders orders : ordersList)
            if(orders.getDate().after(java.sql.Date.valueOf("2020-05-05")))
                System.out.printf("%-25s%-25tF%-25d \n", orders.getProduct().getType(), orders.getDate(), orders.getCost_order());
    }
}
