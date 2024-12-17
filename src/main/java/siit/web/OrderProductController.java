package siit.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import siit.model.OrderProduct;
import siit.service.OrderProductService;
import siit.utility.JsonUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/order_products")
public class OrderProductController extends HttpServlet {

    private OrderProductService orderProductService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        orderProductService = new OrderProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int customerId = Integer.parseInt(req.getParameter("customerId"));
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        List<OrderProduct> orderProducts = orderProductService.getOrderProducts(customerId, orderId);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonObject = JsonUtil.convertToJsonString(orderProducts);

        out.print(jsonObject);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       var test =  """
                {
                    "product": {
                            "id": 23,
                            "name": "product_name/product_number"   
                    },
                    "quantity": 222,
                    "value": 311
                }
                """;

        JSONObject payload = JsonUtil.getJsonObjFrom(req);

        int orderId = Integer.parseInt(req.getParameter("orderId"));
        int productId = payload.getJSONObject("product").getInt("id");
        BigDecimal quantity = payload.getBigDecimal("quantity");

        OrderProduct orderProduct = orderProductService.addOrderProduct(orderId, productId, quantity);
        String jsonResponse = JsonUtil.convertToJsonString(orderProduct);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        out.print(jsonResponse);
        out.flush();

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        int productId = Integer.parseInt(req.getParameter("productId"));

        orderProductService.deleteOrderProductBy(orderId, productId);
    }
}
