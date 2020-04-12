<?php
require("sidebar.php");
require("topbar.php");

require("conncet.php");
if(isset($_POST['submit']))
{

    $b_l=$_POST['bin_longitude'];
    $b_la=$_POST['bin_latitude'];
    $a_n=$_POST['area_name'];
    $d_s=$_POST['dustbin_storage'];
    $q_code=$_POST['qr_code'];

    $insert_INFO="insert into smartdustbin (bin_longitude,bin_latitude,area_name,dustbin_storage,qr_code) VALUES('$b_l','$b_la','$a_n','$d_s','$q_code')";
    if(!mysqli_query($conn,$insert_INFO))
    {
        echo "<script>alert('Error in Inserted')</script>";
    }
    else
    {
        echo "<script>alert('SmartBin Inserted')</script>";
    }
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Smart Dustbin|Add Smart Bin</title>
    <!-- HTML5 Shim and Respond.js IE10 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 10]>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	0	<![endif]-->
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
    <!-- [ Pre-loader ] start -->
    <div class="loader-bg">
        <div class="loader-track">
            <div class="loader-fill"></div>
        </div>
    </div>
    <!-- [ Pre-loader ] End -->
   
    <!-- [ Main Content ] start -->
    <div class="pcoded-main-container">
        <div class="pcoded-wrapper">
            <div class="pcoded-content">
                <div class="pcoded-inner-content">
                    <!-- [ breadcrumb ] start -->
                                
                          
                    <!-- [ breadcrumb ] end -->
                        <div class="page-wrapper">
                            <!-- [ Main Content ] start -->
                            <div class="row">
                                <div class="col-xl-12">
                                
                                    <div class="card card-border-c-yellow p-10">
                                        <h4 class="m-b-10">Update SmartBin</h4>
                                    </div>
                                   
                                
                                  
                                    <div class="card">

                                        <div class="card-body">
                                            <!-- <h5>Form controls</h5> -->
                                            <hr>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <form method="POST">
                                                      
                                                        <div class="form-group"> 
                                                            <label for="exampleInputEmail1">Bin Longitude</label>
                                                            <input type="text" class="form-control" name="bin_longitude" placeholder="Enter bin longitude">
                                                            <!-- <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small> -->
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="exampleInputPassword1">Bin Latitude</label>
                                                            <input type="text" class="form-control"name="bin_latitude" placeholder="Enter bin latitude">
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="exampleInputPassword1">Area Name</label>
                                                            <input type="text" class="form-control" name="area_name"  placeholder="Enter area name">
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="exampleInputPassword1">Dustbin_Storage</label>
                                                            <input type="text" class="form-control" name="dustbin_storage" placeholder="Enter dustbin storage">
                                                        </div>

                                                        <div class="form-group">
                                                            <label class="text-c-red" >Select path of Qrcode (e.g. /SmartBin/Qr_Code/filname.png)</label>
                                                            <input type="text" class="form-control" name="qr_code" placeholder="provide path for QR code Image">
                                                        </div>

                                                        <!-- <div class="form-group form-check">
                                                            <input type="checkbox" class="form-check-input" id="exampleCheck1">
                                                            <label class="form-check-label" for="exampleCheck1">Check me out</label>
                                                        </div> -->
                                                        <input type="submit" name="submit" value="Update!" class="btn btn-primary" ></button>
                                                        
                                                    </form>
                                                </div>
                                               
                                            </div>
                                          


</body>



</html>
