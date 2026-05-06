<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="New Unit">
    <h1>Create New Unit</h1>

    <form action="/ui/properties/${propertyId}/units/new" method="post" class="request-form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <label for="unitNumber">Unit Number</label>
        <input type="text" id="unitNumber" name="unitNumber" placeholder="#(optional)">
        
        <label for="unitAddress">Unit Address</label>
        <input type="text" id="unitAddress" name="unitAddress" placeholder="address (optional)">

        <button type="submit" class="submit-btn">Create Unit</button>
    </form>

    <a href="/ui/properties/${propertyId}">Back</a>
</@layout.page>