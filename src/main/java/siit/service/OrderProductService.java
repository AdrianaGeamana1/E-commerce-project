package siit.service;

import siit.dao.OrderProductDao;
import siit.model.OrderProduct;
import siit.model.Product;
import siit.validation.ValidationErrorException;

import java.math.BigDecimal;
import java.util.List;

public class OrderProductService {
    private OrderProductDao orderProductDao;
    private ProductService productService;

    public OrderProductService() {
        this.orderProductDao = new OrderProductDao();
        this.productService=new ProductService();
    }

    public OrderProductService(OrderProductDao orderProductDao, ProductService productService) {
        this.orderProductDao = orderProductDao;
        this.productService = productService;
    }

    public List<OrderProduct> getOrderProducts(int customerId, int orderId) {
        return orderProductDao.getOrderProducts(customerId, orderId);
    }

    public OrderProduct addOrderProduct(int orderId, int productId, BigDecimal quantity) {
        Product product= productService.getProductById(productId);
        BigDecimal stock=BigDecimal.valueOf(product.getStock());
        if(quantity.compareTo(stock)>0){
            throw new ValidationErrorException("Invalid stock!The stock of "+product.getName()+" is "+product.getStock()+" !");
        }
        OrderProduct existingOrderProduct = orderProductDao.findBy(orderId, productId);

        if (existingOrderProduct == null) {
            orderProductDao.addOrderProduct(orderId, productId, quantity);
        } else {
            BigDecimal totalQuantity = quantity.add(existingOrderProduct.getQuantity());
            orderProductDao.updateOrderProduct(orderId, productId, totalQuantity);
        }

        return orderProductDao.findBy(orderId, productId);
    }

    public void deleteOrderProductBy(int orderId, int productId) {
        orderProductDao.deleteBy(orderId, productId);
    }

    public OrderProduct getOrderProductByOrderIdAndProductId(int orderId, int productId) {
        return orderProductDao.getOrderProductByOrderIdAndProductId(orderId,productId);
    }

}
