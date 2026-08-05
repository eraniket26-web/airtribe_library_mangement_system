**Project Overview**
The Library Management System is a Java-based application designed to help librarians manage books, patrons, and lending operations. The system supports book inventory management, patron registration, book checkout/return workflows, and borrowing history tracking.
Language: Java
**Concepts demonstrated:**
Object-Oriented Programming
SOLID Principles
Design Patterns
Java Collections
Logging
**Features**
Core Features
Book Management
Add new books
Remove books from inventory
Update book information
Search books by:
Title
Author
ISBN
Patron Management
Register new patrons
Update patron information
Maintain borrowing history
View previously borrowed books
Lending Management
Checkout books
Return books
Track:
Borrow date
Due date
Return date
Maintain available/borrowed book status


+----------------------+
|       Library        |
+----------------------+
| - books: List<Book>  |
| - patrons: List<Patron> |
| - loans: List<Loan>  |
+----------------------+
| + addBook()          |
| + removeBook()       |
| + updateBook()       |
| + addPatron()        |
| + addLoan()          |
| + findBook()         |
| + findPatron()       |
+----------------------+
          |
          | 1
          |
          | manages
          |
          | *
+----------------------+
|        Book          |
+----------------------+
| - bookId: String     |
| - title: String      |
| - author: String     |
| - isbn: String       |
| - publicationYear:int|
| - status: Status     |
+----------------------+
| + updateStatus()     |
+----------------------+
          |
          | 1
          |
          | borrowed through
          |
          | *
+----------------------+
|        Loan          |
+----------------------+
| - loanId: String     |
| - borrowDate: Date   |
| - dueDate: Date      |
| - returnDate: Date   |
| - status: LoanStatus |
+----------------------+
| + returnBook()       |
| + isActive()         |
+----------------------+
          |
          | *
          |
          | belongs to
          |
          | 1
+----------------------+
|       Patron         |
+----------------------+
| - id: String         |
| - name: String       |
| - email: String      |
| - phoneNumber:String |
| - history: List<Loan>|
| - currentLoans:List<Loan>|
+----------------------+
| + addLoan()          |
| + removeLoan()       |
+----------------------+



+----------------------+
|   LendingService     |
+----------------------+
| - library: Library   |
+----------------------+
| + checkoutBook()     |
| + returnBook()       |
+----------------------+
          |
          | uses
          |
          v
       Library



+----------------------+
|   PatronManager      |
+----------------------+
| - library: Library   |
+----------------------+
| + addPatron()        |
| + updatePatron()     |
| + getHistory()       |
+----------------------+
          |
          | uses
          |
          v
       Library


Below points are important

a) For lending process you need patron id which starts with 'PAT0001' b) Each book will have unique id and isbn number to it mostly you need isbn number to query data for book or lending process , for demo you can use '978-0132350884'