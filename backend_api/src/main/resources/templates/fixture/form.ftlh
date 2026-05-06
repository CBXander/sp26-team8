<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="Fixtures">
    
        <section>
            <#if fixture??>
                <h1>Edit Fixture</h1>
            <#else>
                <h1>Create Fixture</h1>
            </#if>
        </section>
        <section>
            <form action="${(fixture??)?then('/ui/fixtures/' + fixture.fixtureId + '/edit', '/ui/fixtures/new')}" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <label for="title">Title</label>
                <input type="text" id="title" name="title" value="${(fixture.title)!''}" />

                <label for="description">Description</label>
                <textarea for=""description name="description">
                    ${(fixture.description)!''}
                </textarea>

                <button type="submit">Save</button>
            </form>
        </section>
</@layout.page>