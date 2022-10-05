package com.cs5424t.ycql.Entities;

import cs5424t.ysql.Entities.PrimaryKeys.OrderLinePK;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "order_line_ysql")
@IdClass(OrderLinePK.class)
@Data
public class OrderLine implements Serializable {

    @Serial
    private static final long serialVersionUID = 15L;

    @Id
    @Column(name = "ol_w_id")
    private Integer warehouseId;

    @Id
    @Column(name = "ol_d_id")
    private Integer districtId;

    @Id
    @Column(name = "ol_o_id")
    private Integer orderId;

    @Id
    @Column(name = "ol_number")
    private Integer id;

    @Column(name = "ol_i_id")
    private Integer itemId;

    @Column(name = "ol_delivery_id")
    private Timestamp deliveryDate;

    @Column(name = "ol_amount")
    private BigDecimal totalPrice;

    @Column(name = "ol_supply_w_id")
    private Integer supplyWarehouseId;

    @Column(name = "ol_quantity")
    private BigDecimal quantity;

    @Column(name = "ol_dist_info")
    private String extraData;

}
