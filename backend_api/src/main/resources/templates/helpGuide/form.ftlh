<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="Fixtures">
    
        <section>
            <#if helpGuide??>
                <h1>Edit HelpGuide</h1>
            <#else>
                <h1>Create HelpGuide</h1>
            </#if>
        </section>
        <section>
            <form action="${(guide??)?then('/ui/helpGuides/' + guide.fixtureId + '/edit', '/ui/helpGuides/new')}" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                <label for="title">Title</label>
                <input type="text" id="title" name="title" value="${(guide.title)!''}" />

                <label for="helpGuide">Description</label>
                <textarea for=""helpGuide name="helpGuide">
                    ${(guide.description)!''}
                </textarea>
                <label for="category">Category</label>
                <!--dropdown for category-->
                <label for="fixture">Fixture</label>
                <input type="search" for="fixture"/>
                
                <button type="submit">Save</button>
            </form>
        </section>
</@layout.page>