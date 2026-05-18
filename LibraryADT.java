import java.util.List;

public interface LibraryADT {
    boolean addBook(long isbn, String title, String author);

    Book searchBook(long isbn);

    LoanRecord borrowBook(String studentId, long isbn);

    LoanRecord returnBook(String studentId, long isbn);

    List<LoanRecord> viewBorrowHistory(String studentId);

    List<Book> viewAvailableBooks();

    boolean editBook(long isbn, String title, String author);

    Book removeBook(long isbn);

    List<Book> searchByTitleOrAuthor(String keyword);
}
