

$(() => {
    $.getJSON(`/products_edit`)
        .done(function (products){
            products.forEach(addProductRow);
        });
    $('#saveProduct').click(() => {
        var productName = $('#name2').val();
        var productWeight = $('#weight').val();
        var productPrice= $('#price').val();
        var productImage = $('#image').val();
        var productStock = $('#stock').val();
        $('#name2,#weight,#price,#image,#stock').val(null);

        if(productName.trim()===''){
            $('#nameError2').text('Name is required');
        }
        if(productWeight.trim()===''){
            $('#weightError').text('Weight is required');
        }
        if(productPrice.trim()===''){
            $('#priceError').text('Price is required');
        }
        if(productImage.trim()===''){
            $('#imageError').text('Image is required');
        }
        if(productStock.trim()===''){
            $('#stockError').text('Stock is required');
        }
        else{
            $('#nameError2').text('');
            $('#weightError').text('');
            $('#priceError').text('');
            $('#imageError').text('');
            $('#stockError').text('');
            
        $.ajax({
               url: `/products_edit`,
               type: 'POST',
               dataType: 'json',
               contentType: 'application/json',
               data: JSON.stringify({
                   name: productName,
                   weight: productWeight,
                   price: productPrice,
                   image: productImage,
                   stock: productStock
               })

           }).done(addProductRow)
               .fail(function (error9, event) {
                   console.log("error response:", error9.responseText);
                   $('#controllerError').text(error9.responseText);
               });

       }});
})
function addProductRow(product){
    $('#controllerError').text('');
    var newRow=$(`
        <tr id="pr_${product.id}">
        <th><span name="nameProduct">${product.name}</span></th>
        <th><span name="weightProduct">${product.weight}</span></th>
        <th><span name="priceProduct">${product.price}$</span></th>
        <th><img src="/static/${product.image}" alt="oo.jpg" width="38" height="38"></th>
        <th><span name="stockProduct">${product.stock}</span></th>
        <th><a href="/product_update?productId=${product.id}"><button name="updateProduct" class="btn btn-info">Update</button></a></th>
        <th><button name="deleteProduct" class="btn btn-info">Delete</button></th>
       </tr>

    `);
    $('#pTable').append(newRow);
    newRow.find('[name="deleteProduct"]').click(() => {
        $.ajax({
            url: `/products_edit?productId=${product.id}`,
            type: 'DELETE'
        }).done(() =>
            newRow.hide(400, () => newRow.remove())
        );
    });
}
