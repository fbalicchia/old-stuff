package it.balyfix.streaming.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.balyfix.streaming.utils.LocalDateTimeDeserializer;
import it.balyfix.streaming.utils.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class Order
{

    private String orderNumber;

    private String paymentType;

    private String email;

    private BigDecimal totalAmount;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime orderDate;


    public String getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public String getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }


    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }


    public LocalDateTime getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate)
    {
        this.orderDate = orderDate;
    }

    public long getTimestamp()
    {
        return orderDate.toInstant(ZoneOffset.ofTotalSeconds(0)).getEpochSecond();
    }
}
