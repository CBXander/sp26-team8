<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Maintenance Chat</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>

<body>

<header>
    <h1>Chat for Ticket: ${ticket.title}</h1>
</header>

<nav>
    <a href="/tenant/dashboard">Dashboard</a>
    <a href="/tenant/request">Request Maintenance</a>
    <a href="/tenant/guides">Guides</a>

    <form action="/logout" method="post" style="display:inline;">
        <button id="logoutBtn" class="logout-btn" type="submit">Log Out</button>
    </form>
</nav>

<main>
    <div class="chat-container">

        <div class="chat-window" id="chatWindow">
            <#if messages?has_content>
                <#list messages as message>
                    <div class="message <#if message.sender.userId == tenant.userId>tenant<#else>maintenance</#if>">
                        <#if message.content?? && message.content?has_content>
                            <p>${message.content}</p>
                        </#if>

                        <#if message.attachments?? && message.attachments?has_content>
                            <#list message.attachments as attachment>
                            
                             <#if attachment.fileType?? && attachment.fileType?starts_with("image/")>
                                <div class="chat-media-preview">
                                    <img src="${attachment.filePath}"
                                        alt="Attached image"
                                        class="chat-image-thumb"
                                        onclick="openImageOverlay('${attachment.filePath}')">
                                </div>
                            <#elseif attachment.fileType?? && attachment.fileType?starts_with("video/")>
                                <div>
                                    <video controls class="chat-video-thumb">
                                        <source src="${attachment.filePath}" type="${attachment.fileType}">
                                    </video>
                                </div>
                            <#elseif attachment.filePath?lower_case?ends_with(".png")
                                || attachment.filePath?lower_case?ends_with(".jpg")
                                || attachment.filePath?lower_case?ends_with(".jpeg")
                                || attachment.filePath?lower_case?ends_with(".webp")
                                || attachment.filePath?lower_case?ends_with(".gif")>
                                <div class="chat-media-preview">
                                    <img src="${attachment.filePath}"
                                        alt="Attached image"
                                        class="chat-image-thumb"
                                        onclick="openImageOverlay('${attachment.filePath}')">
                                </div>
                            <#elseif attachment.filePath?lower_case?ends_with(".mp4")
                                || attachment.filePath?lower_case?ends_with(".webm")
                                || attachment.filePath?lower_case?ends_with(".mov")>
                                <div>
                                    <video controls class="chat-video-thumb">
                                        <source src="${attachment.filePath}">
                                    </video>
                                </div>
                            <#else>
                                <div>
                                    <a href="${attachment.filePath}" target="_blank">View Attachment</a>
                                </div>
                            </#if>   
                            </#list>
                        </#if>
                    </div>
                </#list>
            <#else>
                <div class="message maintenance">
                    No messages yet.
                </div>
            </#if>
        </div>

        <form action="/tenant/chat/${ticket.ticketId}" method="post" enctype="multipart/form-data" class="chat-input">
            <input type="text" name="content" placeholder="Type your message...">
            <input type="file" name="file" accept="image/*,video/*">
            <button id="sendBtn" type="submit">Send</button>
        </form>

    </div>
</main>

<div id="imageOverlay" class="image-overlay" onclick="closeImageOverlay()">
    <div class="image-overlay-content" onclick="event.stopPropagation()">
        <button type="button" class="image-overlay-close" onclick="closeImageOverlay()">✕</button>
        <img id="overlayImage" src="" alt="Expanded image" class="image-overlay-img">
    </div>
</div>

<script>
function openImageOverlay(imageSrc) {
    document.getElementById("overlayImage").src = imageSrc;
    document.getElementById("imageOverlay").classList.add("active");
    document.body.classList.add("overlay-open");
}

function closeImageOverlay() {
    document.getElementById("imageOverlay").classList.remove("active");
    document.getElementById("overlayImage").src = "";
    document.body.classList.remove("overlay-open");
}
</script>

</body>
</html>