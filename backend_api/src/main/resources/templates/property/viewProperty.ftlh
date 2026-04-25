<#import "/defaultlayout.ftlh" as layout>

<@layout.page title="Property Overview">
    <div>
        <h1>${property.name}</h1>
        <h2>${property.address}</h2>
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
                    <th><a href="/ui/properties/${propertyId}/units/new">Add Unit</a></th>
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
                    <th><a href="/ui/properties/${property.propertyId}/staff/new">Add new staff memember</a></th>
                </tr>
            </thead>
            <tbody>
                <#if property.staff??>
                    <#list property.staff as staff>
                        <tr>
                            <td>${staff.firstName} ${staff.lastName}</td>
                            <td>${staff.email}</td>
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
</@layout.page>