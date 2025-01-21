document.getElementById('signup-form').addEventListener('submit', async function(event) {
    event.preventDefault();
    const formData = new FormData(this);

    const response = await fetch('/api/auth/signup', {
        method: 'POST',
        body: new URLSearchParams(formData)
    });

    if (response.ok) {
        window.location.href = '/';
    } else {
        const error = await response.text();
        alert(error);
    }
});
