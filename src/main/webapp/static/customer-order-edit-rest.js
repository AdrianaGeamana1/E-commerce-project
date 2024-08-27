var customerId = $.url('?customerId');
var orderId = $.url('?orderId');


$(() => {
    $.getJSON(`/order_edit?customerId=${customerId}&orderId=${orderId}`)
        .done((json) => {
            $('#titleOrderNumber').text(json.number);
            $('#buttonDone').attr('href', `/order?id=${customerId}`);
        });

    $.getJSON(`/order_products?customerId=${customerId}&orderId=${orderId}`)
        .done((orderProducts) => orderProducts.forEach(addOrderProductRow)
        );

    $('#productAddName').autocomplete({
        source: searchProductByTerm,
        select: (ev, ui) => {
            $('#productAddId').val(ui.item.id);
            $('#productAddStock').val(ui.item.stock)
        }
    });

    $('#buttonProductAdd').click(() => {
        var productStock = $('#productAddStock').val();
        var productId = $('#productAddId').val();
        var productName = $('#productAddName').val();
        var quantity = $('#productAddQuantity').val();
        $('#productAddId, #productAddName, #productAddQuantity, #productAddStock').val(null);
        //   validateStockError(productStock,quantity,productName,'click');
        $.ajax({
            url: `/order_products?customerId=${customerId}&orderId=${orderId}`,
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({
                product: {id: productId},
                quantity: quantity
            })

        }).done(addOrUpdateOrderProductRow)
            .fail(function (error){
                console.log("error details:",error.responseText);
                $('#stockError').text(error.responseText);
            });

    });
})

function addOrUpdateOrderProductRow(orderProduct) {
    $('#stockError').text('');
    var existingRow = $("#op_" + orderProduct.product.id);
    if (existingRow.length === 1) {
        existingRow.find('[name="spanQuantity"]')
            .hide().text(orderProduct.quantity).show('slow');
        existingRow.find('[name="spanValue"]')
            .hide().text(orderProduct.value).show('slow');
    } else {
        addOrderProductRow(orderProduct);
    }
}

function addOrderProductRow(orderProduct) {
    var newRow = $(`
        <tr id="op_${orderProduct.product.id}">
            <th>${orderProduct.product.name}</th>
            <th><span name="spanQuantity">${orderProduct.quantity}</span></th>
            <th><span name="spanValue">${orderProduct.value}</span></th>
            <th><img src="${orderProduct.product.image}" alt="oo.jpg" width="38px" height="38px"></th>
            <th><button name="buttonProductRemove" class="btn btn-info">Remove</button></th>
        </tr>
    `);
    newRow.find('[name="buttonProductRemove"]').click(() => {
        $.ajax({
            url: `/order_products?orderId=${orderId}&productId=${orderProduct.product.id}`,
            type: 'DELETE'
        }).done(() =>
            newRow.hide(400, () => newRow.remove())
        );
    });

    newRow.hide().insertBefore('#productAddFormRow').show('slow');
}

function searchProductByTerm(term, resultCallback) {
    $.getJSON('/products', term)
        .done((products) => {
            resultCallback(products.map((p) => {
                return {
                    id: p.id,
                    label: p.name + ' ' + 'price: ' + p.price + '$' + ' ' + 'stock: ' + p.stock,
                    value: p.name,
                    stock: p.stock
                }
            }))
        })
}

function validateStockError(stock, quantity, name, event) {
    document.getElementById('stockError').textContent = '';
    let isValid = true;
    if (quantity > stock) {
        event.preventDefault();
        document.getElementById('stockError').textContent = 'Invalid stock! The stock of ' + name + ' is ' + stock + '.';
        isValid = false;
    }
    if (isValid) {
        document.getElementById('buttonProductAdd').click();
    }
}