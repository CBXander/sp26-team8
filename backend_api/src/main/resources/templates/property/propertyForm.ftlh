<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="New Property">
    <h1>Create New Property</h1>

    <form action="/properties/new" method="post" class="request-form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <label for="name">Property Name</label>
        <input type="text" id="name" name="name" required placeholder="Property name">

        <label for="address">Address</label>
        <input type="text" id="address" name="address" required placeholder="Property address">

        <button type="submit" class="submit-btn">Create Property</button>
    </form>

    <a href="/dashboard">Cancel</a>
</@layout.page>