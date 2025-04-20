package in.yashwant.billingsoftware.service;

import in.yashwant.billingsoftware.io.OrderRequest;
import in.yashwant.billingsoftware.io.OrderResponse;
import in.yashwant.billingsoftware.io.PaymentVerificationRequest;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();

    OrderResponse verifyPayment(PaymentVerificationRequest request);
}
