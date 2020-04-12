<?php

header('Content-Type: application/json');

include 'dbconn.php';

$target_dir = 'C:/xampp/htdocs/SmartBin/upload/userphoto/';
$userId = $_POST['userid'];

$target_file = $target_dir . basename($_FILES["image"]["name"]);

//status brbr 6 k nai
$message;
$uploadOk = 1;
$imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));
// Check if image file is a actual image or fake image
$check = getimagesize($_FILES["image"]["tmp_name"]);
if($check !== false) {
        // echo "File is an image - " . $check["mime"] . ".";
	$uploadOk = 1;
} else {
	$message =  "File is not an image.";
	$uploadOk = 0;
}

// Check if file already exists
if (file_exists($target_file)) {
	$message = "Sorry, file already exists.";
	$uploadOk = 0;
}
// Check file size
if ($_FILES["image"]["size"] > 500000) {
	$message = "Sorry, your file is too large.";
	$uploadOk = 0;
}
// Allow certain file formats
if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
	&& $imageFileType != "gif" ) {
	$message = "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
$uploadOk = 0;
}
$temp = explode(".", $_FILES["image"]["name"]);
$imagename = "ewsb_". $userId . '.' . end($temp);
$newfilename = $target_dir . $imagename;

if (move_uploaded_file($_FILES["image"]["tmp_name"], $newfilename)) {
	$message = "Profile Photo Uploaded.";
	$checkQuery = "UPDATE `users` SET `profile_image`='"."/upload/userphoto/" . $imagename."' where id=".$userId;
	$result = mysqli_query($con, $checkQuery);

		// echo "The file ". basename( $_FILES["profile_photo"]["name"]). " has been uploaded.";
	$uploadOk = 1;
} else {
	$message = "Sorry, there was an error uploading your file.";
	$uploadOk = 0;
}

if ($uploadOk == 1) {
	$status = TRUE;
}else{
	$status = FALSE;
}

echo json_encode(array("success" => $status ,"message" => $message,"profile_photo" => "upload/userphoto/". $imagename));


function compressImage($source, $destination, $quality) {

	$info = getimagesize($source);

	if ($info['mime'] == 'image/jpeg') 
		$image = imagecreatefromjpeg($source);

	elseif ($info['mime'] == 'image/gif') 
		$image = imagecreatefromgif($source);

	elseif ($info['mime'] == 'image/png') 
		$image = imagecreatefrompng($source);

	imagejpeg($image, $destination, $quality);

	return $destination;

}

?>