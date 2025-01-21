document.getElementById('login-form').addEventListener('submit', async function(event) {
    event.preventDefault();
    const formData = new FormData(this);

    const response = await fetch('/api/auth/login', {
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
