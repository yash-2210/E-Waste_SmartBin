<?php
    require("conncet.php");
    $sql = "SELECT * FROM smartdustbin";
    $response = mysqli_query($conn, $sql);
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Smart Dustbin</title>
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
    <link rel="icon" href="logo.png" type="image/x-icon">
    <!-- fontawesome icon -->
    <link rel="stylesheet" href="assets/fonts/fontawesome/css/fontawesome-all.min.css">
    <!-- animation css -->
    <link rel="stylesheet" href="assets/plugins/animation/css/animate.min.css">
    <!-- vendor css -->
    <link rel="stylesheet" href="assets/css/style.css">br

</head>

<body>
    <!-- [ Pre-loader ] start -->
    <div class="loader-bg">
        <div class="loader-track">
            <div class="loader-fill"></div>
        </div>
    </div>
   
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
                                <div class="card-header card-border-c-blue f-20"style="border-radius:10px;">
                                    <h4 class="m-l-10">Smart Dustbin Details</h4>
                                </div>
                                   
                                    <a href="addsmartbin.php" class="p-l-10">
                                        <input type="button" name="submit" value="Add Dustbin" class="btn btn-outline-info m-t-15" >
                                    </a>
                                    <div class="main-search text-right"><i class="feather icon-search f-20"></i>
                                     <input class="border-0 p-2 bg-c-green" style="border-radius: 10px;" type="text" id="myInput" onkeyup="myFunction()" class="form-control" placeholder="Search . . .">
                                    </div>
                                <form method="post">
                                        
                                        <div class="card-block shadow-lg m-t-10">
                                            <div class=" table-responsive" style="border-radius:15px;">
                                                <table id="myTable" class="table dataTables_paginate table-bordered table-striped table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th>#</th>
                                                            <th>Longitude</th>
                                                            <th>Latitude</th>
                                                            <th>Area</th>
                                                            <th>Dustbin_Storage</th>
                                                            <th>Update Storage</th>
                                                            <th>QR CODE </th>
                                                            <th>Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <?php while($bin = mysqli_fetch_assoc($response)){ ?>
                                                      <tr>
                                                        <td><?=$bin['bin_id']?></td>
                                                        <td><?=$bin['bin_longitude']?></td>
                                                        <td><?=$bin['bin_latitude']?></td>
                                                        <td><?=$bin['area_name']?></td>
                                                        <td><?=$bin['dustbin_storage']?></td>
                                                        <td><?=$bin['update_storage']?></td>
                                                        <td><img src=<?=$bin['qr_code']?> style="width: 60px; height: 60px;"/></td>
                                                        <td>
                                                            <a href="http://localhost:1022/AdminPortal/updatesmartbin.php?bin_id=<?=$bin['bin_id']?>">
                                                                <i class="feather icon-edit-2 f-26"></i>
                                                            </a>
                                                            <a href="http://localhost:1022/AdminPortal/deletesmartbin.php?bin_id=<?=$bin['bin_id']?>">
                                                                <i class="fas fa-trash text-c-red f-26"></i>
                                                            </a>
                                                        </td>  
                                                      </tr>
                                                      <?php
                                                      }
                                                      ?>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    </form>
                                </div>
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
    <!-- [ Main Content ] end -->

    <!-- Warning Section Starts -->
    <!-- Older IE warning message -->
    <!--[if lt IE 11]>
        <div class="ie-warning">
            <h1>Warning!!</h1>
            <p>You are using an outdated version of Internet Explorer, please upgrade
               <br/>to any of the following web browsers to access this website.
            </p>
            <div class="iew-container">
                <ul class="iew-download">
                    <li>
                        <a href="http://www.google.com/chrome/">
                            <img src="assets/images/browser/chrome.png" alt="Chrome">
                            <div>Chrome</div>
                        </a>
                    </li>
                    <li>
                        <a href="https://www.mozilla.org/en-US/firefox/new/">
                            <img src="assets/images/browser/firefox.png" alt="Firefox">
                            <div>Firefox</div>
                        </a>
                    </li>
                    <li>
                        <a href="http://www.opera.com">
                            <img src="assets/images/browser/opera.png" alt="Opera">
                            <div>Opera</div>
                        </a>
                    </li>
                    <li>
                        <a href="https://www.apple.com/safari/">
                            <img src="assets/images/browser/safari.png" alt="Safari">
                            <div>Safari</div>
                        </a>
                    </li>
                    <li>
                        <a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie">
                            <img src="assets/images/browser/ie.png" alt="">
                            <div>IE (11 & above)</div>
                        </a>
                    </li>
                </ul>
            </div>
            <p>Sorry for the inconvenience!</p>
        </div>
    <![endif]-->
    <!-- Warning Section Ends -->

    <!-- Required Js -->


</body>
    <script src="assets/js/vendor-all.min.js"></script>

	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
  
    <script src="assets/js/pcoded.min.js"></script>
    
    <script src="assets/js/dataTables.bootstrap.min.js"></script>
    
    <script src="/js/dataTables.bootstrap4.min.js"></script>
    
    <script src="/assets/js/datatables.min.js"></script>

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

</html>

