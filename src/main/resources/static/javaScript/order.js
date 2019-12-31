function createOrder(){
    let userId = 0;
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/user/order/"+userId+"/createOrder",
        success : function (response) {
            alert("order created");
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}
function getOrders() {
    let userId = 0;
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/user/order/"+userId+"/getOrders",
        success : function (response) {
            let orders = JSON.parse(JSON.stringify(response));
            if(orders.length === 0){
                document.getElementById("getOrders").innerHTML =
                    "<p>No Order History Found</p>>"
            }
            else {
                let orderDisplay = "";
                for(let i = 0; i < orders.length; i++){
                    let order = orders[i];
                    orderDisplay +=  displayOrders(order);
                }
                document.getElementById("getOrders").innerHTML = orderDisplay;
            }
        },
        error : function (xhr) {
            alert(xhr.responseText);
        }
    })
}
function displayOrders(order) {
    return "<p>order Id : " + order.orderId + "</p>" +
        "<p>product List : <br>" + displayOrderItems(order.orderItems)+ "</p>" +
        "<p>order status : " + order.orderStatus + "</p>"
}
function displayOrderItems(orderItems) {
    let display = "";
    for(let i = 0; i < orderItems.length; i++)
        display += "<p>product " + (i+1) + " : " + orderItems[i].product.productName + "</p>"
    return display;
}