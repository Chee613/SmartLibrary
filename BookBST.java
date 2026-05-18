import java.util.ArrayList;
import java.util.List;

public class BookBST {
    private Node root;

    private static class Node {
        private Book book;
        private Node left;
        private Node right;

        private Node(Book book) {
            this.book = book;
        }
    }

    public boolean insert(Book book) {
        if (root == null) {
            root = new Node(book);
            return true;
        }

        return insertRecursive(root, book);
    }

    private boolean insertRecursive(Node current, Book book) {
        if (book.getIsbn() == current.book.getIsbn()) {
            return false;
        }

        if (book.getIsbn() < current.book.getIsbn()) {
            if (current.left == null) {
                current.left = new Node(book);
                return true;
            }
            return insertRecursive(current.left, book);
        }

        if (current.right == null) {
            current.right = new Node(book);
            return true;
        }
        return insertRecursive(current.right, book);
    }

    public Book search(long isbn) {
        return searchRecursive(root, isbn);
    }

    private Book searchRecursive(Node current, long isbn) {
        if (current == null) {
            return null;
        }

        if (isbn == current.book.getIsbn()) {
            return current.book;
        }

        if (isbn < current.book.getIsbn()) {
            return searchRecursive(current.left, isbn);
        }

        return searchRecursive(current.right, isbn);
    }

    public boolean deleteByIsbn(long isbn) {
        if (search(isbn) == null) {
            return false;
        }

        root = deleteRecursive(root, isbn);
        return true;
    }

    private Node deleteRecursive(Node current, long isbn) {
        if (current == null) {
            return null;
        }

        if (isbn < current.book.getIsbn()) {
            current.left = deleteRecursive(current.left, isbn);
            return current;
        }

        if (isbn > current.book.getIsbn()) {
            current.right = deleteRecursive(current.right, isbn);
            return current;
        }

        if (current.left == null) {
            return current.right;
        }

        if (current.right == null) {
            return current.left;
        }

        Node successor = findSmallest(current.right);
        current.book = successor.book;
        current.right = deleteRecursive(current.right, successor.book.getIsbn());
        return current;
    }

    private Node findSmallest(Node current) {
        if (current.left == null) {
            return current;
        }

        return findSmallest(current.left);
    }

    public List<Book> toList() {
        List<Book> books = new ArrayList<>();
        addBooksInOrder(root, books);
        return books;
    }

    private void addBooksInOrder(Node current, List<Book> books) {
        if (current == null) {
            return;
        }

        addBooksInOrder(current.left, books);
        books.add(current.book);
        addBooksInOrder(current.right, books);
    }
}
