package machine;
import java.util.*;

public class CoffeeMachine {
    // state of machine
    private static int water = 400;
    private static int milk = 540;
    private static int coffeeBeans = 120;
    private static int disposableCups = 9;
    private static int money = 550;
    private static boolean buyOn = true;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (buyOn) {
            Process();
        }
    }

    private static void Process() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String action = scanner.nextLine();
        switch (action) {
            case "buy":
                Buy();
                break;
            case "fill":
                Fill();
                break;
            case "take":
                Take();
                break;
            case "remaining":
                PrintStateOfMachine();
                break;
            case "exit":
                buyOn = false;
                break;
            default:
                System.out.println("Error!!!");
                break;
        }
    }

    private static void Take() {
        System.out.println("I gave you $" + money);
        money = 0;
    }

    private static void Fill() {
        int add;
        System.out.println("Write how many ml of water do you want to add:");
        add = scanner.nextInt();
        water += add;
        System.out.println("Write how many ml of milk do you want to add:");
        add = scanner.nextInt();
        milk += add;
        System.out.println("Write how many g of coffee beans do you want to add:");
        add = scanner.nextInt();
        coffeeBeans += add;
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        add = scanner.nextInt();
        disposableCups += add;
    }

    private static void Buy() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String choice = scanner.nextLine();
        if (choice.equals("back")) {
            return;
        }
        int typeOfCoffee = Integer.parseInt(choice);
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        switch (typeOfCoffee) {
            case 1:
                a = 250;
                c = 16;
                d = 4;
                break;
            case 2:
                a = 350;
                b = 75;
                c = 20;
                d = 7;
                break;
            case 3:
                a = 200;
                b = 100;
                c = 12;
                d = 6;
                break;
        }
        if (water < a) {
            System.out.println("Sorry, not enough water!");
        } else if (milk < b) {
            System.out.println("Sorry, not enough milk!");
        } else if (coffeeBeans < c) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (disposableCups == 0) {
            System.out.println("Sorry, not enough disposable cups!");
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            water -= a;
            milk -= b;
            coffeeBeans -= c;
            disposableCups--;
            money += d;
        }
    }

    private static void PrintStateOfMachine() {
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(coffeeBeans + " of coffee beans");
        System.out.println(disposableCups + " of disposable cups");
        System.out.println(money + " of money");
    }
}
