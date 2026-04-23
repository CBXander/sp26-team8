<#import "/defaultlayout.ftlh" as layout>

<@layout.page title="New Tenant">
    <h1>Create New Tenant</h1>

    <form action="/ui/units/${unitId}/tenant/new" method="post" class="request-form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <label for="firstName">First Name</label>
        <input type="text" id="firstName" name="firstName" required placeholder="first name">

        <label for="lastName">Last Name</label>
        <input type="text" id="lastName" name="lastName" required placeholder="last name">

        <label for="email">Email</label>
        <input type="email" id="email" name="email" required placeholder="tenant email">

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required placeholder="tenant password">

        <button type="submit" class="submit-btn">Create Tenant</button>
    </form>

    <a href="/ui/units/${unitId}">Back</a>
</@layout.page>