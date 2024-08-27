$(() => {
    $('#backButton').attr('href', '/homepage');
    $.getJSON(`/homepage_products`)
        .done(function (products){
            products.forEach(addProductRow);
        });
})
function addProductRow(product){
    var newRow=$(`
        <tr id="pr_${product.id}">
        <th><span name="nameProduct">${product.name}</span></th>
        <th><span name="weightProduct">${product.weight}</span></th>
        <th><span name="priceProduct">${product.price}$</span></th>
        <th><img src="/static/${product.image}" alt="oo.jpg" width="38" height="38"></th>
       </tr>

    `);
    $('#pTable').append(newRow);
}