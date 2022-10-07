package com.cs5424t.ycql.Entities;

import com.cs5424t.ycql.Entities.PrimaryKeys.OrderPK;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Table("order_ycql")
@Data
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 15L;

    @PrimaryKey
    private OrderPK orderPK;

    @Column("o_c_id")
    private Integer customerId;

    @Column("o_carrier_id")
    private Integer carrierId;

    @Column("o_ol_cnt")
    private BigDecimal numOfItems;

    @Column("o_all_local")
    private BigDecimal status;

    @Column("o_entry_d")
    private Timestamp createTime;
}
