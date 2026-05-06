<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="Fixtures">
    <section>
        <h1>Fixtures</h1>
        <form action="/ui/fixtures/new" method="get">
            <button type="submit">Create</button>
        </form>
    </section>
    <section>
        <!--Card Container-->
        <#if fixtures??>
            <#list fixtures as fixture>
            <div class="card">
                <h3><a href="/ui/fixtures/${fixture.fixtureId}">${fixture.title}</a></h3>
                <form action="/ui/fixtures/${fixture.fixtureId}/delete" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit">X</button>
                </form>
                <p>${fixture.description}</p>
            </div>
            </#list>
        </#if>
    </section>
</@layout.page>