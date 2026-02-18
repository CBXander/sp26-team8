const dropdownBtn = document.querySelector(".dropdown-btn");
const dropdownContent = document.querySelector(".dropdown-content");

dropdownBtn.addEventListener("click", function () {
    dropdownContent.classList.toggle("active");

    if (dropdownContent.classList.contains("active")) {
        dropdownBtn.innerHTML = "Your Active Requests ▲";
    } else {
        dropdownBtn.innerHTML = "Your Active Requests ▼";
    }
});


const form = document.getElementById("maintenanceForm");
const successMessage = document.getElementById("successMessage");

if (form) {
    form.addEventListener("submit", function (e) {
        e.preventDefault();
        form.reset();
        successMessage.classList.remove("hidden");
    });
}
