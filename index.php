<?php
  require("conncet.php");
    $sql = "SELECT count(*) from users";
    $response = mysqli_query($conn, $sql);
    $row =mysqli_fetch_assoc($response);
    $totalUser = $row['count(*)'];

    $countBinQue = "SELECT count(*) from smartdustbin";
    $response = mysqli_query($conn, $countBinQue);
    $row =mysqli_fetch_assoc($response);
    $totalSmartBin = $row['count(*)'];
?>




<!DOCTYPE html>
<html lang="en">

<head>
    <title>EWSB Admin Portal</title>
    <!-- HTML5 Shim and Respond.js IE11 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 11]>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="description" content="Free Datta Able Admin Template come up with latest Bootstrap 4 framework with basic components, form elements and lots of pre-made layout options" />
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
    <?php
        require ("sidebar.php");
        require ("topbar.php");
    ?>
    <!-- [ Main Content ] start -->
    <div class="pcoded-main-container">
        <div class="pcoded-wrapper">
            <div class="pcoded-content">
                <div class="pcoded-inner-content">
                    <!-- [ breadcrumb ] start -->

                    <!-- [ breadcrumb ] end -->
                    <div class="main-body">
                        <div class="page-wrapper">
                            <!-- [ Main Content ] start -->
                            <div class="row">
                                <!--[ user card ] start-->
                                <div class="col-xl-4 col-md-6">
                                    <div class="shadow p-3 card Recent-Users" style="background:#EFF7FF; border-radius:20px;">
                                        <div class="card-header">
                                            <span class="d-block"><h5><b class="text-c-blue" style="font-size: 22px; font-stretch: ultra-expanded;">Users</b></h5></span>
                                        </div>
                                        <div class="card-block">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-auto">
                                                    <i class="feather icon-users f-30 text-c-blue"></i>
                                                </div>
                                                <div class="col text-c-blue">
                                                    <h4 class="f-24" style="color:#017CFF; font-weight: bold;"> Users : <?=$totalUser?></h4>
                                                
                                                </div>
                                            </div>
                                        </div>            
                                    </div>
                                </div>
                                <!--[ user card end ] end-->
                                <!--[ Dustbin Card ] start-->
                                <div class="col-xl-4 col-md-6">
                                    <div class="shadow p-3 card Recent-Users" style="background:#FFE0E6; border-radius:20px;"> 
                                        <div class="card-header">
                                            <span class=""><h5><b class="text-c-red" style="font-size:22px;">Smart Dustbins</b></h5></span>
                                        </div>
                                        <div class="card-block">
                                            <div class="row d-flex align-items-center">
                                                <div class="col-auto">
                                                    <i class="feather icon-map-pin f-30 text-c-red"></i>
                                                </div>
                                                <div class="col">
                                                    <h2 class="f-22" style="color:#FF2250;font-weight: bold;">Smart Bins : <?=$totalSmartBin?></h2>
                                                </div>
                                            </div>
                                        </div>            
                                    </div>
                                </div>
</body>
</html>
