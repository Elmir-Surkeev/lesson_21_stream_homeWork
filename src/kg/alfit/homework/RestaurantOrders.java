package kg.alfit.homework;

import com.google.gson.Gson;
import kg.alfit.homework.domain.Item;
import kg.alfit.homework.domain.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RestaurantOrders {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private List<Order> orders;

    private RestaurantOrders(String fileName) {
        var filePath = Path.of("data", fileName);
        Gson gson = new Gson();
        try {
            orders = List.of(gson.fromJson(Files.readString(filePath), Order[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RestaurantOrders read(String fileName) {
        var ro = new RestaurantOrders(fileName);
        ro.getOrders().forEach(Order::calculateTotal);
        return ro;
    }

    public List<Order> getOrders() {
        return orders;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!

    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------
    public void printOrders(){
        orders.forEach(order -> {
                    System.out.println("Customer: " + order.getCustomer().getFullName());
                    System.out.println("Email: " + order.getCustomer().getEmail());
                    System.out.println("Home Delivery: " + (order.isHomeDelivery() ? "Yes" : "No"));
                    System.out.println("Total: $" + order.getTotal());
                    System.out.println("Items:");
                    order.getItems().forEach(item -> {
                        System.out.println("-" + item.getName() + item.getAmount() + "price: " + item.getPrice());
                    });
                    System.out.println("______________________________________");
        });
    }

    public List<Order> moreExpensiveOrders(int n){
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }


    public List<Order> moreCheaperOrders(int n){
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Order> printHaveDelivery(){
        return orders.stream()
                .filter(order -> order.isHomeDelivery())
                .toList();
    }


    public List<Order> getOrdersWithinRange(double minOrderTotal, double maxOrderTotal) {
        return orders.stream()
                .filter(order -> order.getTotal() > minOrderTotal && order.getTotal() < maxOrderTotal)
                .collect(Collectors.toList());
    }

    public double calculateTotalCost() {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(Item::getPrice)
                .sum();
    }


    public List<String> getUniqueSortedEmails() {
        Set<String> emailSet = new HashSet<>();
        List<String> sortedEmails = new ArrayList<>();

        for (Order order : orders) {
            String email = order.getCustomer().getEmail();
            if (emailSet.add(email)) {
                int insertionPoint = 0;
                while (insertionPoint < sortedEmails.size() && sortedEmails.get(insertionPoint).compareTo(email) < 0) {
                    insertionPoint++;
                }
                sortedEmails.add(insertionPoint, email);
            }
        }

        return sortedEmails;
    }
    public Map<String, List<Order>> getOrdersGroupedByCustomerName() {
        return orders.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer().getFullName()));
    }

    public Map<String, Double> getTotalOrdersByCustomer() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCustomer().getFullName(),
                        Collectors.summingDouble(Order::getTotal)
                ));
    }

    public String getCustomerWithMaxTotalOrders() {
        return getTotalOrdersByCustomer().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public String getCustomerWithMinTotalOrders() {
        return getTotalOrdersByCustomer().entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Map<String, Long> getProductsGroupedByQuantity() {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        Item::getName,
                        Collectors.summingLong(Item::getAmount)
                ));
    }




    // Наполните этот класс решением домашнего задания.
    // Вам необходимо создать все необходимые методы
    // для решения заданий из домашки :)
    // вы можете добавлять все необходимые imports
    //
}
