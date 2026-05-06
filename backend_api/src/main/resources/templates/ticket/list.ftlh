<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="Tickets">
    <section>
        <h1>Tickets</h1>

        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Unit</th>
                    <th>Status</th>
                    <th>Priority</th>
                    <th>Assigned To</th>
                    <th>Created</th>
                </tr>
            </thead>
            <tbody>
                <#if tickets??>
                    <#list tickets as ticket>
                        <tr>
                            <td><a href="/ui/tickets/${ticket.ticketId}">${ticket.title}</a></td>
                            <td>${ticket.unit.unitAddress!"—"} ${ticket.unit.unitNum!"—"}</td>
                            <td>${ticket.status!"—"}</td>
                            <td>${ticket.priority!"—"}</td>
                            <td>
                                <#if ticket.assignedTo??>
                                    ${ticket.assignedTo.firstName} ${ticket.assignedTo.lastName}
                                <#else>
                                    Unassigned
                                </#if>
                            </td>
                            <td>${ticket.createdAt!"—"}</td>
                        </tr>
                    <#else>
                        <tr><td colspan="6">No tickets found</td></tr>
                    </#list>
                </#if>
            </tbody>
        </table>
    </section>
</@layout.page>