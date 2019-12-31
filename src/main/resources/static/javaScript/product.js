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
function productList(){
    $.ajax({
        method : "GET",
        url : "http://localhost:8080/user/products/getProducts",
        success : function (response) {
            document.getElementById("productList").classList.remove("d-none");
            document.getElementById("filteredProductList").classList.add("d-none");
            let products = JSON.parse(JSON.stringify(response));
            let productDisplay = "";
            if (products.length === 0){
                document.getElementById("productList").innerHTML =
                    "<p> No Products Found </p>"
            }
            else {
                for (let i = 0; i < products.length; i++) {
                    let product = products[i];
                    productDisplay += displayProduct(product);
                }
                document.getElementById("productList").innerHTML = productDisplay;
            }
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
};
function displayProduct(product) {
    return "<p>name : " + product.productName + "</p>" +
        "<p>price : " + product.price + "</p>" +
        "<p>details : " + product.details + "</p>" +
        "<p>category : " + product.category + "</p>" +
        "<p>subCategory : " + product.subCategory + "</p>" +
        "<button id='"+product.productId+"' onclick='addToCart(this)'>add to cart</button>";
}
function productSearch() {
    let data = new FormData();
    let minimum;
    let maximum;
    let flag  = 0;
    let flag1 = 0;
    let flag2 = 1;
    let searchString;
    if(document.getElementById("searchString").value!='') {
        if (document.getElementById("minimum").value == '' || document.getElementById("maximum").value == '') {
            flag1 = 1;
            minimum = 0;
            maximum = 0;
        } else if (document.getElementById("minimum").value == 0 && document.getElementById("maximum").value == 0) {
            flag = 1;
            minimum = 0;
            maximum = 0;
        } else {
            minimum = document.getElementById("minimum").value;
            maximum = document.getElementById("maximum").value;
        }
        searchString = document.getElementById("searchString").value;
    }
    else{
        alert("invalid search");
        flag2 = 0;
        minimum = 0;
        maximum = 0;
        searchString = 0;
    }
    console.log(flag1);
    data.append("minimum", minimum );
    data.append("maximum", maximum );
    let searchType = document.getElementById("searchType").value;
    if(searchType === "category" && flag2) {
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/user/products/category/"+searchString+"/getFilteredProducts",
            processData : false,
            contentType : false,
            data : data,
            success : function (response) {
                document.getElementById("productList").classList.add("d-none");
                document.getElementById("filteredProductList").classList.remove("d-none");
                let products = JSON.parse(JSON.stringify(response));
                if (products.length === 0 || flag === 1){
                    document.getElementById("filteredProductList").innerHTML =
                        "<p> No Products Found </p>"
                }
                else {
                    let productDisplay = "";
                    for (let i = 0; i < products.length; i++) {
                        let product = products[i];
                        productDisplay += displayProduct(product);
                    }
                    document.getElementById("filteredProductList").innerHTML = productDisplay;
                }
            },
            error : function (xhr) {
                alert(xhr.responseText);
            }
        })
    }
    if(searchType === "subCategory" && flag2) {
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/user/products/subCategory/"+searchString+"/getFilteredProducts",
            processData : false,
            contentType : false,
            data : data,
            success : function (response) {
                document.getElementById("productList").classList.add("d-none");
                document.getElementById("filteredProductList").classList.remove("d-none");
                let products = JSON.parse(JSON.stringify(response));
                if (products.length === 0 || flag === 1){
                    document.getElementById("filteredProductList").innerHTML =
                        "<p> No Products Found </p>"
                }
                else {
                    let productDisplay = "";
                    for (let i = 0; i < products.length; i++) {
                        let product = products[i];
                        productDisplay += displayProduct(product);
                    }
                    document.getElementById("filteredProductList").innerHTML = productDisplay;
                }
            },
            error : function (xhr) {
                alert(xhr.responseText);
            }
        })
    }
    if(searchType === "details" && flag2) {
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/user/products/details/"+searchString+"/getFilteredProducts",
            processData : false,
            contentType : false,
            data : data,
            success : function (response) {
                document.getElementById("productList").classList.add("d-none");
                document.getElementById("filteredProductList").classList.remove("d-none");
                let products = JSON.parse(JSON.stringify(response));
                if (products.length === 0 || flag === 1){
                    document.getElementById("filteredProductList").innerHTML =
                        "<p> No Products Found </p>"
                }
                else {
                    let productDisplay = "";
                    for (let i = 0; i < products.length; i++) {
                        let product = products[i];
                        productDisplay += displayProduct(product);
                    }
                    document.getElementById("filteredProductList").innerHTML = productDisplay;
                }
            },
            error : function (xhr) {
                alert(xhr.responseText);
            }
        })
    }
    if(searchType === "productName" && flag2) {
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/user/products/productName/"+searchString+"/getFilteredProducts",
            processData : false,
            contentType : false,
            data : data,
            success : function (response) {
                document.getElementById("productList").classList.add("d-none");
                document.getElementById("filteredProductList").classList.remove("d-none");
                let products = JSON.parse(JSON.stringify(response));
                if (products.length === 0 || flag === 1){
                    document.getElementById("filteredProductList").innerHTML =
                        "<p> No Products Found </p>"
                }
                else {
                    let productDisplay = "";
                    for (let i = 0; i < products.length; i++) {
                        let product = products[i];
                        productDisplay += displayProduct(product);
                    }
                    document.getElementById("filteredProductList").innerHTML = productDisplay;
                }
            },
            error : function (xhr) {
                alert(xhr.responseText);
            }
        })
    }
    if(searchType === "all" && flag2 && flag1==0) {
        console.log("gdeq");
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/user/products/search/"+searchString+"/getFilteredProducts",
            processData : false,
            contentType : false,
            data : data,
            success : function (response) {
                document.getElementById("productList").classList.add("d-none");
                document.getElementById("filteredProductList").classList.remove("d-none");
                let products = JSON.parse(JSON.stringify(response));
                if (products.length === 0 || flag === 1){
                    document.getElementById("filteredProductList").innerHTML =
                        "<p> No Products Found </p>"
                }
                else {
                    let productDisplay = "";
                    for (let i = 0; i < products.length; i++) {
                        let product = products[i];
                        productDisplay += displayProduct(product);
                    }
                    document.getElementById("filteredProductList").innerHTML = productDisplay;
                }
            },
            error : function (xhr) {
                alert(xhr.responseText);
            }
        })
    }
    if(searchType === "all" && flag2 && flag1) {
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/user/products/search/"+searchString,
            success : function (response) {
                document.getElementById("productList").classList.add("d-none");
                document.getElementById("filteredProductList").classList.remove("d-none");
                let products = JSON.parse(JSON.stringify(response));
                if (products.length === 0 || flag === 1){
                    document.getElementById("filteredProductList").innerHTML =
                        "<p> No Products Found </p>"
                }
                else {
                    let productDisplay = "";
                    for (let i = 0; i < products.length; i++) {
                        let product = products[i];
                        productDisplay += displayProduct(product);
                    }
                    document.getElementById("filteredProductList").innerHTML = productDisplay;
                }
            },
            error : function (xhr) {
                alert(xhr.responseText);
            }
        })
    }
}