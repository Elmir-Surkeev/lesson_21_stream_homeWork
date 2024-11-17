package kg.alfit;

import kg.alfit.homework.RestaurantOrders;
import kg.alfit.homework.domain.Order;
// используя статические imports
// мы импортируем *всё* из Collectors и Comparator.
// теперь нам доступны такие операции как
// toList(), toSet() и прочие, без указания уточняющего слова Collectors. или Comparator.
// вот так было до импорта Collectors.toList(), теперь стало просто toList()

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static int INSTEAD_OF_N = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Загружаем заказы
        var orders = RestaurantOrders.read("orders_100.json");
        //var orders = RestaurantOrders.read("orders_10_000.json").getOrders();
        //var orders1 = RestaurantOrders.read("orders_1000.json").getOrders();

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Вывести список всех заказов");
            System.out.println("2. Найти " + INSTEAD_OF_N + " самых дорогих заказов");
            System.out.println("3. Найти " + INSTEAD_OF_N + " самых дешёвых заказов");
            System.out.println("4. Вывести заказы на дом");
            System.out.println("5. Найти самый прибыльный заказ на дом");
            System.out.println("6. Найти наименее прибыльный заказ на дом");
            System.out.println("7. Найти заказы в диапазоне стоимости");
            System.out.println("8. Рассчитать общую стоимость всех заказов");
            System.out.println("9. Вывести список уникальных и отсортированных email адресов");
            System.out.println("10. Сгруппировать заказы по имени клиентов");
            System.out.println("11. Найти клиента с максимальной суммой заказов");
            System.out.println("12. Найти клиента с минимальной суммой заказов");
            System.out.println("13. Вывести статистику проданных товаров");
            System.out.println("0. Выйти");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    orders.printOrders();
                    break;
                case 2:
                    var expensiveOrders = orders.moreExpensiveOrders(INSTEAD_OF_N);
                    expensiveOrders.forEach(e -> System.out.println(e.getCustomer().getFullName() + ": " + e.getTotal()));
                    break;
                case 3:
                    var cheaperOrders = orders.moreCheaperOrders(INSTEAD_OF_N);
                    cheaperOrders.forEach(e -> System.out.println(e.getCustomer().getFullName() + ": " + e.getTotal()));
                    break;
                case 4:
                    var deliveryOrders = orders.printHaveDelivery();
                    deliveryOrders.forEach(e -> System.out.println(e.getCustomer().getFullName()));
                    break;
                case 5:
                    var maxOrder = orders.printHaveDelivery()
                            .stream()
                            .reduce((max, current) -> current.getTotal() > max.getTotal() ? current : max);
                    maxOrder.ifPresent(order -> System.out.println("Самый прибыльный заказ на дом: " + order.getCustomer().getFullName() + " - " + order.getTotal()));
                    break;
                case 6:
                    var minOrder = orders.printHaveDelivery()
                            .stream()
                            .reduce((min, current) -> current.getTotal() < min.getTotal() ? current : min);
                    minOrder.ifPresent(order -> System.out.println("Наименее прибыльный заказ на дом: " + order.getCustomer().getFullName() + " - " + order.getTotal()));
                    break;
                case 7:
                    System.out.println("Введите минимальную сумму заказа:");
                    double minTotal = scanner.nextDouble();
                    System.out.println("Введите максимальную сумму заказа:");
                    double maxTotal = scanner.nextDouble();
                    List<Order> ordersInRange = orders.getOrdersWithinRange(minTotal, maxTotal);
                    ordersInRange.forEach(order -> System.out.println(order.getCustomer().getFullName() + ": " + order.getTotal()));
                    break;
                case 8:
                    double totalCost = orders.calculateTotalCost();
                    System.out.println("Общая стоимость всех заказов: " + totalCost);
                    break;
                case 9:
                    List<String> uniqueEmails = orders.getUniqueSortedEmails();
                    uniqueEmails.forEach(System.out::println);
                    break;
                case 10:
                    Map<String, List<Order>> groupedOrders = orders.getOrdersGroupedByCustomerName();
                    groupedOrders.forEach((name, orderList) -> {
                        System.out.println(name + ":");
                        orderList.forEach(order -> System.out.println("  - " + order.getTotal()));
                    });
                    break;
                case 11:
                    String maxCustomer = orders.getCustomerWithMaxTotalOrders();
                    System.out.println("Клиент с максимальной суммой заказов: " + maxCustomer);
                    break;
                case 12:
                    String minCustomer = orders.getCustomerWithMinTotalOrders();
                    System.out.println("Клиент с минимальной суммой заказов: " + minCustomer);
                    break;
                case 13:
                    Map<String, Long> productQuantities = orders.getProductsGroupedByQuantity();
                    productQuantities.forEach((product, quantity) -> System.out.println(product + ": " + quantity));
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
