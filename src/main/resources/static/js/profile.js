async function returnBook(loanId) {
    try {
        console.log("Loan ID received:", loanId);

        const response = await fetch(`/api/loans/return?loanId=${loanId}`, {
            method: 'POST'
        });

        if (response.ok) {
            alert("Book successfully returned!");
            window.location.href = "/profile";
        } else {
            const errorMessage = await response.text();
            alert("Failed to return book: " + errorMessage);
        }
    } catch (error) {
        console.error("Error in returnBook:", error);
        alert("An error occurred while returning the book.");
    }
}
