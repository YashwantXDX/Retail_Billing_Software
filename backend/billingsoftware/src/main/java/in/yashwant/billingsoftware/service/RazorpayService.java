package in.yashwant.billingsoftware.service;

import com.razorpay.RazorpayException;
import in.yashwant.billingsoftware.io.RazorpayOrderResponse;

public interface RazorpayService {

    RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;

}
