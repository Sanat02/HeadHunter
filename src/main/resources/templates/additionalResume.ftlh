<#import "spring.ftl" as spring>
<!DOCTYPE html>
<html>
<head>
    <link href='https://fonts.googleapis.com/css?family=Poppins' rel='stylesheet'/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
    <style>
        body {
            font-family: 'Poppins', sans-serif;
        }
        #lk{
            background-color: greenyellow;
            border-color:greenyellow; ;
        }

        .container {
            max-width: 800px;
            margin: 60px auto;
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

        .category {
            color: #00a86b;
        }

        .date {
            color: #777;
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
            border-radius: 4px;
            padding: 8px 20px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s ease;
            margin-left: -4px;
        }

        .search-form button:hover {
            background-color: #008754;
        }

        .vtxt {
            margin-bottom: 40px;
        }


        .form-section h2 {
            color: #333;
            margin-bottom: 10px;
        }

        .form-section form {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .form-section form label {
            font-weight: bold;
        }

        .form-section form input[type="text"],
        .form-section form input[type="date"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .form-section form button {
            background-color: greenyellow;
            color: #fff;
            border: none;
            border-radius: 4px;
            padding: 10px 20px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        .form-section form button:hover {
            background-color: #008754;
        }

        #addEducationBtn {
            margin-bottom: 10px;
            display: inline-block;
            margin-right: 10px;
            background-color: #00a86b;
            border: none;
        }
    </style>
    <title><@spring.message 'resume'/></title>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"><@spring.message 'navbar.logo'/></a>
        <div class="navbar-links">
            <a class="nav-link " aria-current="page" href="#"><@spring.message 'navbar.navi.home'/></a>
            <a class="nav-link active" href="/profile"><@spring.message 'navbar.navi.profile'/></a>
            <a class="nav-link" href="/vacancies"><@spring.message 'navbar.navi.vacancies'/></a>
            <a class="nav-link" href="/jobresumes"><@spring.message 'navbar.navi.jobs'/></a>

        </div>
        <div class="right-buttons">
            <div class="dropdown" >
                <button class="btn btn-secondary dropdown-toggle" id="lk" type="button" data-bs-toggle="dropdown"
                        aria-expanded="false">
                    <i class="flag-united-kingdom flag m-0"></i>
                    <@spring.message 'navbar.navi.lang'/>
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="?lang=en">English</a></li>
                    <li><a class="dropdown-item" href="?lang=ru">Русский</a></li>
                </ul>
            </div>
            <a class="btn btn-primary btn-sign-in" href="/logout" id="logout"><@spring.message 'logout'/></a>
        </div>
    </div>
</nav>

<div class="container">
    <h1 class="vtxt"><@spring.message 'resume'/></h1>


    <ul>
        <li><span class="publisher"><@spring.message 'category'/></span> ${resume.category}</li>
        <li><span class="publisher"><@spring.message 'resume.name'/></span> ${resume.job}</li>
        <li><span class="publisher"><@spring.message 'salary'/></span> ${resume.expectedSalary}</li>

    </ul>

    <#if resume.education??>
        <h1 class="vtxt"><@spring.message 'education'/></h1>
        <ul>
            <li><span class="publisher"><@spring.message 'university'/></span> ${resume.education.institutionName}</li>
            <li><span class="publisher"><@spring.message 'degree'/> </span>${resume.education.degree} </li>
            <li><span class="publisher"><@spring.message 'start.date'/> </span> ${resume.education.startDate}</li>
            <li><span class="publisher"><@spring.message 'end.date'/></span>${resume.education.endDate}</li>
        </ul>
    <#else>
        <a href="/resumes/${resume.id}/education">
            <div>
                <button class="btn btn-primary" id="addEducationBtn"><@spring.message 'add.btn'/></button>
            </div>
        </a>
    </#if>


    <#if resume.jobExperience??>
        <h1 class="vtxt"><@spring.message 'experience'/></h1>
        <ul>
            <li><span class="publisher"><@spring.message 'position'/></span> ${resume.jobExperience.position}</li>
            <li><span class="publisher"><@spring.message 'start.date'/></span>${resume.jobExperience.startDate}</li>
            <li><span class="publisher"><@spring.message 'end.date'/></span>${resume.jobExperience.endDate}</li>
        </ul>
    <#else>
        <div>
            <a href="/resumes/${resume.id}/experience">
                <button class="btn btn-primary" id="addEducationBtn"><@spring.message 'add.btn'/></button>
            </a>
        </div>
    </#if>
    <#if resume.contacts?size == 0>
        <a href="/resumes/${resume.id}/contacts">
            <button class="btn btn-primary" id="addEducationBtn"><@spring.message 'add.btn'/></button>
        </a>
    <#else>
        <h1 class="vtxt"><@spring.message 'contacts'/></h1>
        <#list resume.contacts as contact>
            <li><span class="publisher">${contact.type}</span> ${contact.value}</li>
        </#list>
    </#if>

</div>
<script>
    document.getElementById('logout').addEventListener('click',()=>{
        localStorage.removeItem('user')
    })
</script>

</body>
</html>
