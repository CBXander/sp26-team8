<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="HelpGuides">
    <section>
        <h1>HelpGuides</h1>
        <!--LANDLORD-->
        <#if role == "LANDLORD">
            <form action="/helpGuides/new" method="get">
                <button type="submit">Create</button>
            </form>
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
                    <h3><a href="/helpGuides/${guide.helpGuideId}">${guide.title}</a></h3>
                    <p>${guide.category!"—"}</p>
                    <#if guide.fixture??>
                        <p>Fixture: ${guide.fixture.title}</p>
                    </#if>
                    <p>${guide.description}</p>      
                </div>
                </#list>
            </#if>
        <#elseif role == "TENANT">
            <#if fixtures??>
                <#list fixtures as fixture>
                    <div>
                        <h3>${fixture.title}</h3>
                        <p>${fixture.description}</p>
                        <div>
                            <#if fixture.helpGuides??>
                                <#list fixture.helpGuides as guide>
                                    <div>
                                        <#if guide.filePath??>
                                            <#if guide.filePath?ends_with(".pdf")>
                                                <p>PDF Attached</p>
                                            <#else>
                                                <img src="/uploads/helpGuides/${guide.filePath}" alt="preview" style="max-width: 150px; max-height: 100px;">
                                            </#if>
                                        </#if>
                                        <h3><a href="/helpGuides/${guide.helpGuideId}">${guide.title}</a></h3>
                                        <p>${guide.category!"—"}</p>
                                        <#if guide.fixture??>
                                            <p>Fixture: ${guide.fixture.title}</p>
                                        </#if>
                                        <p>${guide.description}</p>
                                    </div>
                                </#list>
                            </#if>
                        </div>
                    </div>
                </#list>
            </#if>
        </#if>
    </section>
</@layout.page>