<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="HelpGuides">
    <section>
        <div>
            <h1>${guide.title}</h3>
            <form action="/helpGuides/${guide.helpGuideId}/edit" method="get">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <button type="submit">Edit</button>
            </form>
            <form action="/helpGuides/${guide.helpGuideId}/delete" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <button type="submit">X</button>
            </form>
        </div>
        <div>
            <p>${guide.description}</p>
            <p>${guide.category}</p>
            <#if guide.filePath??>
                <#if guide.filePath?ends_with(".pdf")>
                    <embed src="/uploads/helpGuides/${guide.filePath}" type="application/pdf" width="100%" height="500px">
                <#else>
                    <img src="/uploads/helpGuides/${guide.filePath}" alt="Help Guide Image" style="max-width: 500px;">
                </#if>
            </#if>
            <#if guide.fixture??>
                <div>
                    <h3><a href="/fixtures/${guide.fixture.fixtureId}">${guide.fixture.title}</a></h3>
                    <p>${guide.fixture.description}</p>
                </div>
            </#if>
        </div>
    </section>
</@layout.page>