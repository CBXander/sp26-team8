function loadNav() {
    const userType = localStorage.getItem("userType");  // MAKE THIS WORK 

    const nav = document.createElement("nav");
    if (userType == "provider"){
    nav.innerHTML = `
        <div class="nav-L">
            <a href="../provider/dash.html">Dashboard</a>
            <a href="../common/tickets.html">Tickets</a>
            <a href="../provider/units.html">Units</a>
            <a href="../common/messages.html">Messages</a>
            <a href="../provider/guides.html">HelpGuides</a>
        </div>

        <div class="nav-R">
            <!--ADD HAMBURGER MENU WITH SIGN OUT, EDIT PROFILE -->
        </div>
    `;
    } else if (userType == "maintenance"){
        nav.innerHTML = `
        <div class="nav-L">
            <a href="../maintenance/dash.html">Dashboard</a>
            <a href="../common/s.html">Tickets</a>
            <a href="../common/messages.html">Messages</a>
        </div>

        <div class="nav-R">
            
        </div>
    `;
    }
    
    document.querySelector("header").append(nav);
};

loadNav();