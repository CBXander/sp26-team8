<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="Unit Details">
    <a href="/properties/${unit.property.propertyId}">Back to Property</a>
    <div>
        <#if unit.unitNum == "SINGLE">
            <h1>${unit.unitAddress}</h1>
            <p>Style: ${unit.unitNum}</p>
            <p>Status: ${unit.unitStatus}</p>
        <#else>
            <h1>Unit ${unit.unitNum}</h1>
            <h3>${unit.unitAddress}</h3>
            <p>Style: Apartment</p>
            <p>Status: ${unit.unitStatus}</p>
        </#if>
    </div>

    <div>
        <h3>Tenant</h3>
        <#if unit.tenant??>
            <p>${unit.tenant.firstName} ${unit.tenant.lastName} - ${unit.tenant.email}</p>
            <form action="/units/${unit.unitId}/tenant/remove" method="post" style="display:inline;">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <button type="submit">Remove Tenant</button>
            </form>
        <#else>
            <p>No tenant assigned</p>
            <a href="/units/${unit.unitId}/tenant/new">Assign Tenant</a>
        </#if>
    </div>

    <div>
        <h3>Fixtures</h3>
        <#if unit.fixtures??>
            <#list unit.fixtures as fixture>
                <div class="page-header">
                    <span>${fixture.title} - ${fixture.description}</span>
                    <form action="/units/${unit.unitId}/fixtures/${fixture.fixtureId}/remove" method="post" style="display:inline;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button type="submit">Remove</button>
                    </form>
                </div>
            <#else>
                <p>No fixtures assigned</p>
            </#list>
        </#if>
        <#if allFixtures?? && (allFixtures?size > 0)>
            <form action="/units/${unit.unitId}/fixtures/add" method="post" style="display:inline;">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <select name="fixtureId" required>
                    <option value="">Select fixture</option>
                    <#list allFixtures as fixture>
                        <option value="${fixture.fixtureId}">${fixture.title}</option>
                    </#list>
                </select>
                <button type="submit">Add Fixture</button>
            </form>
        </#if>
    </div>

    <div>
        <h3>Tickets</h3>
        <#if unit.tickets??>
            <table>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Status</th>
                        <th>Priority</th>
                        <th>Created</th>
                    </tr>
                </thead>
                <tbody>
                    <#list unit.tickets as ticket>
                        <tr>
                            <td><a href="/tickets/${ticket.ticketId}">${ticket.title}</a></td>
                            <td>${ticket.status!"—"}</td>
                            <td>${ticket.priority!"—"}</td>
                            <td>${ticket.createdAt!"—"}</td>
                        </tr>
                    <#else>
                        <tr><td colspan="4">No tickets</td></tr>
                    </#list>
                </tbody>
            </table>
        </#if>
        <a href="/tickets/new?unitId=${unit.unitId}">Create Ticket</a>
    </div>

    
</@layout.page>
