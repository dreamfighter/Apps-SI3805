<?php
	$data = array(
  "registration_ids"=> array(
    "dLnomXhQIls:APA91bGOzfUEubf5g8DioIqjVHM72iHSoSsCaZHzHOQBNQi_PrkSnVJmURn728JCA7Ggg67kvPZYZAQ74E_k1me1Rfs6TrbPJhLEMaLyEMqWw5AyhahZrptCnZ__tT-nYko0tp5fQDvb"
  ),
   "data" => array(
      "message" => "Hi therei hello world",

      "userId" => "113060038"
    ),
  "content_available"=> true
);
	$ch = curl_init(); 
        curl_setopt($ch, CURLOPT_URL, "https://gcm-http.googleapis.com/gcm/send");
	curl_setopt($ch, CURLOPT_POST, 1); 
        //curl_setopt($ch, CURLOPT_HEADER, TRUE); 
        //curl_setopt($ch, CURLOPT_NOBODY, TRUE); // remove body 
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE); 
	curl_setopt($ch, CURLOPT_HTTPHEADER, array("Content-Type: application/json","Authorization:key=AIzaSyD3EgPEbJylGq53CyvVkKjbKx6iFPb1Xe0"));
	curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        $head = curl_exec($ch); 
	echo "success!".$head;
?>
