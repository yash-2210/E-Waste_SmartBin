<?php
require("sidebar.php"); 
require("topbar.php"); 


?> 

<!DOCTYPE html>
<html lang="en">

<head>
    <title>SMART DUSTBIN</title>
<style type="text/css">
.card-block
{
    margin-top: 300px;
}


</style>
<h1><center><b>MUNICIPAL CORPORATION</b><center></h1>
</head>

    <div class="card-block"> 
            <form  >
                <center>
                
                <input type="button" class="btn btn-primary" value="VIEW" onclick="window.location.href='http://localhost/hachthone/municipalviewuser.php'"/>
                <input type="button" class="btn btn-primary" value="ADD" onclick="window.location.href=''http://localhost/webclient/viewuser.php'"/>
                <input type="button" class="btn btn-primary" value="UPDATE" onclick="window.location.href=''http://localhost/webclient/updateuser.php'"/>
                <input type="button" class="btn btn-primary" value="REMOVE" onclick="window.location.href=''http://localhost/webclient/deleteuser.php'"/>
            </center>

            </form>
    </div>
<script src="assets/js/vendor-all.min.js"></script>
	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/pcoded.min.js"></script>
 

</body>
</html>
