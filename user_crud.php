<?php

require("conncet.php");
//require("sidebar.php");

//require("topbar.php");

$sql = "SELECT * FROM users";

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
                    
                    <!-- [ breadcrumb ] end -->
                    <div class="main-body">
                        <div class="page-wrapper">
                            <!-- [ Main Content ] start -->
                            <div class="row">
                                <!-- [ basic-table ] start -->
                                <div class="col-xl-12">
                                <div class="card-header card-border-c-blue f-20 p-10 m-b-10" style="border-radius:10px;">
                                      <h4 class="m-l-10">User Details</h4>
                                 </div>
                                 <div class="main-search text-right"><i class="feather icon-search f-20"></i>
                                     <input class="border-0 p-2 bg-c-green" style="border-radius: 10px;" type="text" id="myInput" onkeyup="myFunction()" class="form-control" placeholder="Search . . .">
                                    </div>
                                        <div class="card-block shadow-lg m-t-10">
                                            <div class="table-responsive" style="border-radius:15px;">
                                                <table id="myTable" class="table table-hover nowrap table-striped table-bordered">
                                                    <thead>
                                                    <tr>
                                                        <th>#</th>
                                                        <th>Profile Picuture</th>
                                                        <th>Name</th>
                                                        <th>Email</th>
                                                        <th>Contact No</th>
                                                        <th>Rewards</th>
                                                        <th>Action</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <?php while($user = mysqli_fetch_assoc($response)){ ?>

                                                          <tr>

                                                            <td><?=$user['id']?></td>
                                                            <td><img src=/SmartBin/<?=$user['profile_image']?> style="width: 60px; height: 60px;"/></td>
                                                            <td><?=$user['name']?></td>
                                                            <td><?=$user['email_id']?></td>
                                                            <td><?=$user['mobile_no']?></td>
                                                            <td><?=$user['Rewards']?></td>
                                                           <td> 
                                                                <a href="http://localhost:1022/AdminPortal/updateuser.php?userId=<?=$user['id']?>" class="btn btn-success btn-circle btn-lg">
                                                                     <i class="fas fa-user-edit"></i>
                                                                </a>
                                                                <a href="http://localhost:1022/AdminPortal/deleteuser.php?userId=<?=$user['id']?>" class="btn btn-danger btn-circle btn-lg">
                                                                    <i class="fas fa-trash"></i>
                                                                 </a></td>                                                        
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
    <script>
		function myFunction() {
		  // Declare variables
		  var input, filter, table, tr, td, i, txtValue;
		  input = document.getElementById("myInput");
		  filter = input.value.toUpperCase();
		  table = document.getElementById("myTable");
		  tr = table.getElementsByTagName("tr");

		  // Loop through all table rows, and hide those who don't match the search query
		  for (i = 0; i < tr.length; i++) {
		    td = tr[i].getElementsByTagName("td")[0];
		    if (td) {
		      txtValue = td.textContent || td.innerText;
		      if (txtValue.toUpperCase().indexOf(filter) > -1) {
		        tr[i].style.display = "";
		      } else {
		        tr[i].style.display = "none";
		      }
		    }
		  }
		}
</script>

</body>
</html>
