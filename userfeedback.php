<?php

require("conncet.php");
//require("sidebar.php");

//require("topbar.php");

$sql = "SELECT * FROM feedback";

    $response = mysqli_query($conn, $sql);

?>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Smart Dustbin Users</title>
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
     <link rel="icon" href="logo.png" type="image/x-icon"> -->
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

    
    <?php
    require("sidebar.php");
    ?>

    <!-- [ Header ] start -->
    <?
    require("topbar.php");
    ?>
   

    <!-- [ Main Content ] start -->
    <section class="pcoded-main-container">
        <div class="pcoded-wrapper">
            <div class="pcoded-content">
                <div class="pcoded-inner-content">
                    <!-- [ breadcrumb ] start -->
                    <div class="page-header">
                        <div class="page-block">
                            <div class="row align-items-center">
                                <div class="col-md-12">
                                   
                                    <ul class="breadcrumb">
                                      
                                        <h3 class="breadcrumb-item"><b>User Feedback</b></h3>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- [ breadcrumb ] end -->
                    <div class="main-body">
                        <div class="page-wrapper">
                            <!-- [ Main Content ] start -->
                            <div class="row">
                                <!-- [ basic-table ] start -->
                                <div class="col-xl-12">
                                    <div class="card">
                                       
                                        <div class="card-block table-border-style">
                                            <div class="table-responsive">
                                                <table class="table">
                                                    <thead>
                                                    <tr>
                                                    <th>User_Id</th>
                                                        
                                                    <th>Description</th>
                            
                                                  
                                                    <th>Rating</th>
                                                                                                  </tr>
                                                      </tr>
                                                    </thead>
                                                    <tbody>
                                                    <?php while($user = mysqli_fetch_assoc($response)){ ?>

                                                          <tr>

                                                            <td><?=$user['id']?></td>
                                                            <td><?=$user['Description']?></td>
                                                            <td><?=$user['Rating']?></td>
                                                            
                                                           <!-- <td> 
                                                                <a href="http://localhost/AdminPortal/updateuser.php?userId=<?=$user['id']?>" class="btn btn-success btn-circle btn-lg">
                                                                     <i class="fas fa-user-edit"></i>
                                                                </a>
                                                                <a href="http://localhost:/AdminPortal/deleteuser.php?user_id=1" class="btn btn-danger btn-circle btn-lg">
                                                                    <i class="fas fa-trash"></i>
                                                                 </a></td>


                                                            --> 
                                                          </tr>
                                                          <?php
                                                          }
                                                          ?>

                                                        
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- [ basic-table ] end -->

                               
                                </div>
                                <!-- [ stiped-table ] end -->
                            </div>
                            <!-- [ Main Content ] end -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section> 
   
    <script src="assets/js/vendor-all.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/pcoded.min.js"></script>

</body>
</html>
