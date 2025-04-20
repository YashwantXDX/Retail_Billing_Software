package in.yashwant.billingsoftware.service.impl;

import com.razorpay.RazorpayException;
import in.yashwant.billingsoftware.io.RazorpayOrderResponse;
import in.yashwant.billingsoftware.service.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RazorpayServiceImpl implements RazorpayService {
    @Override
    public RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException {

    }
}
