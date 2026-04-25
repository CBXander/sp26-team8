<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HelpRent | Login</title>
    <link rel="stylesheet" href="/css/common.css">
</head>

<body class="login-page">

    <div class="login-container">
        <h1>HelpRent</h1>
        <p class="subtitle">Login Portal</p>

        <#if RequestParameters.error??>
            <p id="loginError" class="error "> Invalid email or password </p>
        </#if>

        <#if RequestParameters.logout??>
            <p id="loginError" class="error ">You have been logged out </p>
        </#if>

        <form id="loginForm" action="/ui/login" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <label for="email">Email</label>
            <input type="email" id="email" name="username" required placeholder="your-email@email.com">

            <label for="password">Password</label>
            <input type="password" id="password" name="password" required placeholder="your password">

            <button type="submit" form="loginForm">Log In</button>
        </form>
        <p>Need a landlord account? <a href="/ui/signup">Register now!</a></p>
    </div>
    
</body>
</html>