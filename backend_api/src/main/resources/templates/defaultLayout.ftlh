<#macro page title="HelpRent">
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>${title}</title>
            <link rel="stylesheet" href="/css/common.css?v=4">
        </head>
        <body>
            <nav>
                <#if role == "LANDLORD"> 
                    <a href="/ui/dashboard">Dashboard</a>
                    <a href="/ui/tickets">Tickets</a>
                    <!-- <a href="NEED TO ADD">Messages</a> -->
                    <a href="NEED TO ADD">HelpGuides</a>

                    <form action="/ui/logout" method="post" style="display:inline;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button class="logout-btn" id="logoutBtn">Sign Out</button>
                    </form>

                <#elseif role == "MAINTENANCE">
                    <a href="/ui/dashboard">Dashboard</a>
                    <a href="/ui/tickets">Tickets</a>
                    
                    <!-- <a href="NEED TO ADD">Messages</a> -->

                    <form action="/ui/logout" method="post" style="display:inline;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button class="logout-btn" id="logoutBtn">Sign Out</button>
                    </form>

                <#elseif role == "TENANT">
                    <!-- ADD TENANT NAVBAR HERE -->

                </#if>
            </nav>
            <main>
                <#nested>
            </main>
        </body>
    </html>
</#macro>