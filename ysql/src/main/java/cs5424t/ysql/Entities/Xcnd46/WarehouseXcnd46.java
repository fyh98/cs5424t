package cs5424t.ysql.Entities.Xcnd46;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "warehouse_ysql")
@Data
public class WarehouseXcnd46 {
    @Id
    @Column(name = "w_id")
    private Integer id;

    @Column(name = "w_name")
    private String name;

    @Column(name = "w_street_1")
    private String street1;

    @Column(name = "w_street_2")
    private String street2;

    @Column(name = "w_city")
    private String city;

    @Column(name = "w_state")
    private String state;

    @Column(name = "w_zip")
    private String zipcode;

    @Column(name = "w_tax")
    private BigDecimal tax;

    @Column(name = "w_ytd")
    private BigDecimal ytd;

}
