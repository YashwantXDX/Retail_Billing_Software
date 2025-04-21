package in.yashwant.billingsoftware.service;

import in.yashwant.billingsoftware.io.OrderRequest;
import in.yashwant.billingsoftware.io.OrderResponse;
import in.yashwant.billingsoftware.io.PaymentVerificationRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();

    OrderResponse verifyPayment(PaymentVerificationRequest request);

    Double sumSalesByDate(LocalDate date);

    Long countByOrderDate(LocalDate date);

    List<OrderResponse> findByRecentOrders();
}
