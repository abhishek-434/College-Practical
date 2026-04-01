import library.Book;

public class BookTest {
    public static void main(String[] args) {

        Book book1 = new Book("Effective Java", "Joshua Bloch", 54.99);
        Book book2 = new Book("Clean Code", "Robert C. Martin", 42.50);

        System.out.println("Displaying results imported from 'library' package:");
        book1.displayDetails();
        book2.displayDetails();
    }
}
