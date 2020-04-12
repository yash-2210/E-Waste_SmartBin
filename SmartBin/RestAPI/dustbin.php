<?php
    require_once 'dbconn.php';
    
    $bin_quer = " SELECT * FROM smartdustbin ";

    $response = mysqli_query($con,$bin_quer);
    $dustbinList = array();

    if($response->num_rows>0){
        while($row = mysqli_fetch_assoc($response))
        {
            array_push($dustbinList,$row);    
        }
        $result['success'] = true;
        $result['message'] = "You are here";
        $result['data'] = $dustbinList;
    }
    else
    {
        $result['success'] = false;
        $result['message'] = "NO data found";
        $result['data'] = array();
    }
    echo json_encode($result);
?>