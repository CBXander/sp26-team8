<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="HelpGuides">
    <section>
    <#if helpGuide??>
        <h1>Edit HelpGuide</h1>
        <form action="/helpGuides/{guide.helpGuideId}/edit" method="post" enctype="multipart/form-data">
    <#else>
        <h1>Create HelpGuide</h1>
        <form action="/helpGuides/new" method="post" enctype="multipart/form-data">
    </#if>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <label for="title">Title</label>
        <input type="text" id="title" name="title" value="${(guide.title)!''}" required/>

        <label for="description">Description</label>
        <textarea id="description" name="description" required>
            ${(guide.description)!''}
        </textarea>
        
        <label for="category">Category</label>
        <select name="category" id="category">
            <option value="">Select category (optional)</option>
            <option value="Plumbing" <#if guide?? && guide.category?? && guide.category == "Plumbing">selected</#if>>Plumbing</option>
            <option value="Electrical" <#if guide?? && guide.category?? && guide.category == "Electrical">selected</#if>>Electrical</option>
            <option value="HVAC" <#if guide?? && guide.category?? && guide.category == "HVAC">selected</#if>>HVAC</option>
            <option value="Appliances" <#if guide?? && guide.category?? && guide.category == "Appliances">selected</#if>>Appliances</option>
            <option value="Flooring" <#if guide?? && guide.category?? && guide.category == "Flooring">selected</#if>>Flooring</option>
            <option value="Doors & Windows" <#if guide?? && guide.category?? && guide.category == "Doors & Windows">selected</#if>>Doors & Windows</option>
            <option value="Pest Control" <#if guide?? && guide.category?? && guide.category == "Pest Control">selected</#if>>Pest Control</option>
            <option value="Safety" <#if guide?? && guide.category?? && guide.category == "Safety">selected</#if>>Safety</option>
            <option value="General Maintenance" <#if guide?? && guide.category?? && guide.category == "General Maintenance">selected</#if>>General Maintenance</option>
        </select>

        <label for="fixtureId">Related Fixture (optional)</label>
        <select name="fixtureId" id="fixtureId">
            <option value="">None</option>
            <#if fixtures??>
                <#list fixtures as fixture>
                    <option value="${fixture.fixtureId}" <#if guide?? && guide.fixture?? && guide.fixture.fixtureId == fixture.fixtureId>selected</#if>>${fixture.title}</option>
                </#list>
            </#if>
        </select>

        <label for="file">Attachment (optional)</label>
        <input type="file" id="file" name="file" accept=".pdf,.png,.jpg,.jpeg" >

        <button type="submit">Save</button>
        </form>
    </section>
</@layout.page>