<!DOCTYPE html>
<html>
<head>
    <link href='https://fonts.googleapis.com/css?family=Poppins' rel='stylesheet' />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
        }
        .container {
            margin-top: 100px;
            max-width: 800px;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
        }

        ul {
            list-style: none;
            padding: 0;
        }

        li {
            border-bottom: 1px solid #ccc;
            padding: 10px 0;
        }

        .publisher {
            font-weight: bold;
        }

        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
        }

        .navbar-brand {
            margin-left: 10px;
        }

        .navbar-links {
            display: flex;
            align-items: center;
        }

        .navbar-links a {
            margin-left: 10px;
            color: #212529;
            text-decoration: none;
            padding: 14px 16px;
            transition: background-color 0.3s ease;
        }

        .navbar-links a:hover {
            background-color: #ddd;
        }

        .navbar-links a.active {
            background-color: greenyellow;
            color: white;
            border-radius: 4px;
        }

        .navbar-links a.disabled {
            color: #6c757d;
            pointer-events: none;
        }

        /* Style for the "Sign In" and "Sign Up" buttons */
        .right-buttons {
            display: flex;
            align-items: center;
        }

        .right-buttons .btn {
            margin-left: 10px;
        }

        .right-buttons .btn-sign-in,
        .right-buttons .btn-sign-up {
            background-color: greenyellow;
            color: white;
            border: none;
            border-radius: 4px;
            padding: 5px 10px;
            cursor: pointer;
        }

        .right-buttons .btn-sign-in:hover,
        .right-buttons .btn-sign-up:hover {
            background-color: #0056b3;
        }

        .search-form {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }

        .search-form input[type="text"] {
            width: 250px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px 0 0 4px;
            box-sizing: border-box;
        }

        .search-form button {
            background-color: greenyellow;
            color: #fff;
            border: none;
            border-radius: 0 4px 4px 0;
            padding: 8px 20px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        .search-form button:hover {
            background-color: #008754;
        }
        .vtxt{
            margin-bottom: 40px;
        }
        .publ{
            width: 150px;
            display: inline-block;
        }
        .cat{
            width: 150px;
            display: inline-block;
        }
        .pag{
            margin-left: 15px;
            margin-top: 10px;
            text-decoration: none;
            color: white;
            background-color: #00a86b;
            padding: 5px;
        }
    </style>
    <title>Resumes</title>
</head>
<body>


<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <div class="navbar-links">
            <!-- Navigation links -->
            <a class="nav-link" aria-current="page" href="/">Home</a>
                <a class="nav-link" href="/profile">Profile</a>
            <a class="nav-link" href="/vacancies">Vacancies</a>
            <a class="nav-link active" href="/resumes">Resumes</a>
        </div>
        <div class="right-buttons">
                <a class="btn btn-primary btn-sign-in" href="/logout">Log Out</a>
        </div>
    </div>
</nav>

    <div class="container">
        <h1 class="vtxt">Resumes</h1>
        <div class="search-form">
            <input type="text" name="category" placeholder="Search by category">
            <button type="submit">Search</button>
        </div>



        <#if resumes.content?size == 0>
            <p>No vacancies found.</p>
        <#else>
            <ul>
                <#list resumes.content as resume>
                    <li>
                        <div class="publ"><span class="publisher">${resume.applicant.accountName}</span></div>
                        <span class="job">${resume.job}</span>
                    </li>
                </#list>
            </ul>
        </#if>


        <#if resumes.hasPrevious()>
            <a class="pag" href="/vacancies?page=${resumes.number - 1}">Previous Page</a>
        </#if>
        <#if resumes.hasNext()>
            <a class="pag" href="/vacancies?page=${resumes.number + 1}">Next Page</a>
        </#if>


</body>
</html>