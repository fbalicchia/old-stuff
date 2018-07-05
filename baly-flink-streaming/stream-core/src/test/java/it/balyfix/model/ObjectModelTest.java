package it.balyfix.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import it.balyfix.streaming.model.Order;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;


public class ObjectModelTest
{


    @Test
    public void marShallOrder() throws JsonProcessingException
    {
        Order order = new Order();
        order.setTotalAmount(BigDecimal.valueOf(22));
        order.setPaymentType("CreditCard");
        order.setEmail("fbalicchia@gmail.com");
        //order.setOrderDate(LocalDateTime.now());
        order.setOrderNumber("20180000000310");
        XmlMapper xmlMapper = new XmlMapper();
        String orderString = xmlMapper.writeValueAsString(order);

        Assert.assertNotNull(orderString);

    }


    @Test
    public void unMarShallOrder() throws IOException
    {

        String orderStr = "<Order><orderNumber>20180000000310</orderNumber><paymentType>CreditCard</paymentType><email>fbalicchia@gmail.com</email><totalAmount>22</totalAmount><orderDate>2018-03-03T09:40:49.247</orderDate></Order>";

        XmlMapper xmlMapper = new XmlMapper();
        Order order1 = xmlMapper.readValue(orderStr, Order.class);
        Assert.assertEquals(order1.getOrderNumber().equalsIgnoreCase("20180000000310"), true);
    }

}
