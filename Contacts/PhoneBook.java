package contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBook implements Serializable {
    private final List<Contact> phoneBook;

    public PhoneBook() {
        phoneBook = new ArrayList<>();
    }

    public int getNumberOfContacts() {
        return phoneBook.size();
    }

    public void addContact(Contact contact) {
        phoneBook.add(contact);
    }

    public Contact getContact(int index) {
        return phoneBook.get(index);
    }

    public boolean empty() {
        return phoneBook.isEmpty();
    }

    public ArrayList<Integer> searchQuery(String query) {
        Pattern pattern = Pattern.compile("(?i).*" + query + ".*");
        ArrayList<Integer> foundContacts = new ArrayList<>();
        for (int i = 0; i < phoneBook.size(); i++) {
            Contact contact = phoneBook.get(i);
            Matcher matcher = pattern.matcher(contact.getFullName());
            Matcher matcher1 = pattern.matcher(contact.getNumber());
            if (matcher.find() || matcher1.find()) {
                foundContacts.add(i);
            }
        }
        return foundContacts;
    }

    public void removeContact(int index) {
        phoneBook.remove(index);
    }
}
