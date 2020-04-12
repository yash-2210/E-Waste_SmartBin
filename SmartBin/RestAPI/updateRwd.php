<?php
//echo phpinfo();
if ($_SERVER['REQUEST_METHOD']=='POST') 
{
    $email = $_POST['email'];
    $Reward = $_POST['Reward'];

    include ('dbconn.php');

    $sql = " Update users set Rewards=$Reward where id='$email' ";

    if(mysqli_query($con, $sql))
    {
        $flag = 0;
        $result['status'] = false;
    }
    else
    {
        $flag=1;
        $result['status']=true;
    }
    echo json_encode($result);
    mysqli_close($con);
}

?>