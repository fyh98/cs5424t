package com.cs5424t.ycql.Entities;

import com.cs5424t.ycql.Entities.PrimaryKeys.ItemPK;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


import java.math.BigDecimal;

@Table("item_ycql")
@Data
public class Item{

    @PrimaryKey
    private ItemPK itemPK;

    @Column("i_name")
    private String name;

    @Column("i_price")
    private BigDecimal price;

    @Column("i_im_id")
    private Integer imageId;

    @Column("i_data")
    private String brandInfo;
}
