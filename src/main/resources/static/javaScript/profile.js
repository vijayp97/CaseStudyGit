function updateprofile(){
    let data = new FormData();
    data.append("userId", 1);
    data.append("email", document.getElementById("email").value);
    data.append("name", document.getElementById("name").value);
    data.append("phone", document.getElementById("phone").value);
    data.append("address.street", document.getElementById("street").value);
    data.append("address.city", document.getElementById("city").value);
    data.append("address.state", document.getElementById("state").value);
    data.append("address.pincode", document.getElementById("pincode").value);
    $.ajax({
        method : "POST",
        url : "http://localhost:8080/user/updateProfile",
        processData : false,
        contentType : false,
        data : data,
        success : function () {
            alert("profile updated successfully")
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}

function getprofile(){
    let userId = 1;
    console.log("a");
    /*$("#hide").show();*/
    $.ajax({
        method : "GET",
        url : "http://localhost:8080/user/getprofile/1",
        /*redirect : "/getProfilePage",*/
        /*processData : false,
        contentType : false,*/
        success : function (response) {
            console.log("c");
            console.log(Object(response)[0]);
            console.log(JSON.parse(JSON.stringify(Object(response)[0])).name);
            document.getElementById("hide").style.display = "block";
            document.getElementById("name").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).name;
            document.getElementById("email").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).email;
            document.getElementById("phone").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).phone;
            document.getElementById("street").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).address.street;
            document.getElementById("city").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).address.city;
            document.getElementById("state").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).address.state;
            document.getElementById("pincode").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).address.pincode;
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}