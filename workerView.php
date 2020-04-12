<?php

require("conncet.php");
require("sidebar.php");

require("topbar.php");

$sql = "SELECT * FROM workers";

    $response = mysqli_query($conn, $sql);

?>
<html>
<body>
<style>
.ta
{
    width:50px;
    height:10px;
    margin-left:270px;
    
}
</style>
<div class="ta">
 <table class="table table-bordered" id="dataTable" width="500" cellspacing="0">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Worker_Name</th>
                      <th>Email</th>
                      <th>Phone Number</th>
                      <th>Address</th>
                    </tr>
                  </thead>
                  <tbody>
                   <?php while($user = mysqli_fetch_assoc($response)){ ?>

                    <tr>
                      <td><?=$user['worker_id']?></td>
                      <td><?=$user['worker_name']?></td>
                      <td><?=$user['worker_email']?></td>
                      <td><?=$user['contact_number']?></td>
                      <td><?=$user['worker_area']?></td>

                    </tr>
                    <?php
                  }
                    ?>
                  
         
                  </tbody>
                </table>
                </div>
                </body>
                </html>