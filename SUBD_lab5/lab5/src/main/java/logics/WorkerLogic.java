package logics;

import models.Product;
import models.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class WorkerLogic {
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
        System.out.print("Введите имя работника:");
        String worker_name = scanner.nextLine();
        System.out.print("Введите фамилию работника:");
        String worker_surname = scanner.nextLine();
        System.out.print("Введите отчество работника:");
        String worker_patronymic = scanner.nextLine();
        System.out.print("Введите номер телефона:");
        long phone_number = scanner.nextLong();
        try {
            Worker worker = new Worker(worker_name, worker_surname,
                                       worker_patronymic, phone_number);
            session.save(worker);
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
                List<Worker> workerList = session.createQuery("SELECT w FROM Worker w",
                        Worker.class).getResultList();
                System.out.println("Работник");
                System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID",
                                  "Имя", "Фамилия", "Отчетсво", "Номер телефона");
                workerList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }
    }

    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID работника:");
        int worker_id = scanner.nextInt();
        System.out.println("Что вы хотите обновить?");
        System.out.println("1.Имя");
        System.out.println("2.Фамилию");
        System.out.println("3.Отчество");
        System.out.println("4.Номер телефона");
        System.out.print(">");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                System.out.print("Введите имя:");
                scanner.nextLine();
                String worker_name = scanner.nextLine();
                try {
                    Worker worker = session.get(Worker.class, worker_id);
                    worker.setName(worker_name);
                    session.save(worker);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите фамилию:");
                scanner.nextLine();
                String worker_surname = scanner.nextLine();
                try {
                    Worker worker = session.get(Worker.class, worker_id);
                    worker.setSurname(worker_surname);
                    session.save(worker);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите отчетсво:");
                scanner.nextLine();
                String worker_patronymic = scanner.nextLine();
                try {
                    Worker worker = session.get(Worker.class, worker_id);
                    worker.setPatronymic(worker_patronymic);
                    session.save(worker);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите номер телефона:");
                scanner.nextLine();
                long phone_number = scanner.nextInt();
                try {
                    Worker worker = session.get(Worker.class, worker_id);
                    worker.setPhone_number(phone_number);
                    session.save(worker);
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
        System.out.print("Введите ID работника:");
        int worker_id = scanner.nextInt();
        try {
            Worker worker = session.get(Worker.class, worker_id);
            session.delete(worker);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.Id работника");
        System.out.println("2.Имя");
        System.out.println("3.Фамилию");
        System.out.println("4.Отчество");
        System.out.println("5.Номер телефона");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Worker> workerList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID работника:");
                scanner.nextLine();
                int id = scanner.nextInt();
                try {
                    workerList = session.createQuery("SELECT w FROM Worker w WHERE id =" + id,
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID",
                            "Имя", "Фамилия", "Отчетсво", "Номер телефона");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите имя работника:");
                scanner.nextLine();
                String name = scanner.nextLine();
                try {
                    workerList = session.createQuery("SELECT p FROM Worker p WHERE name ='" + name + "'",
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID",
                            "Имя", "Фамилия", "Отчетсво", "Номер телефона");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите фамилию работника:");
                scanner.nextLine();
                String surname = scanner.nextLine();
                try {
                    workerList = session.createQuery("SELECT p FROM Worker p WHERE surname ='" + surname + "'",
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID",
                            "Имя", "Фамилия", "Отчетсво", "Номер телефона");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите отчество работника:");
                scanner.nextLine();
                String patronymic = scanner.nextLine();
                try {
                    workerList = session.createQuery("SELECT p FROM Worker p WHERE patronymic ='" + patronymic + "'",
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID",
                            "Имя", "Фамилия", "Отчетсво", "Номер телефона");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 5 -> {
                System.out.print("Введите номер телефона работника:");
                scanner.nextLine();
                long phone_number = scanner.nextLong();
                try {
                    workerList = session.createQuery("SELECT p FROM Worker p WHERE phone_number =" + phone_number,
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","ID",
                            "Имя", "Фамилия", "Отчетсво", "Номер телефона");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }
}
