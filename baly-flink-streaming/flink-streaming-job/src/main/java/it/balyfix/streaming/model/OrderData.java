package it.balyfix.streaming.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@ToString
public class OrderData
{
    @Getter
    @Setter
    private String orderNumber;
    @Getter
    @Setter
    private String paymentType;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private BigDecimal totalAmount;

    @Getter
    @Setter
    private Integer number;
    @Getter
    @Setter
    private Long orderDate;


}
