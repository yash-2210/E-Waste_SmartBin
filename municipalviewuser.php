<?php

require("conncet.php");
require("sidebar.php");

require("topbar.php");

$sql = "SELECT * FROM municipal";

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
 <table class="table table-bordered" id="dataTable" width="10px" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Municipal_Id</th>
                      <th>Password</th>
                      <th>Department_Name</th>
                      <th>Contact_No</th>
                      <th>Address</th>
                      <th>City</th>
                    </tr>
                  </thead>
                  <tbody>
                   <?php while($user = mysqli_fetch_assoc($response)){ ?>

                    <tr>
                      <td><?=$user['municipal_id']?></td>
                      <td><?=$user['password']?></td>
                      <td><?=$user['department_name']?></td>
                      <td><?=$user['contact_no']?></td>
                      <td><?=$user['address']?></td>
                      <td><?=$user['city']?></td>

                      
                    </tr>
                    <?php
                  }
                    ?>
                  
         
                  </tbody>
                </table>
                </div>
                </body>
                </html>