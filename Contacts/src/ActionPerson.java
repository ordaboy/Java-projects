import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionPerson implements Action {

    @Override
    public Contact addRecord(Scanner scanner) {
        ContactPerson contact = new ContactPerson();
        System.out.print("Enter name: ");
        String namePerson = scanner.nextLine();
        contact.setName(namePerson);
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        contact.setSurname(surname);
        System.out.print("Enter birth date: ");
        String birthDate = scanner.nextLine();
        contact.setBirthDate(toValidBirthDate(birthDate));
        System.out.print("Enter gender (M, F): ");
        String gender = scanner.nextLine();
        contact.setGender(toValidGender(gender));
        System.out.print("Enter number: ");
        String number = scanner.nextLine();
        contact.setNumber(number);
        contact.setTimeCreatedAndEdited();
        return contact;
    }

    @Override
    public void printFullName(Contact contact) {
        System.out.println(contact.getFullName());
    }

    @Override
    public void printInfo(Contact contact) {
        ContactPerson person = (ContactPerson) contact;
        System.out.println("Name: " + person.getName());
        System.out.println("Surname: " + person.getSurname());
        System.out.println("Birth date: " + (person.getBirthDate() == null ? "[no data]" : person.getBirthDate()));
        System.out.println("Gender: " + (person.getGender() == 'U' ? "[no data]" : person.getGender()));
        System.out.println("Number: " + ("".equals(person.getNumber()) ? "[no number]" : person.getNumber()));
        System.out.println("Time created: " + person.getTimeCreated());
        System.out.println("Time last edit: " + person.getTimeLastEdited());
        System.out.println();
    }

    @Override
    public void editContact(Contact contact, Scanner scanner) {
        ContactPerson person = (ContactPerson) contact;
        System.out.print("Select a field (name, surname, birth date, gender, number): ");
        String field = scanner.nextLine();
        switch (field) {
            case "name" -> {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                person.setName(name);
            }
            case "surname" -> {
                System.out.print("Enter surname: ");
                String surname = scanner.nextLine();
                person.setSurname(surname);
            }
            case "birth date" -> {
                System.out.print("Enter birth date: ");
                String birthDate = scanner.nextLine();
                person.setBirthDate(toValidBirthDate(birthDate));
            }
            case "gender" -> {
                System.out.print("Enter gender: ");
                String gender = scanner.nextLine();
                person.setGender(toValidGender(gender));
            }
            case "number" -> {
                System.out.print("Enter number");
                String number = scanner.nextLine();
                person.setNumber(number);
            }
            default -> System.out.println("Bad input!");
        }
        person.setTimeLastEdited(LocalDateTime.now());
    }

    private LocalDate toValidBirthDate(String birthDate) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(birthDate);
        if (matcher.matches()) {
            try {
                return LocalDate.parse(birthDate);
            } catch (Exception exception) {
                System.out.println("Bad birth date!");
                return null;
            }
        } else {
            System.out.println("Bad birth date!");
            return null;
        }
    }

    private char toValidGender(String gender) {
        if ("M".equals(gender)) {
            return 'M';
        } else if ("F".equals(gender)) {
            return 'F';
        } else {
            System.out.println("Bad gender!");
            return 'U';
        }
    }
}
