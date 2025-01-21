# Library Management System

A simple application for managing a library, built using Java and PostgreSQL.

## Table of Contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
  - [Docker](#docker)
  - [IntelliJ](#intellij)

## Overview
This project provides a web-based library management system where users can explore books and their categories. The backend is powered by Java, while PostgreSQL serves as the database.

## Prerequisites
Before running the project, ensure you have the following installed:
- [Docker](https://www.docker.com/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- Java Development Kit (JDK) 17

## Setup Instructions

### 1. Docker

1. **Run a PostgreSQL Docker container**  
   Execute the following command in your terminal to start a PostgreSQL container:
   ```bash
   docker run --name library-container -e POSTGRES_DB=library -e POSTGRES_USER=exampleuser -e POSTGRES_PASSWORD=examplepassword -p 5432:5432 -d postgres
   ```

2. **Log in to the PostgreSQL database**  
   Run the following commands:
   ```bash
   docker exec -it library-container bash
   psql -U exampleuser -d library
   ```

3. **Insert example data**  
   Copy and execute the following SQL commands inside the PostgreSQL console to populate the database with test data:
   ```sql
   INSERT INTO categories (name) VALUES ('Hard Sci-Fi');
   INSERT INTO categories (name) VALUES ('Post-Cyberpunk');
   INSERT INTO categories (name) VALUES ('Science Fiction');
   INSERT INTO categories (name) VALUES ('Dystopian');
   INSERT INTO categories (name) VALUES ('High Fantasy');

   INSERT INTO books (title, author, published_year, isbn, cover_art, description, popularity)
   VALUES 
   ('The Three-Body Problem', 'Liu Cixin', 2008, '9780765377067', 'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1415428227i/20518872.jpg', 'A hard sci-fi novel exploring humanity’s first contact with an alien civilization.', 0),
   ('The Diamond Age', 'Neal Stephenson', 1995, '9780553380965', 'https://m.media-amazon.com/images/I/71I0dNstp2L._UF1000,1000_QL80_.jpg', 'A post-cyberpunk story set in a nano-technological future, blending adventure and philosophy.', 0),
   ('Dune', 'Frank Herbert', 1965, '9780441013593', 'https://m.media-amazon.com/images/I/81TmnPZWb0L._AC_UF1000,1000_QL80_.jpg', 'An epic science fiction saga about politics, religion, and survival on the desert planet of Arrakis.', 0),
   ('1984', 'George Orwell', 1949, '9780451524935', 'https://m.media-amazon.com/images/I/612ADI+BVlL.jpg', 'A dystopian novel exploring the dangers of totalitarian regimes and surveillance.', 0),
   ('The Hobbit', 'J.R.R. Tolkien', 1937, '9780547928227', 'https://cdn.swiatksiazki.pl/media/catalog/product/3/x/3x99904354330-1.jpg?width=650&height=650&store=default&image-type=small_image', 'The beloved tale of Bilbo Baggins’s adventure to recover treasure guarded by a dragon.', 0),
   ('The Fellowship of the Ring', 'J.R.R. Tolkien', 1954, '9780547928210', 'https://ecsmedia.pl/c/the-fellowship-of-the-ring-b-iext163232596.jpg', 'The first part of The Lord of the Rings trilogy, following Frodo’s journey to destroy the One Ring.', 0),
   ('The Two Towers', 'J.R.R. Tolkien', 1954, '9780547928203', 'https://ecsmedia.pl/c/lord-of-the-rings-2-two-towers-b-iext163944920.jpg', 'The second part of The Lord of the Rings trilogy, chronicling the quest against Sauron’s forces.', 0),
   ('The Return of the King', 'J.R.R. Tolkien', 1955, '9780547928197', 'https://m.media-amazon.com/images/I/71tDovoHA+L._AC_UF1000,1000_QL80_.jpg', 'The final part of The Lord of the Rings trilogy, detailing the conclusion of the War of the Ring.', 0),
   ('The Dark Forest', 'Liu Cixin', 2008, '9780765377081', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlzjcoqiBf5J8dFEKbivxGViPCcKt3vbSclg&s', 'The sequel to The Three-Body Problem, examining humanity’s strategy in the face of alien invasion.', 0),
   ('Death’s End', 'Liu Cixin', 2010, '9780765377104', 'https://upload.wikimedia.org/wikipedia/en/6/62/Death%27s_End_-_bookcover.jpg', 'The stunning conclusion to The Three-Body Problem trilogy, exploring the vast future of civilization.', 0);

   INSERT INTO book_categories (book_id, category_id)
   VALUES 
   ((SELECT book_id FROM books WHERE title = 'The Three-Body Problem'), (SELECT category_id FROM categories WHERE name = 'Hard Sci-Fi')),
   ((SELECT book_id FROM books WHERE title = 'The Diamond Age'), (SELECT category_id FROM categories WHERE name = 'Post-Cyberpunk')),
   ((SELECT book_id FROM books WHERE title = 'Dune'), (SELECT category_id FROM categories WHERE name = 'Science Fiction')),
   ((SELECT book_id FROM books WHERE title = '1984'), (SELECT category_id FROM categories WHERE name = 'Dystopian')),
   ((SELECT book_id FROM books WHERE title = 'The Hobbit'), (SELECT category_id FROM categories WHERE name = 'High Fantasy')),
   ((SELECT book_id FROM books WHERE title = 'The Fellowship of the Ring'), (SELECT category_id FROM categories WHERE name = 'High Fantasy')),
   ((SELECT book_id FROM books WHERE title = 'The Two Towers'), (SELECT category_id FROM categories WHERE name = 'High Fantasy')),
   ((SELECT book_id FROM books WHERE title = 'The Return of the King'), (SELECT category_id FROM categories WHERE name = 'High Fantasy')),
   ((SELECT book_id FROM books WHERE title = 'The Dark Forest'), (SELECT category_id FROM categories WHERE name = 'Hard Sci-Fi')),
   ((SELECT book_id FROM books WHERE title = 'Death’s End'), (SELECT category_id FROM categories WHERE name = 'Hard Sci-Fi'));
   ```

### 2. IntelliJ

1. **Configure the project structure**
   - Ensure the project uses SDK 17.
   - Set the module language level to 17.

2. **Adjust IntelliJ settings**
   - Under Java Compiler, use `Javac` and ensure both the project and target bytecode version are set to 17.
   - Enable annotation processing and set it to obtain processors from the project classpath.

3. **Sync and build the project**
   - Once synced and built, run `LibraryApplication` to start the application.
   - Access the website at `http://localhost:8080/`.