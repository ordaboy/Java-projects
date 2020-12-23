public class Main {
    public static void main(String[] args) {
        String fileName = "phonebook.db";
        Program program = new Program(fileName);
        while (program.isRunning()) {
            program.menu();
        }
        program.save(fileName);
    }

}
