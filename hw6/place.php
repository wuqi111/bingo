  <?php 

            error_reporting(E_ERROR);   
            $keyword="";
            $distance="";
            $category="";
            $newlocation="";
            $keywordErr="";
            $locationErr="";
            $local="checked";
            $random="unchecked";
        
        if ($_SERVER["REQUEST_METHOD"]=="POST")
        {
            if (empty(  $_POST["Keyword"]))
            {
                $keywordErr="please fill out this file.";
            }
          
            
            if(empty($_POST["location"]))
            {
                 $locationErr="please fill out this file.";
            }
        }
        
        if(isset($_POST["search"])){
            $keyword=$_POST["Keyword"];
            $category=$_POST["Category"];
            
            
            if(isset($_POST["Distance"])&& !empty($_POST["Distance"])){
               $distance=$_POST["Distance"]; 
            }else{
                $distance=10;
            }
            
            
            $position=$_POST["location"];
            $newlocation=$_POST["newlocation"];
            $enkeyword =urlencode($keyword);
            $encategory=urlencode($category);
            $endistance=urlencode($distance*1609.34);
            $ennewlocation=urlencode($newlocation);
           global $api_key;
              $api_key ="AIzaSyCySUo-85EpYeYIv_cgnZoBeppOUve67tc";
            
           if($position=="local"){
                $local="checked";
                $random="unchecked";
                $url1='http://ip-api.com/json';
                $urlContent=file_get_contents($url1);
                $urldecodejson=json_decode($urlContent,true);
                $latitude=$urldecodejson["lat"];
                $enlatitude=urlencode($latitude);
                $longitude=$urldecodejson["lon"];
                $enlongitude=urlencode($longitude);
               
            //   $enlatitude=$_REQUEST["lat"];
              // $enlongitude=$_REQUEST["lon"];
            }
            else if($position=="random" && isset($ennewlocation)){
                $random="checked";
                $local="unchecked";
                $url2="https://maps.googleapis.com/maps/api/geocode/json?address={$ennewlocation}&key={$api_key}";
                $urlContent=file_get_contents($url2);
                $urldecodejson=json_decode($urlContent,true);
                $latitude=$urldecodejson["results"]["0"]["geometry"]["location"]["lat"];
                $enlatitude=urlencode($latitude);
                $longitude=$urldecodejson["results"]["0"]["geometry"]["location"]["lng"];
                $enlongitude=urlencode($longitude);
               
            }
       
           // $urlContent=file_get_contents($url)
            
            $urldirect= "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location={$enlatitude},{$enlongitude}&radius={$endistance}&type={$encategory}&keyword={$enkeyword}&key={$api_key}";
            $urlcontenttarget=file_get_contents($urldirect);
            $urldecodetarget=json_decode($urlcontenttarget,true);
            
            //print_r($urldecodetarget);
            
            global $data;
            global $number;
            $number=count($urldecodetarget["results"]);
        //    global $place;
            
            if(is_array($urldecodetarget["results"])){
            foreach($urldecodetarget["results"] as $item){
                $icon=$item["icon"];
                $name=$item["name"];
                $address=$item["vicinity"];
                $place_id=$item["place_id"];
                $newlat=$item["geometry"]["location"]["lat"];
                $newlng=$item["geometry"]["location"]["lng"];
                
                $data[]=array(
                "category"=>$icon,
                "Name"=>$name,
                "Address"=>$address,
                "placeid"=>$place_id,
                 "newlat"=>$newlat,
                "newlng"=>$newlng
                );
            }
                $newdata=json_encode($data,JSON_UNESCAPED_SLASHES);
                
            }
            }     
        
        if(isset($_GET['placeidcc'])){
                   $placeidbb="";
                    $placeidbb=$_GET["placeidcc"];  
       
           
            $photos=array();          
            $reviewes=array();
            
            $photo_review=array();
             $url3="https://maps.googleapis.com/maps/api/place/details/json?placeid={$placeidbb}&key=AIzaSyCySUo-85EpYeYIv_cgnZoBeppOUve67tc";
                $urlplace=file_get_contents($url3);
                $urldeplace=json_decode($urlplace,true);
            
        
            
            for($j=0;$j<5;$j++){
    
                 if(isset($urldeplace["result"]["photos"]["$j"])&&!empty($urldeplace["result"]["photos"]["$j"]))
                 {
                 $photo=$urldeplace["result"]["photos"]["$j"]["photo_reference"];
                
                $photoaa=urlencode($photo);
                 $url4="https://maps.googleapis.com/maps/api/place/photo?maxwidth=750&photoreference={$photoaa}&key=AIzaSyCySUo-85EpYeYIv_cgnZoBeppOUve67tc";
                  
                $picturename="picture".$j.".png"; 
                $urlpicture=file_get_contents($url4);
              //  $decodepicture=json_decode($urlpicture,true);
                file_put_contents($picturename,$urlpicture);
                
                        $photos[]=array(
                        "newphoto"=>$picturename
                        );
                        }
                
              //  print_r($photos);
                    }
       array_push($photo_review,$photos);
            
               for($m=0;$m<5;$m++){
                       if(isset($urldeplace["result"]["reviews"]["$m"])){ 
                        $author=$urldeplace["result"]["reviews"]["$m"]["author_name"];
                        $profile=$urldeplace["result"]["reviews"]["$m"]["profile_photo_url"];
                        $review=$urldeplace["result"]["reviews"]["$m"]["text"];
                            
                        $reviewes[]=array(
                        "author"=>$author,
                        "profile"=>$profile,
                        "newview"=>$review
                        );
                    }  
               }
            
            array_push($photo_review,$reviewes);
         
            $photo_reviewdone=str_replace( "\\/","/",json_encode($photo_review));
            
           
            echo  $photo_reviewdone;
            exit();
        }
               
        ?>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <title>place.php</title>
     <meta charset="utf-8">
     <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <style>
   
        
        .form{
            background-color: rgb(247,247,247);
            border: 2px solid black;
            border-color: rgb(180,180,180);
            margin-left: 400px;
            margin-top: 20px;
            width:640px;
            
        }
        
        .text1{
            font-size: 35px;
            font-style: italic;
            font-family: serif;
            border-bottom: 2px solid black;
            border-bottom-color: rgb(180,180,180);
            padding-left:80px;
            padding-bottom: 10px;
            width: 530px;
            margin-left: 10px;
        }
        
        .text2{
           
            font-size: 20px;
            padding-top:10px;
            padding-left:10px;
            width: 85px;
        }
        
        .text3{
            margin-left: 102px;
            margin-top: -20px;
            position: absolute; 
        }
        
        .text4{
             font-size: 20px;
            padding-top:10px;
            padding-left:10px;
            width: 85px;
        }
        
        .text5{
            margin-left: 102px;
            margin-top: -20px;
            position: absolute; 
        }
        
        .text6{
    
            font-size: 20px;
            padding-top:10px;
            padding-left:10px;
            width: 140px;
        }
        
        .text7{
             margin-left: 150px;
            margin-top: -22px;
            position: absolute; 
        }
    
        .text8{
            font-size: 20px;
             margin-left: 290px;
            margin-top: -22px;
            position: absolute; 
        }
        
        .text9{
            display: inline-block;
            font-size: 20px;
            margin-left: 340px;
            margin-top: -21px;
        }
        
        .text10{
            margin-left: 340px;
        }
    
        .text11{
            margin-left: 60px;
            padding-bottom: 20px;
            padding-top: 10px;
        }
        
        .data1{
            margin-left: 93px;
            width: 1250px;
            margin-top:12px;
        }
        
        .data2{
            position:absolute;
            width: 1250px;
            border: 2px solid black;
            border-color: rgb(180,180,180);
            border-collapse:collapse;
        }
            
        .data3{
            border: 2px solid black;
            border-color: rgb(180,180,180);
            font-size: 20px;
            width: 100px;
            text-align: center
        }
        
        .data4{
            border: 2px solid black;
            border-color: rgb(180,180,180);
            font-size: 20px;
            width: 585px;
            text-align: center;
        }
        
        .data5{
            border: 2px solid black;
            border-color: rgb(180,180,180);
            font-size: 20px;
            width:555;
            text-align: center;
        }
        
        .data6{
            border: 2px solid black;
            border-color: rgb(180,180,180);
        }
        
        a.data7:link {
            text-decoration:none;
            color: black;
            font-family: serif;
            font-weight: 200;
            margin-left: 20px;
        }
        a.data7:visited {
            text-decoration:none;
            color: black;
        }
        a.data7:hover {
            text-decoration:none;
            color: black;
        }
        a.data7:active {
            text-decoration:none;
            color: black;
        }
        
        .data8{
            width: 645px;
            margin-left: 307px;
           
        }
        
        .data9{
           font-size: 20px;
           text-align: center;
          padding-bottom: 20px;
        }
        
        a.data10:link {
            text-decoration:none;
            margin-left: 300px;
        }
        a.data10:visited {
            text-decoration:none;
        }
        a.data10:hover {
            text-decoration:none;
        }
        a.data10:active {
            text-decoration:none;
        }
        
        .data11{
            text-align: center;
            font-size: 20px;
            font-family: serif;
            font-weight: 200;
            margin-left: 300px;
            padding-top: 10px;
        }
        
        .data12{
            position: relative;
            margin-top: 20px;
            width: 785px;
            border: 2px solid black;
            border-color: rgb(225,225,225);
            border-collapse:collapse;
            background-color: rgb(235,235,235);
            margin-left: 250px;
        }
        
        .data13{
            font-weight: 200;
            font-size: 20px;
            text-align: center;
        }
        
        .review1{
            border: 2px solid black;
            border-color: rgb(180,180,180);
            width: 645px;
            border-collapse:collapse;
        }
        
        .review2{
            border: 2px solid black;
            border-color: rgb(180,180,180);
            font-size: 20px;
            height: 40px;
            text-align: center;
        }
        
        .review3{
            border: 2px solid black;
            border-color: rgb(180,180,180);
            font-size: 15px;
            font-weight: 300;
        }
        
        .review4{
            border: 2px solid black;
            border-color: rgb(225,225,225);
            width: 645px;
            text-align: center;
            background-color: rgb(235,235,235);
        }
        
        .photo1{
            border: 2px solid black;
            border-color: rgb(180,180,180);
            width: 650px;
            border-collapse:collapse;
        }
        
        .photo2{
            border: 2px solid black;
            border-color: rgb(180,180,180);
            text-align: center;
            padding-top: 20px;
            padding-bottom: 20px;
        }
        
      
        select option:hover{
            box-shadow: 0 0 10px 100px rgb(206,206,206) inset;
        }
    
        select:focus option:checked{
            box-shadow: 0 0 10px 100px rgb(206,206,206) inset;
        }
    
        
        #mode{
            font-family: serif;
            font-size: 17px;
            background-color: rgb(235,235,235);
            text-align: center;
        }
        
        .select{
            padding-top: 10px;
            padding-bottom: 8px;
        }
        
        </style>
    <body>
        
        
        <script type="text/javascript">
   
        function validation(){
               if(document.getElementById("random").checked){
                    document.getElementById("location").required=true;
               
                }
            else{
   
                document.getElementById("location").required=false;
            }
     
        }
            
            
            table();
            
        function table(){
            var object= <?=$newdata?>;
            console.log(object);
            var j=<?=$number?>;
             var text="";
            
            if(object!=null ){
            root=object.documentElement;
            //var table=document.getElementById('firsttable');
            text+="<table class='data2'>";
            text+="<tr>";
            text+="<th class='data3'>";
            text+="Category";
            text+="</th>";
            text+="<th class='data4''>";
            text+="Name";
            text+="</th>";
            text+="<th class='data5'>";
            text+="Address";
            text+="</th>";
            text+="</tr>";
            
            for(var i=0; i<j;i++){
                
                text+="<tr>";
                text+="<td class='data6'>";
                text+="<img src='"+object[i]['category']+"' width='70px' height='40px'>" ;          
                text+="</td>";
                text+="<td class='data6'>";
             //   text+="<a         href='"+"javascript:void(0)"+"'onclick='"+picture()+"' >"+object[i]['Name']+"</a>";
                text+='<a class="data7" href="javascript:void(0)" onclick="picture(this)" value="'+object[i]['placeid']+'">'+object[i]['Name']+'</a>';
                text+="</td>";
                text+="<td class='data6'>";
                text+='<a value="'+i+'map" name="'+i+'floating-panel" class="data7" href="javascript:void(0)" onclick="map(this)" label1="'+object[i]['newlat']+'" label2="'+object[i]['newlng']+'" >'+object[i]['Address']+'</a>';
                text+="<div style='display:none; height: 416px; width:413px' id='"+i+"map'>";
                text+="</div>";
                text+="<div style='display:none ；width:95px;height：117px' id='"+i+"floating-panel'>";
                text+="</div>";
                text+="</td>";
               // text+="</td>";
                text+="</tr>";
            }
            text+="</table>";
            console.log(text);
            }
            else{
                text+="<table class='data12'><tr><td class='data13'>";
                text+="No Records has been found";
                text+="</td></tr></table>"
            }
            
            window.onload=function(){
            document.getElementById("firsttable").innerHTML=text;
            }
            // initMap();   
        }
            
            var parse;
            var review_photo;
       
            
        function picture(obj){
            var aa=obj.innerHTML;
            var bb=obj.getAttribute("value");
            console.log(bb);
            
            
             if(window.XMLHttpRequest){
                xmlhttp=new XMLHttpRequest();
            }
            else{
                xmlhttp=new ActiveXOject("Microsoft.XMLHTTP");
            }
            var oneurl="/place.php?placeidcc="+bb;
            
            xmlhttp.onreadystatechange=function(){
                if(xmlhttp.readyState==4&& xmlhttp.status==200){
                    review_photo=xmlhttp.responseText;
                    parse=JSON.parse(review_photo);
                    console.log(review_photo);
                }
            }
            
            console.log(oneurl);
            xmlhttp.open("GET",oneurl,false);
            xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
            xmlhttp.send();
            
            var text1 = "<table class='data8'><tr><td class='data9'>"+aa+"</td></tr><tr><td class='data11'>click to show reviews</td></tr><tr><td><div id='reviewce' ><a class='data10' href='javascript:void(0)' onclick='judgereview()'><img src='http://cs-server.usc.edu:45678/hw/hw6/images/arrow_down.png' width='40px' height='30px'></a></div><div id='review5'></div></td></tr><tr><td class='data11'>click to show photos</td></tr><tr><td><div id='photos11'><a class='data10' href='javascript:void(0)' onclick='targetphoto()'><img src='http://cs-server.usc.edu:45678/hw/hw6/images/arrow_down.png' width='40px' height='30px'></a></div><div id='photo5'></div></td></tr><br></table>";
    
           document.getElementById("firsttable").innerHTML=text1;
    }
            
                var target=true;
            
            function judgereview(){
                 if(target==true){
                    review();
                      var re="<a class='data10' href='javascript:void(0)' onclick='judgereview()'><img src='http://cs-server.usc.edu:45678/hw/hw6/images/arrow_up.png' width='40px' height='30px'></a>";
                 document.getElementById("reviewce").innerHTML=re;
                    target=false;
                }
                else{
                      var re="<a class='data10' href='javascript:void(0)' onclick='judgereview()'><img src='http://cs-server.usc.edu:45678/hw/hw6/images/arrow_down.png' width='40px' height='30px'></a>";
                 document.getElementById("reviewce").innerHTML=re;
                    clearreview();
                    target=true;
                }
            }
            
            function clearreview(){
                var reviewdataaa="";
                document.getElementById("review5").innerHTML=reviewdataaa;
                
            }
            
             function review(){
               var reviewdata="";
                 
               
                if(parse["1"]!=null&& parse["1"].length!=0){
                
                reviewdata+="<table class='review1'>";
                for(var reviewlen=0;reviewlen<5;reviewlen++){
                    if(parse["1"][reviewlen]!=null && parse["1"][reviewlen].profile!=undefined){
                    reviewdata+="<tr><td class='review2'>"; 
                    reviewdata+="<img src='"+parse["1"][reviewlen].profile+"' width='70px' height='40px'>"; 
                    reviewdata+=parse["1"][reviewlen].author;
                    reviewdata+="</td></tr><tr><td class='review3'>";
                    reviewdata+=parse["1"][reviewlen].newview;
                    reviewdata+="</td></tr>";
                    }
                }
                  reviewdata+="</table>";
                }
                    else{
                        reviewdata+="<table class='review4'><tr><td>";
                        reviewdata+="No Reviews Found";
                        reviewdata+="</td></tr></table>";
                    }
              document.getElementById("review5").innerHTML=reviewdata;
                console.log(reviewdata);    
            }
            
             function targetphoto(){
                  if(target==true){
                      var arrow="<a class='data10' href='javascript:void(0)' onclick='targetphoto()'><img src='http://cs-server.usc.edu:45678/hw/hw6/images/arrow_up.png' width='40px' height='30px'></a>";
                       document.getElementById("photos11").innerHTML=arrow;
                      
                    photo();
                    target=false;
                }
                else{
                     var arrow="<a class='data10' href='javascript:void(0)' onclick='targetphoto()'><img src='http://cs-server.usc.edu:45678/hw/hw6/images/arrow_down.png' width='40px' height='30px'></a>";
                       document.getElementById("photos11").innerHTML=arrow;
                    clearphoto();
                    target=true;
                }
             }
            
           function clearphoto(){
               var newphoto="";
               document.getElementById("photo5").innerHTML=newphoto;
            document.getElementById("review5").innerHTML=newphoto;
               
           }
            
            
            function photo(){
                var photodata="";
                if(parse["0"]!=null && parse["0"].length!=0){
                photodata+="<table class='photo1'>";
                for(var photolen=0;photolen<5;photolen++){
                    if(parse["0"][photolen]!=null){
                    photodata+="<tr><td class='photo2'>";
                    photodata+="<a href='"+"http://cs-server.usc.edu:26938/"+parse["0"][photolen].newphoto+"'>"
                    photodata+="<img src='"+"http://cs-server.usc.edu:26938/"+parse["0"][photolen].newphoto+"' width='615px' height='410px'>";
                    photodata+="</a>";
                   // photodata+=parse["0"][photolen].newphoto;
                    photodata+="</td></tr>";
                    }
                }
                photodata+="</table>";}
                else{
                    photodata+="<table class='review4'><tr><td>";
                    photodata+="No Photos Found";
                    photodata+="</td></tr><table>";
                }
               console.log(photodata); document.getElementById("photo5").innerHTML=photodata;
               
            }
        
            var newaddress="";
           
            var success=true;
           var cc="";
            var dd="";
            function map(obj){
               ee=obj.getAttribute("label1");
                ee=Number(ee);
                ff=obj.getAttribute("label2");
                ff=Number(ff);
                dd=obj.getAttribute("name");
                cc=obj.getAttribute("value");
                console.log(dd);
                if(success==true){
                newaddress=obj.innerHTML; 
              document.getElementById(dd).style.display="";
                
             document.getElementById(cc).style.display="";
                
          document.getElementById(dd).style.position="absolute";
               document.getElementById(cc).style.position="absolute";
                    
                    initMap();
                    success=false;
                }
                else{
                                       
              document.getElementById(dd).style.display="none";
                
             document.getElementById(cc).style.display="none";
                
          // document.getElementById("floating-panel").style.position="relative";
               //document.getElementById("map").style.position="relative";
                    clearmap();
                    success=true;
                }
            }
            
            function clearmap(){
                var clearmap="";
                document.getElementById(cc).innerHTML=clearmap;
                document.getElementById(dd).innerHTML=clearmap;
            }
            
            var maplat="";
            var maplog="";
        
            function initMap() {
                console.log(newaddress);
                maplat=<?= $enlatitude?>;
                maplog=<?= $enlongitude?>;
                    console.log(maplat);
                console.log(maplog);
                console.log(ee);
                console.log(ff);
                
                 textmap="<select size='3' id='mode'><option class='select' value='WALKING'>Walk there</option><option class='select' value='BICYCLING'>Bike there</option><option class='select' value='DRIVING'>Drive there</option></select>";
            document.getElementById(dd).innerHTML=textmap;
        
        
                 
        var directionsDisplay = new google.maps.DirectionsRenderer;
        var directionsService = new google.maps.DirectionsService;
               //  console.log(directionsDisplay);
                // console.log(directionsService);
        var map = new google.maps.Map(document.getElementById(cc), {
          zoom: 14,
          center: {lat: ee, lng: ff}
        });
                            
        var marker = new google.maps.Marker({
          position: {lat: ee, lng: ff},
          map: map
        });
        directionsDisplay.setMap(map);
                 //console.log( directionsDisplay.setMap(map));

        calculateAndDisplayRoute(directionsService, directionsDisplay);
                
        document.getElementById('mode').addEventListener('change', function() {
          calculateAndDisplayRoute(directionsService, directionsDisplay);
        });
                success=false;
      }

      function calculateAndDisplayRoute(directionsService, directionsDisplay) {
        var selectedMode = document.getElementById('mode').value;
         // console.log(selectedMode);
        directionsService.route({
          origin: {lat: maplat, lng: maplog},  // Haight.
          destination: {lat: ee, lng: ff},  // Ocean Beach.
          // Note that Javascript allows us to access the constant
          // using square brackets and a string value as its
          // "property."
          travelMode: google.maps.TravelMode[selectedMode]
        }, function(response, status) {
          if (status == 'OK') {
            directionsDisplay.setDirections(response);
          } else {
            window.alert('Directions request failed due to ' + status);
          }
        });
      }
            
            
             
        function clear(){
                var clearkeyname=document.getElementById("keyword");
                clearkeyname.removeAttribute("value");
                var clearcategory=document.getElementById("category");
                var categorySelect=clearcategory.options[clearcategory.selectedIndex];
                categorySelect.removeAttribute("selected");
                var cleardistance=document.getElementById("distance");
                cleardistance.removeAttribute("value");
                var clearlocation=document.getElementById("location");
                clearlocation.removeAttribute("value");
                
            }
    
            </script>
          
        <div border="1" class="form">
            <form method="post"  action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
            <div class="text1">Travel and Entertainment Search</div>
                <div class="text2"><b>Keyword</b></div>
            <div class="text3"><input type="text" id="keyword" name="Keyword" 
                           value="<?php echo $keyword;?>" required>
               
                </div>
             
            <div class="text4"><b>Category</b></div>
                <div class="text5">
                    <select id="category" name="Category">
            <option value="" <?php if($category == "")echo "selected"; 
                    ?>>default</option>        
            <option value="cafe" <?php if($category == "cafe")echo "selected"; 
                    ?>>Cafe</option>
            <option value="bakery" <?php if($category == "bakery")echo "selected"; 
                    ?>>bakery</option>
            <option value="restaurant"<?php if($category == "restaurant")echo "selected"; 
                    ?>>restaurant</option>
            <option value="beauty salon"<?php if($category == "beauty salon")echo "selected"; 
                    ?>>beauty salon</option>
            <option value="casino"<?php if($category == "casino")echo "selected";
                    ?>>casino</option>
            <option value="movie theater"<?php if($category == "movie theater")echo "selected"; 
                    ?>>movie theater</option>
            <option value="lodging"<?php if($category == "lodging")echo "selected";
                    ?>>lodging</option>
            <option value="airport"<?php if($category == "airport")echo "selected"; 
                    ?>>airport</option>
            <option value="train station"<?php if($category == "train station")echo "selected";
                    ?>>train station</option>
            <option value="subway station"<?php if($category == "subway station")echo "selected"; 
                    ?>>subway station</option>
            <option value="bus station"<?php if($category == "bus station")echo "selected"; 
                    ?>>bus station</option>
                    </select></div>
                    
                    
            <div class="text6"><b>Distance (miles)</div>
                <div class="text7"><input type="text" id="distance"
                           name="Distance" value="<?php echo $distance;?>" placeholder="10"></div>
                <div class="text8"><b>from</b></div>
                
                <div class="text9"><input id="local" type="radio" name="location"
                           value="local" <?php echo $local; ?>
                                          >Here<br></div>
                <div class="text10">
                <input id="random" type="radio" name="location" 
                       value="random" <?php  echo $random; ?>>
                    
                    <input type="text" id="location" name="newlocation"  value="<?php echo $newlocation;?>" placeholder="location">
                 
                </div>
            
            <div class="text11"><input name="search" type="submit" onclick="validation()" Value="Search">
                <input type="submit" value="Clear" onclick="clear()"></div>
        </form>
        </div>
        
        <div class="data1" id="firsttable">
        </div>
            
       
    
<script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCySUo-85EpYeYIv_cgnZoBeppOUve67tc">
    </script>
    </body>
   
</html>