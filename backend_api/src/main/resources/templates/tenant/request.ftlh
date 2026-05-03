<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Request Maintenance</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>

<body>

<header>
    <h1>Request Maintenance</h1>
</header>

<nav>
    <a href="/tenant/dashboard">Dashboard</a>
    <a href="/tenant/request">Request Maintenance</a>
    <a href="/tenant/guides">Guides</a>

    <form action="/logout" method="post" style="display:inline;">
        <button id="logoutBtn" class="logout-btn" type="submit">Log Out</button>
    </form>
</nav>

<main>

    <form action="/tenant/request" method="post" class="request-form">

        <label>
            Issue Title
            <input type="text" name="title" required placeholder="e.g. Sink leaking under cabinet">
        </label>

        <label>
            Category
            <select name="category" required>
                <option value="">Select category</option>
                <option value="PLUMBING">Plumbing</option>
                <option value="ELECTRICAL">Electrical</option>
                <option value="HVAC">HVAC</option>
                <option value="APPLIANCES">Appliances</option>
                <option value="GENERAL_MAINTENANCE">General Maintenance</option>
            </select>
        </label>

        <label>
            Urgency
            <select name="priority" required>
                <option value="">Select urgency</option>
                <option value="LOW">Low</option>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
                <option value="EMERGENCY">Emergency</option>
            </select>
        </label>

        <label>
            Description
            <textarea name="description" rows="5" required placeholder="Describe the issue in detail..."></textarea>
        </label>

        <button type="submit" class="submit-btn">
            Submit Request
        </button>

    </form>

    <#if tickets?has_content>
        <section>
            <h2>Your Requests</h2>

            <div class="tabs">
                <button onclick="showTab('active')">Active</button>
                <button onclick="showTab('completed')">Completed</button>
                <button onclick="showTab('cancelled')">Cancelled</button>
            </div>

            <div id="activeTab" class="tab-content">
                <#list tickets as ticket>
                    <#if ticket.status != "COMPLETED" && ticket.status != "CANCELLED">
                        <div class="request-card">
                            <h3>${ticket.title}</h3>
                            <p><strong>Status:</strong> ${ticket.status}</p>
                            <p><strong>Created:</strong> ${ticket.createdAt?substring(0,10)?date("yyyy-MM-dd")?string("MMM dd, yyyy")}</p>
                            <p><strong>Updated:</strong> ${ticket.updatedAt?substring(0,10)?date("yyyy-MM-dd")?string("MMM dd, yyyy")}</p>

                            <#if ticket.priority??>
                                <p><strong>Priority:</strong> ${ticket.priority}</p>
                            </#if>

                            <#if ticket.category??>
                                <p><strong>Category:</strong> ${ticket.category}</p>
                            </#if>

                            <#if ticket.description??>
                                <p>${ticket.description}</p>
                            </#if>

                            <a href="/tenant/chat/${ticket.ticketId}">Open Chat</a>

                            <form action="/tenant/request/${ticket.ticketId}/cancel" method="post">
                                <button type="submit">Cancel Request</button>
                            </form>
                        </div>
                    </#if>
                </#list>
            </div>

            <div id="completedTab" class="tab-content" style="display:none;">
                <#list tickets as ticket>
                    <#if ticket.status == "COMPLETED">
                        <div class="request-card completed">
                            <h3>${ticket.title}</h3>
                            <p><strong>Status:</strong> ${ticket.status}</p>
                            <p><strong>Created:</strong> ${ticket.createdAt?substring(0,10)?date("yyyy-MM-dd")?string("MMM dd, yyyy")}</p>
                            <p><strong>Updated:</strong> ${ticket.updatedAt?substring(0,10)?date("yyyy-MM-dd")?string("MMM dd, yyyy")}</p>

                            <a href="/tenant/chat/${ticket.ticketId}">Open Chat</a>
                        </div>
                    </#if>
                </#list>
            </div>

            <div id="cancelledTab" class="tab-content" style="display:none;">
                <#list tickets as ticket>
                    <#if ticket.status == "CANCELLED">
                        <div class="request-card cancelled">
                            <h3>${ticket.title}</h3>
                            <p><strong>Status:</strong> ${ticket.status}</p>
                            <p><strong>Created:</strong> ${ticket.createdAt?substring(0,10)?date("yyyy-MM-dd")?string("MMM dd, yyyy")}</p>
                            <p><strong>Updated:</strong> ${ticket.updatedAt?substring(0,10)?date("yyyy-MM-dd")?string("MMM dd, yyyy")}</p>

                            <a href="/tenant/chat/${ticket.ticketId}">Open Chat</a>
                        </div>
                    </#if>
                </#list>
            </div>

        </section>
    <#else>
        <p>No requests found.</p>
    </#if>

</main>

<script>
function showTab(tab) {
    document.getElementById("activeTab").style.display = "none";
    document.getElementById("completedTab").style.display = "none";
    document.getElementById("cancelledTab").style.display = "none";

    if (tab === "active") document.getElementById("activeTab").style.display = "block";
    if (tab === "completed") document.getElementById("completedTab").style.display = "block";
    if (tab === "cancelled") document.getElementById("cancelledTab").style.display = "block";
}
</script>

<script src="/js/script.js"></script>

</body>
</html>