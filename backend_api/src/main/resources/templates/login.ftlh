<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HelpRent | Login</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>

<body class="login-page">

    <div class="login-container">
        <h1>HelpRent</h1>
        <p class="subtitle">Login Portal</p>

        <form action="/login" method="post">

            <label>Email</label>
            <input type="email" name="username" required placeholder="user@email.com">

            <label>Password</label>
            <input type="password" name="password" required placeholder="Enter password">

            <button type="submit">Log In</button>

        </form>

        <!-- Error message from backend -->
        <#if RequestParameters.error??>
            <p class="error">
                Invalid email or password.
            </p>
        </#if>