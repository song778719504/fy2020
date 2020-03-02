package com.hh.demo.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.service.IPayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order/")
public class PayController {

    @Resource
    IPayService payService;
    @Value("${alipay.public.key}")
      String alipayPublicKey;

    @RequestMapping("pay.do")
    public ServerResponse pay(Long orderNo){
        return payService.pay(orderNo);
    }

    /**
     * 支付宝回调接口
     * 当前用户支付完成后，支付宝服务器会调用该接口
     */
    @RequestMapping("alipay_callback.do")
    public String  alipay_callback(HttpServletRequest request){


        System.out.println("====");

        Map<String,String[]> callbackParam=  request.getParameterMap();

        Iterator<Map.Entry<String,String[]>> iterator=callbackParam.entrySet().iterator();

        Map<String,String> signMap=new HashMap<>();

        while(iterator.hasNext()){
            Map.Entry<String,String[]> entry=iterator.next();
            String key= entry.getKey();
            String[] values=entry.getValue();
            StringBuffer buffer=new StringBuffer();
            for(int i=0;i<values.length;i++){
                buffer.append(values[i]+",");
            }
            String value=buffer.toString();
            value=value.substring(0,value.length()-1);
            signMap.put(key,value);

            System.out.println("=key="+key+"  value="+value);
        }

        //1.验签、

        //必须删除sign_type否则失败
        signMap.remove("sign_type");
        try {
            boolean result= AlipaySignature.rsaCheckV2(signMap,alipayPublicKey,"utf-8","RSA2");
            if(!result){
                return "fail";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        };


        //2.处理业务逻辑
        return payService.callBackLogic(signMap);

    }

}
