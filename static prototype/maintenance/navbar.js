
function loadNav() {
    const userType = localStorage.getItem("userType");  // MAKE THIS WORK 

    const nav = document.createElement("nav");
    nav.innerHTML = `
        <div class="nav-L">
            <a href="../maintenance/dash.html">Dashboard</a>
            <a href="../maintenance/tickets.html">Tickets</a>
            <a href="../maintenance/messages.html">Messages</a>
            <button class="logout-btn" id="logoutBtn">Sign Out</button>
        </div>

        
        </div>
    `;
    
    document.querySelector("header").append(nav);

    document.getElementById("logoutBtn").addEventListener("click", function() {
            localStorage.removeItem("isLoggedIn");
            console.log("sign out button pressed")
            window.location.href = "../common/login.html";
        });
};

function toggleMenu() {
    document.getElementById("nav-dropdown").classList.toggle("active");
}

loadNav();