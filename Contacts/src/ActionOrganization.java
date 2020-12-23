import java.time.LocalDateTime;
import java.util.Scanner;

public class ActionOrganization implements Action {
    @Override
    public Contact addRecord(Scanner scanner) {
        ContactOrganization contact = new ContactOrganization();
        System.out.print("Enter organization name: ");
        String nameOrganization = scanner.nextLine();
        contact.setName(nameOrganization);
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        contact.setAddress(address);
        System.out.print("Enter number: ");
        String number = scanner.nextLine();
        contact.setNumber(number);
        contact.setTimeCreatedAndEdited();
        return contact;
    }

    @Override
    public void printFullName(Contact contact) { System.out.println(contact.getFullName()); }

    @Override
    public void printInfo(Contact contact) {
        ContactOrganization organization = (ContactOrganization) contact;
        System.out.println("Organization name: " + organization.getName());
        System.out.println("Address: " + organization.getAddress());
        System.out.println("Number: " + ("".equals(organization.getNumber()) ? "[no number]" : organization.getNumber()));
        System.out.println("Time created: " + organization.getTimeCreated());
        System.out.println("Time last edit: " + organization.getTimeLastEdited());
        System.out.println();
    }

    @Override
    public void editContact(Contact contact, Scanner scanner) {
        ContactOrganization organization = (ContactOrganization) contact;
        System.out.print("Select a field (name, address, number): ");
        String field = scanner.nextLine();
        switch (field) {
            case "name" -> {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                organization.setName(name);
            }
            case "address" -> {
                System.out.print("Enter address: ");
                String address = scanner.nextLine();
                organization.setAddress(address);
            }
            case "number" -> {
                System.out.print("Enter number");
                String number = scanner.nextLine();
                organization.setNumber(number);
            }
            default -> System.out.println("Wrong input!");
        }
        organization.setTimeLastEdited(LocalDateTime.now());
    }
}
