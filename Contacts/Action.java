package contacts;

import java.util.Scanner;

public interface Action {
    Contact addRecord(Scanner scanner);

    void showFullName(Contact contact);

    void showInfo(Contact contact);

    void editContact(Contact contact, Scanner scanner);
}