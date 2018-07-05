package it.balyfix.streaming.processor;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import it.balyfix.streaming.model.Order;
import org.apache.flink.api.common.functions.MapFunction;

import java.io.IOException;


public class OrderMapping implements MapFunction<String,Order>
{
    private static final long serialVersionUID = -6867736771747690202L;

    private XmlMapper xmlMapper;

    public OrderMapping()
    {

        xmlMapper = new XmlMapper();
    }

    @Override
    public Order map(String s) throws Exception
    {
        return doTransform(s);
    }



    private Order doTransform(String message) throws IOException
    {
        return message != null ?  xmlMapper.readValue(message, Order.class) : new Order();
    }

}
