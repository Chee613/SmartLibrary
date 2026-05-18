import java.time.LocalDate;

public class LoanRecord {
    private final String studentId;
    private final Book book;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private boolean returned;

    public LoanRecord(String studentId, Book book, LocalDate borrowDate, LocalDate dueDate, boolean returned) {
        this.studentId = studentId;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    public String getStudentId() {
        return studentId;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void markReturned() {
        returned = true;
    }

    public String getStatus() {
        return returned ? "Returned" : "Borrowed";
    }

    @Override
    public String toString() {
        return book + " | Student ID: " + studentId
                + " | Borrowed: " + borrowDate
                + " | Due: " + dueDate
                + " | Status: " + getStatus();
    }
}
