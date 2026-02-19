if (!window.location.pathname.includes("login.html")) {
    const isLoggedIn = localStorage.getItem("isLoggedIn");

    if (isLoggedIn !== "true") {
        window.location.href = "../common/login.html";
    }
}

const loginForm = document.getElementById("loginForm");
if (loginForm) {
    loginForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const errorMessage = document.getElementById("loginError");

        if (email === "tenant@email.com" && password === "1234") {
            localStorage.setItem("isLoggedIn", "true");
            window.location.href = "../tenant/cust-home.html";
        } 
        else if (email === "landlord@email.com" && password === "1234") {
            localStorage.setItem("isLoggedIn", "true");
            window.location.href = "../provider/dash.html";
        } 
        else if (email === "maintenance@email.com" && password === "1234") {
            localStorage.setItem("isLoggedIn", "true");
            window.location.href = "../maintenance/dash.html";
        }       
        else {
            errorMessage.classList.remove("hidden");
        }
    });
}
/** Logout */
const logoutBtn = document.getElementById("logoutBtn");

if (logoutBtn) {
    logoutBtn.addEventListener("click", function () {
        localStorage.removeItem("isLoggedIn");
        window.location.href = "../common/login.html";
    });
}