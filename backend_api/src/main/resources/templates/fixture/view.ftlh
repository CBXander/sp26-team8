<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="Fixtures">
    <section>
        <div>
            <h1>${fixture.title}</h1>
            <form action="/ui/fixtures/${fixture.fixtureId}/delete" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <button type="submit">X</button>
            </form>
            <form action="/ui/fixtures/${fixture.fixtureId}/edit" method="get">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <button type="submit">edit</button>
            </form>
            <p>${fixture.description}</p>
            <div>
                <#if fixture.helpGuides??>
                    <#list fixture.helpGuides as guide>
                        <div class="card">
                            <#if guide.filePath??>
                                <#if guide.filePath?ends_with(".pdf")>
                                    <p>PDF Attached</p>
                                <#else>
                                    <img src="/uploads/helpGuides/${guide.filePath}" alt="preview" style="max-width: 150px; max-height: 100px;">
                                </#if>
                            </#if>
                            <h3><a href="/ui/helpGuides/${guide.helpGuideId}">${guide.title}</a></h3>
                            <p>${guide.category!"—"}</p>
                            <p>${guide.description}</p>      
                        </div>
                    </#list>
                </#if>
            </div>
        </div>
        
    </section>
</@layout.page>