<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <style>
        /* General Styles */
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #667eea, #764ba2);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* Form Container */
        .login-container {
            background: white;
            padding: 25px;
            width: 350px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            border-radius: 10px;
            text-align: center;
        }

        .login-container h2 {
            color: #333;
            margin-bottom: 20px;
        }

        /* Error Message */
        .error-message {
            color: white;
            background-color: #dc3545;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 15px;
            display: none;
        }

        /* Form Styling */
        .input-group {
            margin-bottom: 15px;
            text-align: left;
        }

        .input-group label {
            display: block;
            font-size: 14px;
            color: #555;
            margin-bottom: 5px;
        }

        .input-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            outline: none;
            transition: 0.3s;
        }

        .input-group input:focus {
            border-color: #667eea;
            box-shadow: 0px 0px 5px rgba(102, 126, 234, 0.5);
        }

        /* Password Toggle */
        .password-wrapper {
            position: relative;
        }

        .password-wrapper i {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #666;
        }

        /* Buttons */
        .btn {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: 0.3s;
        }

        .btn-login {
            background-color: #007bff;
            color: white;
        }

        .btn-login:hover {
            background-color: #0056b3;
        }

        .btn-reset {
            background-color: #dc3545;
            color: white;
            margin-top: 5px;
        }

        .btn-reset:hover {
            background-color: #b02a37;
        }

        /* Signup Link */
        .signup-link {
            margin-top: 15px;
            font-size: 14px;
        }

        .signup-link a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }

        .signup-link a:hover {
            text-decoration: underline;
        }

    </style>
</head>
<body>

    <div class="login-container">
        <h2>Login</h2>

        <% Object error = request.getAttribute("loginError"); %>
        <% if (error != null) { %>
            <div class="error-message"><%= error.toString() %></div>
        <% } %>

        <form method="post" action="./LoginServlet">
            <div class="input-group">
                <label>Email:</label>
                <input type="text" name="email" required>
            </div>

            <div class="input-group password-wrapper">
                <label>Password:</label>
                <input type="password" name="pass" id="password" required>
                <i onclick="togglePassword()" id="toggleIcon"></i>
            </div>

            <button type="submit" class="btn btn-login">Login</button>
            <button type="reset" class="btn btn-reset">Clear</button>
        </form>

        <p class="signup-link">New User? <a href="Register.html">Sign Up</a></p>
    </div>

    <script>
        function togglePassword() {
            var passwordField = document.getElementById("password");
            var toggleIcon = document.getElementById("toggleIcon");

            if (passwordField.type === "password") {
                passwordField.type = "text";
                toggleIcon.innerHTML = "";
            } else {
                passwordField.type = "password";
                toggleIcon.innerHTML = "";
            }
        }

        // Show error message if exists
        var errorMessage = document.querySelector('.error-message');
        if (errorMessage.innerHTML.trim() !== "") {
            errorMessage.style.display = "block";
        }
    </script>

</body>
</html>
