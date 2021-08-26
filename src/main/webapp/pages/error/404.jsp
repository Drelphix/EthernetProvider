<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>404 Error</title>
    <script type="text/javascript">
    function Redirect()
    {
        window.location="/controller?command=tariff_list";
    }
    document.write("You will be redirected to main page in 5 seconds");
    setTimeout('Redirect()', 5000);


    </script>
</head>
<body>
<h1>Oops, we can't find this page, but we promise we'll find it!</h1>
<a href="/controller?command=tariff_list">Redirect to main page.</a>
</body>
</html>