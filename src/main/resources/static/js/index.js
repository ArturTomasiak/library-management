const loadBooksAndCategories = async () => {
    try {

        const booksResponse = await fetch('/api/books');
        const books = await booksResponse.json();

        const categoriesResponse = await fetch('/api/categories');
        const categories = await categoriesResponse.json();

        populateCategories(categories);

        displayBooks(books);
    } catch (error) {
        console.error('Error loading books or categories:', error);
    }
};

const populateCategories = (categories) => {
    const sortSelect = document.getElementById('sort');
    sortSelect.innerHTML = `
        <option value="">All Books</option>
        <option value="popularity">Popularity</option>
    `;

    categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.name;
        option.textContent = category.name;
        sortSelect.appendChild(option);
    });

    sortSelect.addEventListener('change', (event) => filterBooks(event.target.value));
};

const filterBooks = async (filterOption) => {
    try {
        let url = '/api/books';
        if (filterOption === 'popularity') {
            url += '?sortBy=popularity';
        } else if (filterOption) {
            url += `?category=${filterOption}`;
        }
        const response = await fetch(url);
        const books = await response.json();
        displayBooks(books);
    } catch (error) {
        console.error('Error filtering books:', error);
    }
};

const displayBooks = (books) => {
    const bookList = document.querySelector('.book-list');
    bookList.innerHTML = '';

    books.forEach(book => {
        const bookCard = document.createElement('div');
        bookCard.className = 'book-card';
        bookCard.innerHTML = `
            <a href="/books/${book.bookId}">
                <img src="${book.coverArt}" alt="Book Cover" class="book-cover">
                <h3>${book.title}</h3>
                <p>Author: ${book.author}</p>
                <p>${book.publishedYear || ''}</p>
            </a>`;
        bookList.appendChild(bookCard);
    });
};

document.addEventListener('DOMContentLoaded', loadBooksAndCategories);
