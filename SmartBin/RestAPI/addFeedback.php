<?php

require('dbconn.php');

$user_id = empty($_POST['user_id']) == 1 ? '' : $_POST['user_id'];
$rating = empty($_POST['rating']) == 1 ? '' : $_POST['rating'];
$comment = empty($_POST['comment']) == 1 ? '' : $_POST['comment'];


if($user_id == "" || $rating=="" || $comment==""){	
	$message='empty field found';
	$response['message']=$message;
	$response['success']=FALSE;
	$response['data']=NULL;
	echo json_encode($response);
}else{	

	$sqlItem = "INSERT INTO `feedback`(`user_id`, `Rating`, `Description`) VALUES ('".$user_id."', '".$rating."', '".$comment."')";


	// print_r($sqlItem);
	// exit();

	$item_result=mysqli_query($con,$sqlItem);		

	if ($item_result != 0) {
		$message = 'Feedback Added Succesfully';
		$response['message']= $message;
		$response['success']=TRUE;
	}else{
		$message='feedback not inserted';
		$response['message']=$message;
		$response['success']=FALSE;
	}
	
	echo json_encode($response);

}
mysqli_close($con);

?>