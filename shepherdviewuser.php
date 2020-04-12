<?php

require("conncet.php");
require("sidebar.php");

require("topbar.php");

$sql = "SELECT * FROM shepherd";

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
                      <th>Shepherd_Id</th>
                      <th>UserName</th>
                      <th>Password</th>
                      <th>Shepherd_Name</th>
                    
                      <th>Email</th>
                      <th>Contact_No</th>
                      <th>Address</th>
                      <th>City</th>
                    </tr>
                  </thead>
                  <tbody>
                   <?php while($user = mysqli_fetch_assoc($response)){ ?>

                    <tr>
                    
                      <td><?=$user['shepherd_id']?></td>
                
                      <td><?=$user['user_name']?></td>
                      <td><?=$user['password']?></td>
                      <td><?=$user['shepherd_name']?></td>
                      
                      <td><?=$user['email']?></td>
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