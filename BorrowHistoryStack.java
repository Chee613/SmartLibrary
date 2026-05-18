import java.util.ArrayList;
import java.util.List;

public class BorrowHistoryStack {
    private Node top;

    private static class Node {
        private final LoanRecord loanRecord;
        private final Node next;

        private Node(LoanRecord loanRecord, Node next) {
            this.loanRecord = loanRecord;
            this.next = next;
        }
    }

    public void push(LoanRecord loanRecord) {
        top = new Node(loanRecord, top);
    }

    public boolean isEmpty() {
        return top == null;
    }

    public LoanRecord findActiveLoanByIsbn(long isbn) {
        Node current = top;

        while (current != null) {
            LoanRecord loanRecord = current.loanRecord;
            if (!loanRecord.isReturned() && loanRecord.getBook().getIsbn() == isbn) {
                return loanRecord;
            }
            current = current.next;
        }

        return null;
    }

    public List<LoanRecord> toList() {
        List<LoanRecord> loanRecords = new ArrayList<>();
        Node current = top;

        while (current != null) {
            loanRecords.add(current.loanRecord);
            current = current.next;
        }

        return loanRecords;
    }
}
