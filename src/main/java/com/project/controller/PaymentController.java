package com.project.controller;


import com.project.entities.User;
import com.project.helper.PlanType;
import com.project.response.PaymentLinkResponse;
import com.project.service.IUserService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Autowired
    private IUserService userService;


    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPayment(@PathVariable("planType") PlanType planType,
                                                             @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwt(token);
        System.out.println("plan type --> "+planType);
        int amt = 399*100;

        if(planType.equals(PlanType.ANNUALLY)){
            amt = 799*100;
//            amt = (int)(amt*0.7);
        }

        try{
            RazorpayClient client = new RazorpayClient(apiKey, apiSecret);

            JSONObject razorpay = new JSONObject();

            JSONObject paymentLink = new JSONObject();
            paymentLink.put("amount", amt);
            paymentLink.put("currency", "INR");

            JSONObject customer = new JSONObject();
            customer.put("name", user.getName());
            customer.put("email", user.getEmail());

            JSONObject notify = new JSONObject();
            notify.put("email", true);

            paymentLink.put("customer", customer);
            paymentLink.put("notify", notify);
            paymentLink.put("callback_url", "http://localhost:5173/upgrade_plan/success?planType=" + planType);

            PaymentLink payment = client.paymentLink.create(paymentLink);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
            paymentLinkResponse.setPayment_link_id(paymentLinkId);
            paymentLinkResponse.setPayment_link_url(paymentLinkUrl);
            System.out.println("payment link -->  "+paymentLinkResponse);
            return new ResponseEntity<>(paymentLinkResponse, HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        throw new Exception("Payment not created");
    }
}
