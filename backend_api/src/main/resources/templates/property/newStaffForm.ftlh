<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="New Staff">
    <h1>Create New Staff</h1>

    <form action="/ui/properties/${propertyId}/staff/new" method="post" class="request-form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <label for="firstName">Staff Name</label>
        <input type="text" id="firstName" name="firstName" required placeholder="first">
        <input type="text" id="lastName" name="lastName" required placeholder="last">
        
        <label for="email">Email</label>
        <input type="text" id="email" name="email" required placeholder="staff email">
        
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required placeholder="staff password">
        
        <button type="submit" class="submit-btn">Create Staff</button>
    </form>

    <a href="/ui/properties/${propertyId}">Back</a>
</@layout.page>