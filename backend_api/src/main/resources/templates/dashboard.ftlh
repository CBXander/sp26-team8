<#import "/defaultlayout.ftlh" as layout>

<@layout.page title="Dashboard">
    <h1>Welcome, ${user.firstName}.</h1>

    <#if role == "LANDLORD">
        <#if property??>
            <div>
                <h1>${property.name}</h1>
                <h2>${property.address}</h2>
            </div>
            <div>
                <h3>Unassigned Tickets</h3>
                <#if unassigned?? && (unassigned?size > 0)>
                <table>
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Unit</th>
                            <th>Status</th>
                            <th>Priority</th>
                            <th>Created</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list unassigned as ticket>
                            <tr>
                                <td><a href="/ui/tickets/${ticket.ticketId}">${ticket.title}</a></td>
                                <td>${ticket.unit.unitAddress!"—"} ${ticket.unit.unitNum!"—"}</td>
                                <td>${ticket.status!"—"}</td>
                                <td>${ticket.priority!"—"}</td>
                                <td>${ticket.createdAt!"—"}</td>
                                <td></td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
                <#else>
                    <p>All tickets assigned!</p>
                </#if>
            </div>
            <div>
                <h3>Units</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Unit number</th>
                            <th>Unit address</th>
                            <th>Tenant name</th>
                            <th>Status</th>
                            <th><a href="/ui/properties/${property.propertyId}/units/new">Add Unit</a></th>
                        </tr>
                    </thead>
                    <tbody>
                        <#if property.units??>
                            <#list property.units as unit>
                                <tr>
                                    <td>${unit.unitNum}</td>
                                    <td>${unit.unitAddress}</td>
                                    <td>
                                        <#if unit.tenant??>
                                            ${unit.tenant.firstName} ${unit.tenant.lastName}
                                        <#else>
                                            Vacant
                                        </#if>
                                    </td>
                                    <td>${unit.unitStatus}</td>
                                    <td>
                                        <a href="/ui/units/${unit.unitId}">view</a>
                                        <form action="/ui/properties/${property.propertyId}/units/${unit.unitId}/remove" method="post" style="display:inline;">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                            <button type="submit">Remove</button>
                                        </form>
                                    </td>
                                </tr>
                            <#else>
                                <p>This property has no units yet</p>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </div>
            <div>
                <h3>Staff</h3>
                <table>    
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Tickets Assigned</th>
                            <th><a href="/ui/properties/${property.propertyId}/staff/new">Add new staff memember</a></th>
                        </tr>
                    </thead>
                    <tbody>
                        <#if property.staff??>
                            <#list property.staff as staff>
                                <tr>
                                    <td>${staff.firstName} ${staff.lastName}</td>
                                    <td>${staff.email}</td>
                                    <td>${staffTicketCounts[staff.userId?c]!0}</td>
                                    <td>
                                        <form action="/ui/properties/${property.propertyId}/staff/${staff.userId}/remove" method="post" style="display:inline;">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                            <button type="submit">Remove</button>
                                        </form>
                                    </td>
                                </tr>
                            <#else>
                                <p>This property has no staff yet</p>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </div>
        <#else>
            <p>No properties yet.</p>
            <a href="/ui/properties/new">Add new property</a>
        </#if>   
        
    <#elseif role == "MAINTENANCE">
        <div class="overview">
            <h2>Your Assigned Tickets</h2>
            <table>
                <thead>
                    <tr>
                        <th>Unit</th>
                        <th>Ticket</th>
                        <th>Status</th>
                        <th>Scheduled</th>
                        <th>Created</th>
                    </tr>
                </thead>
                <tbody>
                    <#if tickets??>
                        <#list tickets as ticket>
                        
                        <tr>
                            <td><a href="/ui/tickets/${ticket.ticketId}">${ticket.title}</td>
                            <td>${ticket.unit.unitAddress} ${ticket.unit.unitNum}</td>
                            <td><span class="status pending">${ticket.status}</span></td>
                            <td>...</td>
                            <td>${ticket.createdAt}</td>
                        </tr>
                        <#else>
                        <tr>No tickets assigned</tr>
                        </#list>
                    </#if>
                </tbody>
            </table>
        </div> 
    
    <#elseif role == "TENANT">
        <!-- tenant dashboard -->
    </#if>
</@layout.page>