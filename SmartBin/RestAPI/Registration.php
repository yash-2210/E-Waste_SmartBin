<?php
require_once 'dbconn.php';

if ($_SERVER['REQUEST_METHOD'] =='POST')
{
    $flag = 1;
    $name = $_POST['username'];
    $email = $_POST['email'];
    $mobile_number=$_POST['phone'];
    $password = $_POST['password'];
   
  //  $password = password_hash($password, PASSWORD_DEFAULT);
  $user_id="";

    $sql = "INSERT INTO users (name, email_id, mobile_no, Password) VALUES ('$name', '$email','$mobile_number', '$password')";
    $existQue = " SELECT * from users where email_id='$email' ";
    $check_mobile_Que = " SELECT * from users where mobile_no='$mobile_number' ";
    $existRtn = mysqli_query($con,$existQue);
    $existCheckMobileRtn = mysqli_query($con,$check_mobile_Que);   
    if(mysqli_num_rows($existRtn)==1)
    {
        $flag = 0;
        $result['status'] = false;
        $result['message'] = "User is already Exist"; 
    }
    if(mysqli_num_rows($existCheckMobileRtn)==1)
    {
        $flag = 0;
        $result['status'] = false;
        $result['message'] = "User is already Exist"; 
    }
    
    if($flag==1)
    {
        if ( mysqli_query($con, $sql) )
        {
            $result['status'] = true;
            $result['message'] = "User Created";  
            $user_id = mysqli_insert_id($con);

        } 
        else
        {
            $result['status'] = false;
            $result['message'] = "User Creation Failed";  
        }
    }
    $value = null;

	$query="SELECT * FROM users WHERE id ='".$user_id."'";
	$resultData = mysqli_query($con,$query) or die(mysqli_error($con));


	while ($row = mysqli_fetch_assoc($resultData)) {
		$value = $row;
	// array_push($value,$row); 
    }
    $result['loginAuth'] = $value;

    echo json_encode($result);
    mysqli_close($con);
}
?>