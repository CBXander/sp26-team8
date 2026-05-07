<#import "/defaultlayout.ftlh" as layout>
<@layout.page title="Fixtures">
    <section>
        <div class="page-header">
            <h1>${fixture.title}</h1>
            <form action="/fixtures/${fixture.fixtureId}/delete" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <button type="submit">X</button>
            </form>
            <form action="/fixtures/${fixture.fixtureId}/edit" method="get">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <button type="submit">edit</button>
            </form>

            <#if units?? && (units?size > 0)>
                <form action="/fixtures/${fixture.fixtureId}/assign" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <select name="unitId" required>
                        <option value="">Assign to Units</option>
                        <#list units as unit>
                            <option value="${unit.unitId}">${unit.unitNum} - ${unit.unitAddress}</option>
                        </#list>
                    </select>
                    <button type="submit">Assign</button>
                </form>
            </#if>
        </div>

        <div>
            <p>${fixture.description}</p>
        </div>

        <div>
            <h3>Help Guides</h3>
            <#if fixture.helpGuides?? && (fixture.helpGuides?size > 0)>
                <#list fixture.helpGuides as guide>
                    <div class="page-header">
                        <span><a href="/helpGuides/${guide.helpGuideId}">${guide.title}</a> - ${guide.category!"—"}</span>
                        <form action="/fixtures/${fixture.fixtureId}/guides/${guide.helpGuideId}/remove" method="post" style="display:inline;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <button type="submit">Remove</button>
                        </form>
                    </div>
                </#list>
            <#else>
                <p>No guides linked</p>
            </#if>

            <#if allGuides?? && (allGuides?size > 0)>
                <form action="/fixtures/${fixture.fixtureId}/guides/add" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <select name="helpGuideId" required>
                        <option value="">Select guide</option>
                        <#list allGuides as guide>
                            <option value="${guide.helpGuideId}">${guide.title}</option>
                        </#list>
                    </select>
                    <button type="submit">Add Guide</button>
                </form>
            </#if>
        </div>
    </section>
</@layout.page>
