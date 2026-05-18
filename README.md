# SmartLibrary

SmartLibrary is a Java console application for managing a small library catalogue. It supports separate admin and student workflows, stores books in a binary search tree, and records borrowing activity with stack-based history.

## Features

- Register and log in as an admin or student.
- Admin users can add, edit, remove, search, and list available books.
- Student users can search books, borrow books, return books, and view their borrowing history.
- Book records are sorted and searched by ISBN through a binary search tree.
- Borrowing records are saved per student and displayed with the most recent activity first.
- Library and user data are persisted locally between program runs.

## Project Files

- `Main.java` - application entry point and menu handling.
- `SmartLibrary.java` - library operations and data persistence.
- `LibraryADT.java` - interface for core library actions.
- `BookBST.java` - binary search tree implementation for catalogue books.
- `BorrowHistoryStack.java` - stack implementation for loan history.
- `Book.java`, `LoanRecord.java`, `User.java` - model classes.
- `UserStore.java` - user registration, lookup, default users, and user persistence.

## Requirements

- Java Development Kit (JDK) 17 or newer.

The code uses modern switch expressions, so older Java versions may not compile it correctly.

## How to Run

From the project folder, compile all Java files:

```bash
javac *.java
```

Then start the application:

```bash
java Main
```

## Default Users

If no user file exists yet, the application creates two default accounts:

| Role | User ID | Name |
| --- | --- | --- |
| Admin | `A001` | Default Admin |
| Student | `S001` | Default Student |

You can also register new users from the main menu. User IDs beginning with `A` are treated as admin accounts, while IDs beginning with `S` are treated as student accounts.

## Data Storage

The application creates these local data files while running:

- `smart_library_data.txt` - saved catalogue and loan records.
- `user_info.txt` - saved user accounts.

These files are ignored by Git because they represent local runtime state. Delete them if you want to reset the application back to the default users and an empty catalogue.
