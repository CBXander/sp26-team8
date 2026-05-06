<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="Fixtures">
    <section>
        <div>
            <h1>${fixture.title}</h3>
            <form action="/ui/fixtures/${fixture.fixtureId}/delete" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <button type="submit">X</button>
            </form>
            <form action="/ui/fixtures/${fixture.fixtureId}/edit" method="get">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <button type="submit">edit</button>
            </form>
            <p>${fixture.description}</p>
        </div>
        
    </section>
</@layout.page>