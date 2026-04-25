<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HelpRent | Landlord Registration</title>
    <link rel="stylesheet" href="/css/common.css">
</head>

<body class="login-page">

    <div class="login-container">
        <h1>HelpRent</h1>
        <p class="subtitle">Landlord Registration</p>
        <p> This is only for landlord accounts, if you are a new staff member or a new tenant, please reach out to your landlord for account information</p>
        <#if error??>
            <p class="error">${error}</p>
        </#if>

        <form id="signupForm" action="/ui/signup" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <label for="email">Email</label>
            <input type="email" id="email" name="email" required placeholder="your-email@email.com">

            <label for="firstName">First Name</label>
            <input type="text" id="firstName" name="firstName" required placeholder="first name">

            <label for="lastName">Last Name</label>
            <input type="text" id="lastName" name="lastName" required placeholder="last name">

            <label for="password">Password</label>
            <input type="password" id="password" name="password" required placeholder="your password">

            <button type="submit">Register</button>
        </form>

        <p>Already have an account? <a href="/ui/login">Log in</a></p>
    </div>

</body>
</html>
</html>