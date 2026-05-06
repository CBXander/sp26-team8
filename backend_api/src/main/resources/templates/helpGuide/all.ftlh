<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="Fixtures">
    <section>
        <h1>HelpGuides</h1>
        <form action="/ui/helpGuides/new" method="get">
            <button type="submit">Create</button>
        </form>
    </section>
    <section>
        <!--Card Container-->
        <#if helpGuides??>
            <#list helpGuides as guide>
            <div class="card">
                <h3><a href="/ui/helpGuides/${guide.fixtureId}">${guide.title}</a></h3>
                <form action="/ui/helpGuides/${guide.fixtureId}/delete" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit">X</button>
                </form>
                <p>${guide.description}</p>
                <form action="/ui/helpGuides/${guide.fixtureId}/assign" method="get">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit">assign to fixture</button>
                    <!--TODO: MAKE THIS A SEARCH BY NAME FOR FIXTURES AND SUBMIT!!!!-->
                </form>
            </div>
            </#list>
        </#if>
    </section>
</@layout.page>