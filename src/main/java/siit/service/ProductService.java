package siit.service;

import siit.dao.ProductDao;
import siit.model.Product;
import siit.validation.ValidationErrorException;

import java.math.BigDecimal;
import java.util.List;

public class ProductService {

    private ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
    }

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getProductsBy(String term) {
        return productDao.getProductsBy(term);
    }
    public Product getProductById(int id){
        return productDao.getProductById(id);
    }

    public void updateStockWhenAdd(int productId, BigDecimal quantity) {
       Product product= this.getProductById(productId);
       int productStock=product.getStock();
       BigDecimal productStock2=BigDecimal.valueOf(productStock);
       BigDecimal finalStock=productStock2.subtract(quantity);
       Integer updateStock=Integer.parseInt(finalStock.toString());
       productDao.updateStock(productId,updateStock);
    }

    public void updateStockWhenDelete(int productId, BigDecimal quantity) {
        Product product= this.getProductById(productId);
        int productStock=product.getStock();
        BigDecimal productStock2=BigDecimal.valueOf(productStock);
        BigDecimal finalStock=productStock2.add(quantity);
        Integer updateStock=Integer.parseInt(finalStock.toString());
        productDao.updateStock(productId,updateStock);
    }

    public List<Product> getProductsList() {
        return productDao.getProductsList();
    }

    public Product addNewProduct(String name, BigDecimal weight, BigDecimal price, String image, int stock)throws ValidationErrorException {
        Product existingProduct=productDao.findProductByName(name);
        if(existingProduct!=null){
            throw new ValidationErrorException("This product name is existing in the list");
        }
        if(name.length()<5){
            throw new ValidationErrorException("The name is too short!");
        }
        if(weight.compareTo(BigDecimal.ZERO)<0){
            throw new ValidationErrorException("The weight can't be less than 0!");
        }
        if(price.compareTo(BigDecimal.ZERO)<0||price.compareTo(BigDecimal.ZERO)==0){
            throw new ValidationErrorException("The price can't be less or equal to 0!");
        }
        if(stock<0){
            throw new ValidationErrorException("The stock can't be less than 0!");
        }

        productDao.addNewProduct(name,weight,price,image,stock);

        return productDao.findProductByName(name);
    }

    public void deleteProductBy(int productId) {

        productDao.deleteProductBy(productId);
    }

    public void updateProduct(Product updatedProduct) {
        if(updatedProduct.getName().length()<5){
            throw new ValidationErrorException("Name is too short!");
        }
        if(updatedProduct.getPrice().compareTo(BigDecimal.ZERO)<0){
            throw new ValidationErrorException("Price can't be negative!");
        }
        if(updatedProduct.getStock()<0){
            throw new ValidationErrorException("Stock can't be negative");
        }
        productDao.updateProduct(updatedProduct);
    }
}
