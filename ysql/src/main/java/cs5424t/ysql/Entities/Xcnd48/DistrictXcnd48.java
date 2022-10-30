package cs5424t.ysql.Entities.Xcnd48;

import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "district_ysql")
@IdClass(DistrictPK.class)
@Data
public class DistrictXcnd48 implements Serializable {
    @Serial
    private static final long serialVersionUID = 15L;

    @Id
    @Column(name = "d_w_id")
    private Integer warehouseId;

    @Id
    @Column(name = "d_id")
    private Integer id;

    @Column(name = "d_name")
    private String name;

    @Column(name = "d_street_1")
    private String street1;

    @Column(name = "d_street_2")
    private String street2;

    @Column(name = "d_city")
    private String city;

    @Column(name = "d_state")
    private String state;

    @Column(name = "d_zip")
    private String zipcode;

    @Column(name = "d_tax")
    private BigDecimal tax;

    @Column(name = "d_ytd")
    private BigDecimal ytd;

    @Column(name = "d_next_o_id")
    private Integer nextAvailOrderNum;
}
