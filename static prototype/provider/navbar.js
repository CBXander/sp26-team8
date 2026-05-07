
function loadNav() {
    const nav = document.createElement("nav");
    nav.innerHTML = `
        <div class="nav-L">
            <a href="../provider/dash.html">Dashboard</a>
            <a href="../provider/tickets.html">Tickets</a>
            <a href="../provider/units.html">Units</a>
            <a href="../provider/messages.html">Messages</a>
            <a href="../provider/guides.html">HelpGuides</a>
            <button class="logout-btn" id="logoutBtn">Sign Out</button>
        </div>
    `;
    document.querySelector("header").append(nav);

    document.getElementById("logoutBtn").addEventListener("click", function() {
                localStorage.removeItem("isLoggedIn");
                console.log("sign out button pressed")
                window.location.href = "../common/login.html";
            });
};

loadNav();

