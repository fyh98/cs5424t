package com.cs5424t.ycql.Entities;

import com.cs5424t.ycql.Entities.PrimaryKeys.WarehousePK;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


import java.math.BigDecimal;


@Table("warehouse_ycql")
@Data
public class Warehouse{

    @PrimaryKey
    private WarehousePK warehousePK;

    @Column("w_name")
    private String name;

    @Column("w_street_1")
    private String street1;

    @Column("w_street_2")
    private String street2;

    @Column("w_city")
    private String city;

    @Column("w_state")
    private String state;

    @Column("w_zip")
    private String zipcode;

    @Column("w_tax")
    private BigDecimal tax;

    @Column("w_ytd")
    private BigDecimal ytd;

}
