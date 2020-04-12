package com.example.smartbin.retrofit;

public interface ServiceURL {
    String baseurl = "http://192.168.1.201:1022/SmartBin/RestAPI/";
    String imageurl = "http://192.168.1.201:1022/SmartBin/";


    String checkLogin = "userLogin.php";
    String checkRegistration = "Registration.php";
    String binList = "dustbin.php";
    String updateReward = "updateRwd.php";
    String feedback = "addFeedback.php";
    String updateUserPhoto = "updateUserPhoto.php";
   /* String cafeSearchList = "getSearchCafeList.php";
    String cafeTableList = "getCafeTable.php";
    String cafeMenuList = "getMenuList.php";
    String addOrder = "addOrder.php";
    String orderList = "getOrderList.php";
    String offerList = "getOfferList.php";
    String orderDetails = "getOrderDetails.php";
    String updateOrderStatus = "updateOrderStatus.php";*/

    //model
    String bin = "bin";
    String user = "user";

    //keys
    String userid = "userid";
    String reward = "reward";
    String username = "username";
    String email = "email";
    String password = "password";
    String phone = "phone";
    String status = "status";
    String rating = "rating";
    String comment = "comment";
    String user_id = "user_id";
    String image = "image";

    /*String keyword = "keyword";
    String cafe_id = "cafe_id";
    String order_id = "order_id";
    String order_status = "order_status";
    String table_id = "table_id";*/


}
