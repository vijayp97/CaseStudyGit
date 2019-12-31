function addToCart(element){
    let userId = 0;
    let productId = element.id;
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/user/cart/"+userId+"/add/"+productId,
        success : function () {
            alert("product added to cart");
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}
function getcart() {
    let userId = 0;
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/user/cart/"+userId+"/getCart",
        success : function (response) {
            document.getElementById("getCartItem").classList.add("d-none");
            document.getElementById("getCart").classList.remove("d-none");
            document.getElementById("createOrder").classList.remove("d-none");
            let cart = JSON.parse(JSON.stringify(response));
            let cartItems = cart.cartItem;
            let cartItemDisplay = "";
            if (cartItems.length === 0){
                document.getElementById("getCart").innerHTML =
                    "<p> No Products Found </p>"
            }
            else {
                for (let i = 0; i < cartItems.length; i++) {
                    let cartItem = cartItems[i];
                    cartItemDisplay += displayCartItem(cartItem);
                }
                document.getElementById("getCart").innerHTML = cartItemDisplay;
            }
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
};
function displayCartItem(cartItem) {
    return "<p>cart item Id : " + cartItem.itemId + "</p>" +
        "<p>product : " + cartItem.product.productName + "</p>" +
        "<p>price : " + cartItem.product.price + "</p>" +
        "<p>quantity : " + cartItem.quantity + "</p>" +
        "<button id='"+cartItem.product.productId+"' onclick='removeFromCart(this)'>remove from cart</button>" +
        "<button id='"+cartItem.itemId+"' onclick='getCartItem(this)'>get cart item</button>"
}
function removeFromCart(element){
    let userId = 0;
    let productId = element.id;
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/user/cart/"+userId+"/remove/"+productId,
        success : function () {
            getcart();
            alert("product removed from cart");
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}
function getCartItem(element){
    let userId = 0;
    let cartItemId = element.id;
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/user/cart/"+userId+"/getCartItem/"+cartItemId,
        success : function (response) {
            document.getElementById("getCart").classList.add("d-none");
            document.getElementById("createOrder").classList.add("d-none");
            document.getElementById("getCartItem").classList.remove("d-none");
            let cartItem = JSON.parse(JSON.stringify(response));
            let cartItemDisplay = "";
            cartItemDisplay += displaySingleCartItem(cartItem);
            document.getElementById("getCartItem").innerHTML = cartItemDisplay;
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}
function displaySingleCartItem(cartItem) {
    return "<p>cart item Id : " + cartItem.itemId + "</p>" +
        "<p>product : " + cartItem.product.productName + "</p>" +
        "<p>price : " + cartItem.product.price + "</p>" +
        "<p>category : " + cartItem.product.category + "</p>" +
        "<p>subCategory : " + cartItem.product.subCategory + "</p>" +
        "<p>details : " + cartItem.product.details + "</p>" +
        "<p>quantity : " + cartItem.quantity + "</p>" +
        "<button id='"+cartItem.product.productId+"' onclick='removeFromCart(this)'>remove from cart</button>" +
        "<input id='"+cartItem.product.productId+"' placeholder='quantity' type='number' onchange='changeQuantity(this)'>"
}
function changeQuantity(element) {
    let data = new FormData();
    data.append("quantity", element.value);
    let userId = 0;
    let productId = element.id;
    $.ajax({
        method: "POST",
        url: "http://localhost:8080/user/cart/"+userId+"/changeQuantity/"+productId,
        processData : false,
        contentType : false,
        data : data,
        success : function () {
            getcart();
            alert("cart updated");
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}