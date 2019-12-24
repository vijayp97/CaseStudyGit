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