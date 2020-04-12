<?php

    require("conncet.php");
    require("sidebar.php");
    require("topbar.php");
   
    $user_id=$_GET['userId'];
    $del="DELETE FROM users WHERE id=$user_id";
    
    if(mysqli_query($conn,$del))
    {
      echo "<script>alert('User Removed.')</script>";
    }
    else
    {
      echo "<script>alert('Error In Delete.')</script>";
    }
    


?>