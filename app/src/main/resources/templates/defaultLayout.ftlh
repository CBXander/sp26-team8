<#macro page title="HelpRent">
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>${title}</title>
                <link rel="stylesheet" href="/css/main.css">

        </head>
        <body>
            <nav>
                <#if role == "LANDLORD"> 
                    <a href="/dashboard">Dashboard</a>
                    <a href="/tickets">Tickets</a>
                    <a href="/fixtures">Fixtures</a>
                    <!-- <a href="NEED TO ADD">Messages</a> -->
                    <a href="/helpGuides">HelpGuides</a>

                    <form action="/logout" method="post" style="display:inline;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button class="logout-btn" id="logoutBtn">Sign Out</button>
                    </form>

                <#elseif role == "MAINTENANCE">
                    <a href="/dashboard">Dashboard</a>
                    <a href="/tickets">Tickets</a>
                    
                    <!-- <a href="NEED TO ADD">Messages</a> -->

                    <form action="/logout" method="post" style="display:inline;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button class="logout-btn" id="logoutBtn">Sign Out</button>
                    </form>

                <#elseif role == "TENANT"> 
                    <a href="/dashboard">Dashboard</a>
                    <a href="/tickets/request">Request Maintenance</a>
                    <a href="/helpGuides">Guides</a>

                    <form action="/logout" method="post" style="display:inline;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button class="logout-btn" id="logoutBtn">Sign Out</button>
                    </form>
                </#if>
            </nav>
            <main>
                <#nested>
            </main>
        </body>
    </html>
</#macro>