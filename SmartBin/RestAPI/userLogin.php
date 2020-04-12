<?php
//echo phpinfo();
if ($_SERVER['REQUEST_METHOD']=='POST') 
{
    $email = $_POST['email'];
    $password = $_POST['password'];

    include ('dbconn.php');

    $sql = " SELECT * FROM users WHERE email_id='$email' ";

    $response = mysqli_query($con, $sql);

    $result = array();
    $result['loginAuth'] = array();
    
    if ( mysqli_num_rows($response) == 1 ) {
        
        $row = mysqli_fetch_assoc($response);
        //print_r($row);

        if ( $password == $row['Password'] ) {
            
            $index['name'] = $row['name'];
            $index['email'] = $row['email_id'];
            $index['id'] = $row['id'];
            $index['profileImage'] = $row['profile_image'];
            $index['rewards'] = $row['Rewards'];
            
            $result['loginAuth'] = $index;
            $result['status'] = true;
            $result['message'] = "Login Successful";
        } 
        else
        {   
            $result['loginAuth'] = null;
            $result['status'] = false;
            $result['message'] = "User Not Found :(\n Check your Email or Password!";
        }

        echo json_encode($result);

        mysqli_close($con);


    }
}
?>