package com.example.http.xml.client;

import com.example.http.xml.common.codec.HttpXmlRequest;
import com.example.http.xml.common.codec.HttpXmlResponse;
import com.example.http.xml.common.pojo.Address;
import com.example.http.xml.common.pojo.Customer;
import com.example.http.xml.common.pojo.Order;
import com.example.http.xml.common.pojo.Shipping;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpXmlClientHandle extends SimpleChannelInboundHandler<HttpXmlResponse> {

    private static Logger log = LoggerFactory.getLogger(HttpXmlClientHandle.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Order order = new Order();
        doBusiness(order);
        HttpXmlRequest request = new HttpXmlRequest(null, order);
        ctx.writeAndFlush(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
        log.info("The client receive response of http header is: " + msg.getHttpResponse().headers().names());
        log.info("The client receive response of http body is: " + msg.getResult());
    }

    private void doBusiness(Order order) {
        order.setOrderNumber(123);
        order.setTotal(9999.999f);
        Address address = new Address();
        address.setCity("南京市");
        address.setCountry("中国");
        address.setPostCode("123321");
        address.setState("江苏省");
        address.setStreet1("龙眠大道");
        order.setBillTo(address);
        Customer customer = new Customer();
        customer.setCustomerNumber(123);
        customer.setFirstName("李");
        customer.setLastName("林峰");
        order.setCustomer(customer);
        order.setShipping(Shipping.INTERNATIONAL_MAIL);
        order.setShipTo(address);
    }
}
