function addproduct(){
    let data = new FormData();
    data.append("productName", document.getElementById("name").value);
    data.append("price", document.getElementById("price").value);
    data.append("details", document.getElementById("details").value);
    data.append("category", document.getElementById("category").value);
    data.append("subCategory", document.getElementById("subCategory").value);
    $.ajax({
        method : "POST",
        url : "http://localhost:8080/admin/products/addProduct",
        processData : false,
        contentType : false,
        data : data,
        success : function () {
            alert("product added successfully")
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}
function updateproduct(){
    let data = new FormData();
    data.append("productId", document.getElementById("productId").value);
    data.append("productName", document.getElementById("name").value);
    data.append("price", document.getElementById("price").value);
    data.append("details", document.getElementById("details").value);
    data.append("category", document.getElementById("category").value);
    data.append("subCategory", document.getElementById("subCategory").value);
    $.ajax({
        method : "POST",
        url : "http://localhost:8080/admin/products/update",
        processData : false,
        contentType : false,
        data : data,
        success : function () {
            alert("product updated successfully")
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}