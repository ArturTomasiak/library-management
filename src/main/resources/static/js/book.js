const lendBook = async (bookId) => {
    try {
        const response = await fetch(`/api/loans/lend?bookId=${bookId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
        });

        if (response.ok) {
            alert("Book successfully lent!");
            location.reload();
        } else {
            const errorMessage = await response.text();
            alert("Failed to lend book: " + errorMessage);
        }
    } catch (error) {
        console.error("Error lending book:", error);
        alert("An error occurred while lending the book.");
    }
};