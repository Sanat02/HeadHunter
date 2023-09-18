<#import "spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h2>Forgot Password</h2>
<#if error??>
<b class="text-danger text-center">${error}</b>
</#if>
<#if message??>
<b class="text-warning text-center">${message}</b>
</#if>
<div class="py-5 h-100">
    <div class="row d-flex align-items-center justify-content-center h-100">
        <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
            <form action="/forgot" method="post">
                <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>
            <div class="form-outline  border border-secondary rounded p-3">
                <div>
                    <p><@spring.message 'send.email'/></p>
                </div>
                <div>
                    <p>
                        <input type="email" name="email" class="form-control" placeholder="<@spring.message 'text.email'/>" required autofocus/>
                    </p>
                    <p class="text-center">
                        <input type="submit" value="<@spring.message 'send'/>" class="btn btn-primary" />
                    </p>
                </div>
            </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>