package com.cs5424t.ycql.Entities;

import com.cs5424t.ycql.Entities.PrimaryKeys.DistrictPK;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Table("district_ycql")
@Data
public class District implements Serializable {
    @Serial
    private static final long serialVersionUID = 15L;

    @PrimaryKey
    private DistrictPK districtPK;

    @Column("d_name")
    private String name;

    @Column("d_street_1")
    private String street1;

    @Column("d_street_2")
    private String street2;

    @Column("d_city")
    private String city;

    @Column("d_state")
    private String state;

    @Column("d_zip")
    private String zipcode;

    @Column("d_tax")
    private BigDecimal tax;

    @Column("d_ytd")
    private BigDecimal ytd;

    @Column("d_next_o_id")
    private Integer nextAvailOrderNum;
}
