function updateprofile(){
    let data = new FormData();
    data.append("userId", 1);
    document.getElementById("getProfileDiv").classList.add("d-none");
    document.getElementById("updateProfileDiv").classList.remove("d-none");
    data.append("email", document.getElementById("updateEmail").value);
    data.append("name", document.getElementById("updateName").value);
    data.append("phone", document.getElementById("updatePhone").value);
    data.append("address.street", document.getElementById("updateStreet").value);
    data.append("address.city", document.getElementById("updateCity").value);
    data.append("address.state", document.getElementById("updateState").value);
    data.append("address.pincode", document.getElementById("updatePincode").value);
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
    let userId = 0;
    console.log("a");
    /*$("#hide").show();*/
    $.ajax({
        method : "GET",
        url : "http://localhost:8080/user/getprofile/"+userId,
        /*redirect : "/getProfilePage",*/
        /*processData : false,
        contentType : false,*/
        success : function (response) {
            console.log("c");
            console.log(Object(response)[0]);
            console.log(JSON.parse(JSON.stringify(Object(response)[0])).name);
            document.getElementById("updateProfileDiv").classList.add("d-none");
            document.getElementById("getProfileDiv").classList.remove("d-none");
            document.getElementById("getName").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).name;
            document.getElementById("getEmail").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).email;
            document.getElementById("getPhone").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).phone;
            document.getElementById("getStreet").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).address.street;
            document.getElementById("getCity").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).address.city;
            document.getElementById("getState").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).address.state;
            document.getElementById("getPincode").innerHTML = JSON.parse(JSON.stringify(Object(response)[0])).address.pincode;
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}
function updateprofileform(){
    document.getElementById("getProfileDiv").classList.add("d-none");
    document.getElementById("updateProfileDiv").classList.remove("d-none");
}