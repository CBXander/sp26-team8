<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="Fixtures">
    <section>
        <div class="page-header">
            <h1>Fixtures</h1>
            <form action="/fixtures/new" method="get">
                <button type="submit">Create</button>
            </form>
        </div>
    </section>
    <section>
        <#if fixtures??>
            <#list fixtures as fixture>
            <div class="card">
                <div class="page-header">
                    <h3><a href="/fixtures/${fixture.fixtureId}">${fixture.title}</a></h3>
                    <form action="/fixtures/${fixture.fixtureId}/delete" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button type="submit">X</button>
                    </form>
                </div>
                <p>${fixture.description}</p>
            </div>
            </#list>
        </#if>
    </section>
</@layout.page>