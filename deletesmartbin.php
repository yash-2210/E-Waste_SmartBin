<?php

    require("conncet.php");
    require("sidebar.php");
    require("topbar.php");

  
    $bin_id=$_GET['bin_id'];
    $del="DELETE FROM smartdustbin WHERE bin_id=$bin_id";
    
    
    if(mysqli_query($conn,$del))
    {
      echo "<script>alert('Bin Deleted.')</script>";
    }
    else
    {
      echo "<script>alert('Error In Delete.')</script>";
     
    }

//echo "hi";
    //$user =mysqli_fetch_assoc($response);
    //print_r($user);

?>
