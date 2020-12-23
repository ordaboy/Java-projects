import java.util.Scanner;

public interface Action {
    Contact addRecord(Scanner scanner);

    void printFullName(Contact contact);

    void printInfo(Contact contact);

    void editContact(Contact contact, Scanner scanner);
}