<?php
$errorMsg='';
$emptyMSg='';
if(isset($_POST['login']))
{
  $email=$_POST['email'];
  $password=$_POST['password'];
  if(empty($email) && empty($password))
  {
    $emptyMSg = "Email & Password are required!";
  } 

  if($email=="admin@ewsb.in" && $password=="Admin@ewsb") 
  {
    header("location:index.php");
  }
  else
  {
    $errorMsg="Check your email and password";
  }
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>EWSB ADMIN LOGIN</title>
    <!-- HTML5 Shim and Respond.js IE10 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 10]>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="description" content="Datta Able Bootstrap admin template made using Bootstrap 4 and it has huge amount of ready made feature, UI components, pages which completely fulfills any dashboard needs." />
    <meta name="keywords" content="admin templates, bootstrap admin templates, bootstrap 4, dashboard, dashboard templets, sass admin templets, html admin templates, responsive, bootstrap admin templates free download,premium bootstrap admin templates, datta able, datta able bootstrap admin template, free admin theme, free dashboard template"/>
    <meta name="author" content="CodedThemes"/>

    <!-- Favicon icon -->
    <link rel="icon" href="assets/images/logo.png" type="image/x-icon">
    <!-- fontawesome icon -->
    <link rel="stylesheet" href="assets/fonts/fontawesome/css/fontawesome-all.min.css">
    <!-- animation css -->
    <link rel="stylesheet" href="assets/plugins/animation/css/animate.min.css">
    <!-- vendor css -->
    <link rel="stylesheet" href="assets/css/style.css">

</head>

<body>
    <div class="auth-wrapper">
        <div class="auth-content">
            <div class="auth-bg">
                <span class="r"></span>
                <span class="r s"></span>
                <span class="r s"></span>
                <span class="r"></span>
            </div>
            <form method="POST">
            <div class="card shadow-5" style="background:#E9F6F9;border-radius:20px;">
                <div class="card-body text-center">
                    <div class="mb-4">
                        <img src="logo.png" style="width:80px; height:80px; "></img>
                    </div>
                    <h3 class="mb-4" style="color:#017CFF;"><b>EWSB ADMIN PORTAL Login</b></h3>
                    <label class="text-danger"><?=$emptyMSg?></label>
                    <div class="input-group mb-3">
                         
                      <input type="email" name="email" class="form-control" placeholder="Email" >
                       <div class="input-group-prepend">
                           <span class="input-group-text border-left-0"><i class="feather icon-at-sign"></i></span>
                        </div>
                    </div>
                    <div class="input-group mb-4">
                        
                        <input type="password" name="password" class="form-control" placeholder="password">
                        <div class="input-group-prepend">
                           <span class="input-group-text border-left-0"><i class="feather icon-lock"></i></span>
                        </div>
                    </div>
                    <div class="form-group text-left">
                        <div class="checkbox checkbox-fill d-inline">
                            
                            <label for="checkbox-fill-a1" class="cr text-danger"> <?=$errorMsg?> </label>
                        </div>
                    </div>
                    <button class="btn btn-primary shadow-2 mb-4" name="login">Login</button>
                    <p class="mb-0 text-muted">Don’t have an account? <a href="auth-signup.html">Signup</a></p>
                </div>
            </div>
            </form>
        </div>
    </div>

    <!-- Required Js -->
<script src="assets/js/vendor-all.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
