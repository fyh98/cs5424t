package cs5424t.ysql.Entities.Xcnd45;

import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "customer_ysql")
@IdClass(CustomerPK.class)
@Data
public class CustomerXcnd45 implements Serializable {
    @Serial
    private static final long serialVersionUID = 15L;

    @Id
    @Column(name = "c_w_id")
    private Integer warehouseId;

    @Id
    @Column(name = "c_d_id")
    private Integer districtId;

    @Id
    @Column(name = "c_id")
    private Integer id;

    @Column(name = "c_first")
    private String firstName;

    @Column(name = "c_middle")
    private String middleName;

    @Column(name = "c_last")
    private String lastName;

    @Column(name = "c_street_1")
    private String street1;

    @Column(name = "c_street_2")
    private String street2;

    @Column(name = "c_city")
    private String city;

    @Column(name = "c_state")
    private String state;

    @Column(name = "c_zip")
    private String zipcode;

    @Column(name = "c_phone")
    private String phone;

    @Column(name = "c_since")
    private Timestamp createTime;

    @Column(name = "c_credit")
    private String creditStatus;

    @Column(name = "c_credit_lim")
    private BigDecimal creditLimit;

    @Column(name = "c_discount")
    private BigDecimal discountRate;

    @Column(name = "c_balance")
    private BigDecimal balance;

    @Column(name = "c_ytd_payment")
    private Double ytdPayment;

    @Column(name = "c_payment_cnt")
    private Integer numOfPayment;

    @Column(name = "c_delivery_cnt")
    private Integer numOfDelivery;

    @Column(name = "c_data")
    private String extraData;
}
