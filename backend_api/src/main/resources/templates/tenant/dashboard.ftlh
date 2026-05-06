<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>

<body>
    <header>
        <h1>Welcome, ${tenant.firstName}</h1>
        <p>
            <#if tenantUnit??>
                Apartment ${tenantUnit.unitNum} - ${tenantUnit.property.name}
            <#else>
                Apartment information unavailable
            </#if>
        </p>
    </header>

    <nav>
        <a href="/tenant/dashboard">Dashboard</a>
        <a href="/tenant/request">Request Maintenance</a>
        <a href="/tenant/chat">Chat</a>
        <a href="/tenant/guides">Guides</a>

        <form action="/logout" method="post" style="display:inline;">
            <button type="submit" id="logoutBtn" class="logout-btn">Log Out</button>
        </form>
    </nav>

    <main>
        <section>
            <button class="dropdown-btn" type="button" onclick="toggleRequests()" id="requestsBtn">
                Your Active Requests ▶
            </button>

            <div class="dropdown-content" id="requestsDropdown">
                <#if tickets?has_content>
                    <#list tickets as ticket>
                        <#if ticket.status != "CANCELLED">
                            <div class="request-card ${ticket.status?lower_case}">
                                <h3>${ticket.title}</h3>
                                <p>Status: ${ticket.status}</p>

                                <#if ticket.priority??>
                                    <p>Priority: ${ticket.priority}</p>
                                </#if>

                                <#if ticket.category??>
                                    <p>Category: ${ticket.category}</p>
                                </#if>

                                <#if ticket.description??>
                                    <p>${ticket.description}</p>
                                </#if>
                            </div>
                        </#if>
                    </#list>
                <#else>
                    <p>No requests found.</p>
                </#if>
            </div>
        </section>
        
        <!--        Old Quick Actions section, can be re-added later if needed
        <section>
            <h2>Quick Actions</h2>
            <div class="actions">
                <button type="button" onclick="location.href='/tenant/request'">
                    New Maintenance Request
                </button>

                <button type="button" onclick="location.href='/tenant/chat'">
                    Chat
                </button>

                <button type="button" onclick="location.href='/tenant/guides'">
                    Guides
                </button>
            </div>
        </section>
    </main>
        -->
    <script>
    function toggleRequests() {
        const dropdown = document.getElementById("requestsDropdown");
        const btn = document.getElementById("requestsBtn");

        dropdown.classList.toggle("active");

        if (dropdown.classList.contains("active")) {
            btn.innerHTML = "Your Active Requests ▼";
        } else {
            btn.innerHTML = "Your Active Requests ▶";
        }
    }
    </script>
</body>
</html>