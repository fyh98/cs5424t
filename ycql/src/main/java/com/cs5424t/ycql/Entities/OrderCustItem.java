package com.cs5424t.ycql.Entities;

import com.cs5424t.ycql.Entities.PrimaryKeys.OrderCustItemPK;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.HashSet;

@Table("order_cust_items_ycql")
@Data
public class OrderCustItem implements Serializable {

    private static final long serialVersionUID = 15L;

    @PrimaryKey
    private OrderCustItemPK orderPK;

    @Column("o_c_id")
    private Integer customerId;

    @Column("o_items_set")
    private HashSet<Integer> itemSet;
}
