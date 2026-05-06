<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="HelpGuides">
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
                <#if guide.filePath??>
                    <#if guide.filePath?ends_with(".pdf")>
                        <p>PDF Attached</p>
                    <#else>
                        <img src="/uploads/helpGuides/${guide.filePath}" alt="preview" style="max-width: 150px; max-height: 100px;">
                    </#if>
                </#if>
                <h3><a href="/ui/helpGuides/${guide.helpGuideId}">${guide.title}</a></h3>
                <p>${guide.category!"—"}</p>
                <#if guide.fixture??>
                    <p>Fixture: ${guide.fixture.title}</p>
                </#if>
                <p>${guide.description}</p>      
            </div>
            </#list>
        </#if>
    </section>
</@layout.page>