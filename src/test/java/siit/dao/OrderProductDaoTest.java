package siit.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import siit.model.OrderProduct;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderProductDaoTest {

    OrderProductDao orderProductDao;

    @BeforeEach
    public void init(){
        orderProductDao = new OrderProductDao();
    }

    @Test
    void getOrderProducts() {
        int customerId = 0;
        int orderId = 0;

        List<OrderProduct> result = orderProductDao.getOrderProducts(customerId, orderId);
        List<OrderProduct> expectedResult = new ArrayList<>();

        assertEquals(expectedResult, result);
    }
}