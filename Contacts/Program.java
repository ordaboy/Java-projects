package contacts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    private boolean running;
    private PhoneBook phoneBook;
    private final Scanner scanner;

    public Program(String filename) {
        this.scanner = new Scanner(System.in);
        this.running = true;
        File file = new File(filename);
        if (file.exists()) {
            try {
                this.phoneBook = (PhoneBook) SerializationUtilis.deserialize(filename);
                System.out.println("open " + filename + "\n");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            this.phoneBook = new PhoneBook();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void menu() {
        System.out.print("[menu] Enter action (add, list, search, count, exit): ");
        String action = scanner.nextLine();
        switch (action) {
            case "add":
                add();
                break;
            case "list":
                list();
                break;
            case "search":
                search();
                break;
            case "count":
                count();
                break;
            case "exit":
                exit();
                break;
            default:
                System.out.println("Wrong action!");
                break;
        }
    }

    private void exit() {
        this.running = false;
    }

    private void count() {
        System.out.println("The Phone Book has " + phoneBook.getNumberOfContacts() + " records\n");
    }

    private void search() {
        boolean searching = true;
        while (searching) {
            System.out.print("Enter search query: ");
            String query = scanner.nextLine();
            ArrayList<Integer> arrayList = phoneBook.searchQuery(query);
            System.out.println("Found " + arrayList.size() + " result" + (arrayList.size() > 1 ? "s" : ""));
            for (int i = 1; i <= arrayList.size(); i++) {
                System.out.println(i + ". " + phoneBook.getContact(arrayList.get(i - 1)).getFullName());
            }
            System.out.println();
            System.out.print("[search] Enter action ([number], back, again): ");
            String command = scanner.nextLine();
            try {
                int index = Integer.parseInt(command);
                if (index > 0 && index < arrayList.size() + 1) {
                    Contact contact = phoneBook.getContact(index - 1);
                    Action action = getAction(contact);
                    action.showInfo(contact);
                    record(contact, index - 1); // cont
                    searching = false;
                } else {
                    System.out.println("Bad number!");
                }
            } catch (Exception exception) {
                if ("back".equals(command)) {
                    searching = false;
                } else {
                    if (!"again".equals(command)) {
                        searching = false;
                        System.out.println("Wrong input!");
                    }
                }
            }
        }
    }

    private void record(Contact contact, int index) {
        boolean recording = true;
        while (recording) {
            System.out.print("[record] Enter action (edit, delete, menu): ");
            String action = scanner.nextLine();
            switch (action) {
                case "edit":
                    Action act = getAction(contact);
                    act.editContact(contact, scanner);
                    System.out.println("Saved");
                    act.showInfo(contact);
                    break;
                case "delete":
                    phoneBook.removeContact(index);
                    recording = false;
                    System.out.println();
                    break;
                case "menu":
                    recording = false;
                    System.out.println();
                    break;
                default:
                    System.out.println("Wrong input!");
                    recording = false;
                    System.out.println();
                    break;
            }
        }
    }

    private void add() {
        System.out.print("[add] Enter the type (person, organization): ");
        String type = scanner.nextLine();
        Action action;
        switch (type) {
            case "person":
                action = new ActionPerson();
                break;
            case "organization":
                action = new ActionOrganization();
                break;
            default:
                System.out.println("Wrong type!");
                return;
        }
        Contact newContact = action.addRecord(scanner);
        phoneBook.addContact(newContact);
        System.out.println("The record added.\n");
    }

    private void list() {
        if (phoneBook.empty()) {
            System.out.println("Phone Book is empty!");
            return;
        }
        listAllContacts();
        System.out.println();
        int numberOfContacts = phoneBook.getNumberOfContacts();
        System.out.print("[list] Enter action ([number], back): ");
        String command = scanner.nextLine();
        try {
            int index = Integer.parseInt(command);
            if (index > 0 && index < numberOfContacts + 1) {
                Contact contact = phoneBook.getContact(index - 1);
                Action action = getAction(contact);
                action.showInfo(contact);
                record(contact, index - 1);
            } else {
                System.out.println("Bad number!\n");
            }
        } catch (Exception exception) {
            if (!"back".equals(command)) {
                System.out.println("Wrong input!\n");
            }
        }
    }

    private void listAllContacts() {
        for (int i = 1; i <= phoneBook.getNumberOfContacts(); i++) {
            System.out.print(i + ". ");
            Contact contact = phoneBook.getContact(i - 1);
            Action action = getAction(contact);
            action.showFullName(contact);
        }
    }

    private Action getAction(Contact contact) {
        if (ContactPerson.class == contact.getClass()) {
            return new ActionPerson();
        } else if (ContactOrganization.class == contact.getClass()) {
            return new ActionOrganization();
        } else {
            return null;
        }
    }

    public void save(String filename) {
        try {
            SerializationUtilis.serialize(phoneBook, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
