package com.cs5424t.ycql.Entities;

import com.cs5424t.ycql.Entities.PrimaryKeys.OrderLinePK;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Table("order_line_ycql")
@Data
public class OrderLine implements Serializable {

    @Serial
    private static final long serialVersionUID = 15L;

    @PrimaryKey
    private OrderLinePK orderLinePK;

    @Column("ol_i_id")
    private Integer itemId;

    @Column("ol_delivery_id")
    private Timestamp deliveryDate;

    @Column("ol_amount")
    private BigDecimal totalPrice;

    @Column("ol_supply_w_id")
    private Integer supplyWarehouseId;

    @Column("ol_quantity")
    private BigDecimal quantity;

    @Column("ol_dist_info")
    private String extraData;

}
