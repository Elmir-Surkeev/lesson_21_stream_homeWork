package kg.alfit.homework;

import com.google.gson.Gson;
import kg.alfit.homework.domain.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
    // Наполните этот класс решением домашнего задания.
    // Вам необходимо создать все необходимые методы
    // для решения заданий из домашки :)
    // вы можете добавлять все необходимые imports
    //
}
