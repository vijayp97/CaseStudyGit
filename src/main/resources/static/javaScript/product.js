function addproduct(){
    let data = new FormData();
    data.append("productName", document.getElementById("addName").value);
    data.append("price", document.getElementById("addPrice").value);
    data.append("details", document.getElementById("addDetails").value);
    data.append("category", document.getElementById("addCategory").value);
    data.append("subCategory", document.getElementById("addSubCategory").value);
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
    data.append("productId", document.getElementById("updateProductId").value);
    data.append("productName", document.getElementById("updateName").value);
    data.append("price", document.getElementById("updatePrice").value);
    data.append("details", document.getElementById("updateDetails").value);
    data.append("category", document.getElementById("updateCategory").value);
    data.append("subCategory", document.getElementById("updateSubCategory").value);
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
function addproductform() {
    document.getElementById("updateProductDiv").classList.add("d-none");
    document.getElementById("addProductDiv").classList.remove("d-none");
}
function updateproductform() {
    document.getElementById("addProductDiv").classList.add("d-none");
    document.getElementById("updateProductDiv").classList.remove("d-none");
}
(function productList(){
    $.ajax({
        method : "GET",
        url : "http://localhost:8080/user/products/getProducts",
        success : function (response) {
            let products = JSON.parse(JSON.stringify(response));
            productDisplay = "";
            if (products.length === 0){
                document.getElementById("productList").innerHTML =
                    "<p> No Products Found </p>"
            }
            for (let i = 0 ; i < products.length; i++){
                let product = products[i];
                productDisplay += displayProduct(product);
            }
            document.getElementById("productList").innerHTML = productDisplay;
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
})();
function displayProduct(product) {
    return "<p>name : " + product.productName + "</p>" +
        "<p>price : " + product.price + "</p>" +
        "<p>details : " + product.details + "</p>" +
        "<p>category : " + product.category + "</p>" +
        "<p>subCategory : " + product.subCategory + "</p>";
}
function productSearch() {
    let data = new FormData();
    if(document.getElementById("minimum").value == null)
        mimimum = 0;
    else
        mimimum = document.getElementById("minimum").value;
    data.append("minimum", document.getElementById("minimum").value );
    data.append("maximum", document.getElementById("maximum").value );
    data.append("searchString", document.getElementById("searchString").value );
    let searchType = document.getElementById("searchType").value;
    if(searchType)
    $.ajax({
        method : "POST",
        url : "http://localhost:8080/admin/products/addProduct",
    })
}