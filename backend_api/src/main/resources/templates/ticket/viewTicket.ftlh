<#import "/defaultlayout.ftlh" as layout>

<@layout.page title="Ticket Details">
    <#if role == "LANDLORD">
        <a href="/ui/units/${ticket.unit.unitId}">Back to Unit</a>
    <#else>
        <a href="/ui/dahsboard">back</a>
    </#if>
        <div class="request-card">
            <h1>${ticket.title}</h1>
            <p>${ticket.description}</p>
            <p>Category: ${ticket.category!" "}</p>
            <p>Status: ${ticket.status}</p>
            <p>Priority: ${ticket.priority!" "}</p>
            <p>Submitted by: ${ticket.submittedBy.firstName} ${ticket.submittedBy.lastName}</p>
            <p>Unit: ${ticket.unit.unitNum} - ${ticket.unit.unitAddress}</p>
            <#if ticket.fixture??>
                <p>Fixture: ${ticket.fixture.title}</p>
            </#if>
            <#if ticket.assignedTo??>
                <p>Assigned to: ${ticket.assignedTo.firstName} ${ticket.assignedTo.lastName}</p>
            <#else>
                <p>Assigned to: Unassigned</p>
            </#if>
            <p>Created: ${ticket.createdAt}</p>
            <p>Updated: ${ticket.updatedAt}</p>
        </div>

    <div class="actions">
        <h3>Actions</h3>

        <#if role == "LANDLORD">
            <#if ticket.status == "OPEN" || ticket.status == "IN_PROGRESS">
                <form action="/ui/tickets/${ticket.ticketId}/assign" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                    <select name="staffId" required>
                        <option value="">Select staff</option>
                        <#if staff??>
                            <#list staff as member>
                                <option value="${member.userId}">${member.firstName} ${member.lastName}</option>
                            </#list>
                        </#if>
                    </select>
                    <button type="submit">Assign</button>
                </form>
            </#if>

            <#if ticket.status == "OPEN" || ticket.status == "IN_PROGRESS">
                <form action="/ui/tickets/${ticket.ticketId}/priority" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                    <select name="priority" required>
                        <option value="LOW">Low</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="HIGH">High</option>
                        <option value="EMERGENCY">Emergency</option>
                    </select>
                    <button type="submit">Set Priority</button>
                </form>
            </#if>

            <#if ticket.status == "OPEN" || ticket.status == "IN_PROGRESS">
                <form action="/ui/tickets/${ticket.ticketId}/status" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                    <button type="submit">Advance Status</button>
                </form>
            </#if>

            <#if ticket.status == "COMPLETED">
                <form action="/ui/tickets/${ticket.ticketId}/complete" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit">Close Ticket</button>
                </form>
            </#if>

            <#if ticket.status != "CLOSED" && ticket.status != "CANCELLED">
                <form action="/ui/tickets/${ticket.ticketId}/cancel" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit">Cancel Ticket</button>
                </form>
            </#if>
        </#if>

        <#if role == "MAINTENANCE">
            <#if ticket.status == "OPEN" || ticket.status == "IN_PROGRESS">
                <form action="/ui/tickets/${ticket.ticketId}/priority" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <select name="priority" required>
                        <option value="LOW">Low</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="HIGH">High</option>
                        <option value="EMERGENCY">Emergency</option>
                    </select>
                    <button type="submit">Set Priority</button>
                </form>
            </#if>

            <#if ticket.status == "OPEN" || ticket.status == "IN_PROGRESS">
                <form action="/ui/tickets/${ticket.ticketId}/status" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit">Advance Status</button>
                </form>
            </#if>
        </#if>

        <#if role == "TENANT">
            <!-- tennant implementation -->
        </#if>
    </div>

    
</@layout.page>