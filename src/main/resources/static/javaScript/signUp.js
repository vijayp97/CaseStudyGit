function signup() {
    let data = new FormData();
    data.append("email", document.getElementById("email").value);
    data.append("password", document.getElementById("password").value);
    data.append("name", document.getElementById("name").value);
    $.ajax({
        method : "POST",
        url : "http://localhost:8080/signup",
        processData : false,
        contentType : false,
        data : data,
        success : function () {
            alert("user added successfully")
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}