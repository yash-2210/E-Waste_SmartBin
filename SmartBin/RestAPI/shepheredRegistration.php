<?php
require_once 'dbconn.php';

if ($_SERVER['REQUEST_METHOD'] =='POST')
{
    $flag = 1;
    $name = $_POST['sh_username'];
    $email = $_POST['sh_email'];
    $mobile_number=$_POST['sh_phone'];
    $password = $_POST['sh_password'];
   
  //  $password = password_hash($password, PASSWORD_DEFAULT);
  $user_id="";

    $sql = "INSERT INTO shepherds (password, shepherd_name,email ,contact_no, address) VALUES ('$name', '$email','$mobile_number', '$password')";
    $existQue = " SELECT * from shepherds where email='$email' ";
    $check_mobile_Que = " SELECT * from shepherds where contact_no='$mobile_number' ";
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

	$query="SELECT * FROM shepherds WHERE id ='".$user_id."'";
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