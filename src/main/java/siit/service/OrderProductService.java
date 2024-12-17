package siit.service;

import siit.dao.OrderProductDao;
import siit.model.OrderProduct;

import java.math.BigDecimal;
import java.util.List;

public class OrderProductService {
    private OrderProductDao orderProductDao;

    public OrderProductService() {
        this.orderProductDao = new OrderProductDao();
    }

    public List<OrderProduct> getOrderProducts(int customerId, int orderId) {
        return orderProductDao.getOrderProducts(customerId, orderId);
    }

    public OrderProduct addOrderProduct(int orderId, int productId, BigDecimal quantity) {
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
}
