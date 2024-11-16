package kg.alfit;

import kg.alfit.homework.RestaurantOrders;
import kg.alfit.homework.domain.Order;
import kg.alfit.homework.util.GenerateOrders;

import java.util.List;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

// используя статические imports
// мы импортируем *всё* из Collectors и Comparator.
// теперь нам доступны такие операции как
// toList(), toSet() и прочие, без указания уточняющего слова Collectors. или Comparator.
// вот так было до импорта Collectors.toList(), теперь стало просто toList()


public class Main {
    private static int INSTEAD_OF_N = 3;

    public static void main(String[] args) {

        // это для домашки
        // выберите любое количество заказов, какое вам нравится.
        var orders = RestaurantOrders.read("orders_100.json");
        orders.getOrders();
        orders.printOrders();


        var expensiveOrders = orders.moreExpensiveOrders(INSTEAD_OF_N);
        expensiveOrders.forEach(e -> System.out.println(e.getCustomer().getFullName()));
        var cheaper = orders.moreCheaperOrders(INSTEAD_OF_N);
        cheaper.forEach(e -> System.out.println(e.getCustomer().getFullName()));

        var haveDelivery = orders.printHaveDelivery();
        haveDelivery.forEach(e -> System.out.println(e.getCustomer().getFullName()));


        System.out.println("наиболее прибыльные с заказов на дом");
        haveDelivery.stream()
                .reduce((maxOrder, currentOrder) ->
                        currentOrder.getTotal() > maxOrder.getTotal() ? currentOrder : maxOrder
                );
        haveDelivery.forEach(e -> System.out.println(e.getCustomer().getFullName()));


        System.out.println("наименее прибыльные с заказов на дом");
        haveDelivery.stream()
                .reduce((minOrder, currentOrder) ->
                        currentOrder.getTotal() < minOrder.getTotal() ? currentOrder : minOrder
                );
        haveDelivery.forEach(e -> System.out.println(e.getCustomer().getFullName()));



        //var orders = RestaurantOrders.read("orders_10_000.json").getOrders();
        //var orders1 = RestaurantOrders.read("orders_1000.json").getOrders();
        List<Order> ordersInRange = orders.getOrdersWithinRange(50.3, 200.0);
        System.out.println("Диапазон от 50 - 200.0 ");
        ordersInRange.forEach(order -> System.out.println("Order total: " + order.getTotal()));

        double totalCost = orders.calculateTotalCost();
        System.out.println("Total cost " + totalCost);


        //В разработке
        List<String> uniqueEmails = orders.getUniqueSortedEmails();
        System.out.println("Unique sorted email addresses:");
        uniqueEmails.forEach(System.out::println);

    }
}
