package kg.alfit;

import kg.alfit.homework.RestaurantOrders;
import kg.alfit.homework.domain.Order;
import kg.alfit.homework.util.GenerateOrders;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

// используя статические imports
// мы импортируем *всё* из Collectors и Comparator.
// теперь нам доступны такие операции как
// toList(), toSet() и прочие, без указания уточняющего слова Collectors. или Comparator.
// вот так было до импорта Collectors.toList(), теперь стало просто toList()


public class Main {

    public static void main(String[] args) {

        // это для домашки
        // выберите любое количество заказов, какое вам нравится.
        var orders = RestaurantOrders.read("orders_100.json");
        orders.getOrders();
        orders.printOrders();


        //var orders = RestaurantOrders.read("orders_1000.json").getOrders();
        //var orders = RestaurantOrders.read("orders_10_000.json").getOrders();

        // протестировать ваши методы вы можете как раз в этом файле (или в любом другом, в котором вам будет удобно)
    }
}
