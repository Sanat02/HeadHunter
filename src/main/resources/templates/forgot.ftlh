<#import "spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .container {
            margin-top: 5%;
        }

        .form-container {
            background-color: #ffffff;
            border: 1px solid #ced4da;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
        }

        #warn {
            background-color: white;
            border-color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-7 col-lg-5 col-xl-5">
            <div class="form-container">
                <h2>Forgot Password</h2>
                <div id="warn" class="alert alert-danger text-center"></div>
                <form action="/forgot" method="post">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>
                    <div class="form-group">
                        <label for="email"><@spring.message 'text.email'/></label>
                        <input type="email" name="email" id="email" class="form-control"
                               placeholder="<@spring.message 'text.email'/>" required autofocus/>
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary"><@spring.message 'send'/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function getQueryParam(name) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(name);
    }

    const error = getQueryParam();
    if (error) {
        document.getElementById("warn").style.backgroundColor = 'pink';
        document.getElementById("warn").textContent = `Error: Invalid email address!`;
    }
</script>
</body>
</html>
