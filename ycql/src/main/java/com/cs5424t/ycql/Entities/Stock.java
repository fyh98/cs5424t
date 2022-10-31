package com.cs5424t.ycql.Entities;

import com.cs5424t.ycql.Entities.PrimaryKeys.StockPK;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;

@Table("stock_ycql")
@Data
public class Stock implements Serializable {

    private static final long serialVersionUID = 15L;

    @PrimaryKey
    private StockPK stockPK;

    @Column("s_quantity")
    private BigDecimal stockNum;

    @Column("s_ytd")
    private BigDecimal ytd;

    @Column("s_order_cnt")
    private Integer numOfOrder;

    @Column("s_remote_cnt")
    private Integer numOfRemoteOrder;

    @Column("s_dist_01")
    private String district1;

    @Column("s_dist_02")
    private String district2;

    @Column("s_dist_03")
    private String district3;

    @Column("s_dist_04")
    private String district4;

    @Column("s_dist_05")
    private String district5;

    @Column("s_dist_06")
    private String district6;

    @Column("s_dist_07")
    private String district7;

    @Column("s_dist_08")
    private String district8;

    @Column("s_dist_09")
    private String district9;

    @Column("s_dist_10")
    private String district10;

    @Column("s_data")
    private String extraData;

}
