# 📚 SmartLibrary

> A Java console-based Library Management System that uses custom data structures — a **Binary Search Tree** for the book catalogue and a **Linked-List Stack** for borrowing history — to efficiently manage book inventory, user accounts, and loan tracking.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Data Structures](#data-structures)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Usage Guide](#usage-guide)
- [Default Accounts](#default-accounts)
- [Data Persistence](#data-persistence)
- [File Format Specification](#file-format-specification)
- [Testing](#testing)
- [License](#license)

---

## Overview

**SmartLibrary** is a role-based library management system built entirely in Java without any external dependencies. It demonstrates the practical application of fundamental data structures in a real-world scenario:

- **Binary Search Tree (BST)** — stores the book catalogue, enabling O(log n) average-case search, insertion, and deletion by ISBN.
- **Linked-List Stack** — stores each student's borrowing history, naturally displaying the most recent activity first (LIFO order).
- **LinkedHashMap** — maps student IDs to their individual history stacks, preserving insertion order.

The system supports two user roles — **Admin** and **Student** — each with a dedicated menu of operations. All data is persisted to local text files using a pipe-delimited format with escape-character support.

---

## Features

### Admin Operations
| # | Feature | Description |
|---|---------|-------------|
| 1 | **Add Book** | Insert a new book into the BST catalogue by ISBN, title, and author |
| 2 | **Search by ISBN** | Locate a specific available book using BST search |
| 3 | **View All Books** | Display all available books sorted by ISBN (in-order traversal) |
| 4 | **Edit Book** | Update the title and author of an existing catalogue book |
| 5 | **Remove Book** | Permanently delete a book from the catalogue |
| 6 | **Search by Title/Author** | Find books by keyword match across title or author fields |
| 7 | **View Student History** | Inspect any student's full borrowing history |

### Student Operations
| # | Feature | Description |
|---|---------|-------------|
| 1 | **Search by ISBN** | Look up a book by its ISBN number |
| 2 | **Borrow Book** | Check out an available book (removes it from catalogue) |
| 3 | **Return Book** | Return a borrowed book (restores it to catalogue) |
| 4 | **View My History** | View personal borrowing history (most recent first) |
| 5 | **View All Books** | Browse all available books sorted by ISBN |
| 6 | **Search by Title/Author** | Find books by keyword in title or author |

### General
- **User Registration** — register new admin (`A`-prefixed ID) or student (`S`-prefixed ID) accounts
- **Role-Based Login** — automatic menu routing based on user ID prefix
- **Data Persistence** — all books, loans, and users are saved to disk and restored on startup
- **Input Validation** — robust handling of empty fields, invalid ISBNs, and duplicate entries
- **Dynamic Table Formatting** — console output with auto-sized column widths

---

## Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                     Main.java (UI Layer)                     │
│         Console menus, input validation, table output        │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌─────────────────────┐     ┌───────────────────────────┐  │
│  │  SmartLibrary.java  │     │    UserStore.java         │  │
│  │  (Service Layer)    │     │    (User Management)      │  │
│  │  implements         │     │    Registration, Login,   │  │
│  │  LibraryADT         │     │    Persistence            │  │
│  └────────┬────────────┘     └───────────────────────────┘  │
│           │                                                  │
│  ┌────────┴────────────────────────────────────────────┐    │
│  │              Data Structure Layer                    │    │
│  │  ┌──────────────┐    ┌────────────────────────┐     │    │
│  │  │  BookBST     │    │  BorrowHistoryStack     │    │    │
│  │  │  (BST)       │    │  (Linked-List Stack)    │    │    │
│  │  └──────┬───────┘    └──────────┬─────────────┘     │    │
│  │         │                       │                    │    │
│  │  ┌──────┴───────┐    ┌──────────┴─────────────┐     │    │
│  │  │  Book.java   │    │  LoanRecord.java       │     │    │
│  │  │  (Model)     │    │  (Model)               │     │    │
│  │  └──────────────┘    └────────────────────────┘     │    │
│  └─────────────────────────────────────────────────────┘    │
│                                                              │
│  ┌──────────────────────────────────────────────────────┐   │
│  │              Persistence Layer (File I/O)             │   │
│  │   smart_library_data.txt  │  user_info.txt            │   │
│  └──────────────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────────────┘
```

---

## Project Structure

```
SmartLibrary/
├── Main.java                  # Entry point — console UI, menus, input handling
├── LibraryADT.java            # Interface defining core library operations
├── SmartLibrary.java          # Library service — implements LibraryADT, file I/O
├── BookBST.java               # Binary Search Tree for the book catalogue
├── BorrowHistoryStack.java    # Linked-list stack for per-student loan history
├── Book.java                  # Immutable book model (ISBN, title, author)
├── LoanRecord.java            # Loan transaction model (dates, status)
├── User.java                  # User model (ID, name, role)
├── UserStore.java             # User registration, lookup, and persistence
├── smart_library_data.txt     # Runtime data — catalogue and loan records
├── user_info.txt              # Runtime data — registered user accounts
├── .gitignore                 # Git ignore rules
└── README.md                  # This file
```

---

## Data Structures

### 1. Binary Search Tree (`BookBST`)

The book catalogue is stored in a **Binary Search Tree** keyed by ISBN. This structure was chosen because:

- **Ordered retrieval** — an in-order traversal produces all books sorted by ISBN in O(n) time
- **Efficient lookup** — searching by ISBN runs in O(log n) average case
- **Dynamic sizing** — books can be inserted and deleted without resizing arrays

**Key operations:**

| Operation | Method | Average Time | Worst Case |
|-----------|--------|-------------|------------|
| Insert | `insert(Book)` | O(log n) | O(n) |
| Search | `search(long isbn)` | O(log n) | O(n) |
| Delete | `deleteByIsbn(long isbn)` | O(log n) | O(n) |
| List All | `toList()` | O(n) | O(n) |

**Deletion strategy:** When deleting a node with two children, the BST replaces it with the **in-order successor** (smallest node in the right subtree) to maintain BST ordering.

### 2. Linked-List Stack (`BorrowHistoryStack`)

Each student's borrowing history is stored in a **singly-linked-list stack**. This structure was chosen because:

- **LIFO ordering** — the most recent borrow/return appears first, which is the natural display order
- **O(1) push** — recording a new loan is constant time
- **No capacity limit** — the stack grows dynamically with each transaction

**Key operations:**

| Operation | Method | Time Complexity |
|-----------|--------|----------------|
| Push | `push(LoanRecord)` | O(1) |
| Is Empty | `isEmpty()` | O(1) |
| Find Active Loan | `findActiveLoanByIsbn(long)` | O(n) |
| To List | `toList()` | O(n) |

### 3. LinkedHashMap (Student → History Mapping)

A `LinkedHashMap<String, BorrowHistoryStack>` maps each student ID to their personal history stack. This provides:

- **O(1) lookup** by student ID
- **Insertion-order iteration** for consistent file output
- **Lazy initialization** via `computeIfAbsent()`

---

## Requirements

- **Java Development Kit (JDK) 17** or newer

> The code uses Java 17+ features such as modern switch expressions (`case X ->`) and text API methods (`String.isBlank()`).

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Chee613/SmartLibrary.git
cd SmartLibrary
```

### 2. Compile

```bash
javac *.java
```

### 3. Run

```bash
java Main
```

---

## Usage Guide

### Entry Menu

```
===== Smart Library =====
1. Login
2. Register
3. Exit
Admin ID starts with A. Example: A001
Student ID starts with S. Example: S001
Enter your choice:
```

### Admin Menu (after login with A-prefixed ID)

```
===== Smart Library Admin Menu (A001) =====
1. Add Book
2. Search Book by ISBN
3. View All Available Books
4. Edit Book Details
5. Remove Book
6. Search by Title or Author
7. View Student History
8. Logout
Enter your choice:
```

### Student Menu (after login with S-prefixed ID)

```
===== Smart Library Student Menu (S001) =====
1. Search Book by ISBN
2. Borrow Book
3. Return Book
4. View My History
5. View All Available Books
6. Search by Title or Author
7. Logout
Enter your choice:
```

### Sample Output — View All Available Books

```
Available Books (Sorted by ISBN):
+----+---------------+-------------------------------------------------+------------------------------------------+
| No | ISBN          | Title                                           | Author                                   |
+----+---------------+-------------------------------------------------+------------------------------------------+
|  1 | 9780132350884 | Clean Code                                      | Robert C. Martin                         |
|  2 | 9780134093413 | Computer Networking                             | James Kurose, Keith Ross                 |
|  3 | 9780134685991 | Effective Java                                  | Joshua Bloch                             |
+----+---------------+-------------------------------------------------+------------------------------------------+
```

### Sample Output — Borrowing History

```
Borrowing History for S001 (Most Recent First):
+----+---------------+----------------------------+------------+------------+----------+
| No | ISBN          | Title                      | Borrowed   | Due        | Status   |
+----+---------------+----------------------------+------------+------------+----------+
|  1 | 9780132350884 | Clean Code                 | 2026-05-30 | 2026-06-13 | Borrowed |
+----+---------------+----------------------------+------------+------------+----------+
```

---

## Default Accounts

When no `user_info.txt` file exists, the application creates two default accounts:

| Role | User ID | Name |
|------|---------|------|
| Admin | `A001` | Default Admin |
| Student | `S001` | Default Student |

**ID Conventions:**
- IDs starting with **`A`** → Admin role (full catalogue management)
- IDs starting with **`S`** → Student role (borrow/return operations)

You can register additional accounts from the main menu.

---

## Data Persistence

The application uses two plain-text files for persistence:

| File | Contents | Created When |
|------|----------|-------------|
| `smart_library_data.txt` | Book catalogue and loan records | First book is added or loan is created |
| `user_info.txt` | Registered user accounts | Application starts with no existing user file |

Data is **automatically saved** after every state-changing operation (add, edit, remove, borrow, return, register) and **automatically loaded** when the application starts.

To **reset** the application to its initial state, delete both data files.

---

## File Format Specification

### `smart_library_data.txt`

```
[CATALOGUE]
<isbn>|<title>|<author>
...

[LOANS]
<studentId>|<isbn>|<title>|<author>|<borrowDate>|<dueDate>|<returned>
...
```

### `user_info.txt`

```
[USERS]
<userId>|<name>|<role>
...
```

- Fields are **pipe-delimited** (`|`)
- Pipe characters and backslashes within field values are **escaped** (`\|` and `\\`)
- Dates follow the **ISO 8601** format (`YYYY-MM-DD`)
- The `returned` field is a boolean string (`true` or `false`)

---

## Testing

### Manual Testing Scenarios

| Test Case | Steps | Expected Result |
|-----------|-------|-----------------|
| **Add Book** | Login as admin → Add Book → Enter ISBN, title, author | Book appears in catalogue |
| **Duplicate ISBN** | Add a book with an existing ISBN | "Book could not be added" message |
| **Borrow Book** | Login as student → Borrow → Enter ISBN | Book removed from available list, loan record created |
| **Return Book** | Login as student → Return → Enter borrowed ISBN | Book restored to catalogue, status shows "Returned" |
| **Search by ISBN** | Enter a valid ISBN | Book details displayed |
| **Search by Keyword** | Enter partial title or author name | Matching books displayed |
| **View History** | Login as student → View My History | Loan records shown most-recent-first |
| **Invalid ISBN** | Enter letters or negative number for ISBN | "Invalid ISBN" error message |
| **Empty Field** | Leave title or author blank | "This field cannot be empty" message |
| **Data Persistence** | Add books → Exit → Restart | Previously added books are still present |
| **Default Users** | Delete `user_info.txt` → Restart | A001 and S001 accounts recreated |

### Edge Cases

| Scenario | Behaviour |
|----------|-----------|
| Borrow an already-borrowed book | Fails — book is not in the available catalogue |
| Return a book not borrowed | Fails — no active loan found |
| Edit a borrowed book | Fails — only available catalogue books can be edited |
| Remove a borrowed book | Fails — only available catalogue books can be removed |
| Register duplicate user ID | Fails — "The user ID may already exist" |
| Login with unregistered ID | Fails — "User ID not found" |
| Corrupt data file | Malformed lines are silently skipped during load |

---

## License

This project is developed for educational purposes as part of a Data Structures coursework assignment.
