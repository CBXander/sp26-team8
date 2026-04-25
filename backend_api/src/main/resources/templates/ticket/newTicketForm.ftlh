<#import "/defaultlayout.ftlh" as layout>

<@layout.page title="New Ticket">
    <h1>Create Ticket</h1>
    <p>Unit: ${unit.unitNum} - ${unit.unitAddress}</p>

    <form action="/ui/tickets/new?unitId=${unit.unitId}" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <label for="title">Title</label>
        <input type="text" id="title" name="title" required placeholder="Brief description of issue">

        <label for="description">Description</label>
        <textarea id="description" name="description" required placeholder="Detailed description of issue"></textarea>

        <label for="category">Category</label>
        <input type="text" id="category" name="category" placeholder="e.g. Plumbing, Electrical (seperate categories using commas, optional)">

        <#if unit.fixtures?? && (unit.fixtures?size > 0)>
            <label for="fixtureId">Related Fixture (optional)</label>
            <select name="fixtureId">
                <option value="">None</option>
                <#list unit.fixtures as fixture>
                    <option value="${fixture.fixtureId}">${fixture.title}</option>
                </#list>
            </select>
        </#if>

        <button type="submit">Submit Ticket</button>
    </form>

    <a href="/ui/units/${unit.unitId}">Back</a>
</@layout.page>