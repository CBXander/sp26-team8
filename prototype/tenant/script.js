// Protect pages (except login)
if (!window.location.pathname.includes("login.html")) {
    const isLoggedIn = localStorage.getItem("isLoggedIn");

    if (isLoggedIn !== "true") {
        window.location.href = "../common/login.html";
    }
}



const dropdownBtn = document.querySelector(".dropdown-btn");
const dropdownContent = document.querySelector(".dropdown-content");

if (dropdownBtn && dropdownContent) {
    dropdownBtn.addEventListener("click", function () {
        dropdownContent.classList.toggle("active");

        if (dropdownContent.classList.contains("active")) {
            dropdownBtn.innerHTML = "Your Active Requests ▲";
        } else {
            dropdownBtn.innerHTML = "Your Active Requests ▼";
        }
    });
}


//* This only simulates for request.html //
const form = document.getElementById("maintenanceForm");
const successMessage = document.getElementById("successMessage");

if (form) {
    form.addEventListener("submit", function (e) {
        e.preventDefault();
        form.reset();   
        successMessage.classList.remove("hidden");
    });
}

//* Chat Box Temp //
const sendBtn = document.getElementById("sendBtn");
const chatInput = document.getElementById("chatInput");
const chatWindow = document.getElementById("chatWindow");

if (sendBtn) {
    sendBtn.addEventListener("click", function () {
        const messageText = chatInput.value.trim();

        if (messageText !== "") {
            const newMessage = document.createElement("div");
            newMessage.classList.add("message", "tenant");
            newMessage.textContent = messageText;

            chatWindow.appendChild(newMessage);
            chatInput.value = "";
            chatWindow.scrollTop = chatWindow.scrollHeight;
        }
    });
}