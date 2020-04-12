<?php

$host="localhost";//servername;
$user="root";//username;
$pass="";//password;
$db_name="smart_bin";//database_name;
//$port="2210";//port
$con=mysqli_connect($host,$user,$pass,$db_name,/*$port*/);//for connect to database
if(!$con)
{
  die("Error to connect to database");//program close
}
else
{
}

?>