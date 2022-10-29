package com.cs5424t.ycql.Entities;

import com.cs5424t.ycql.Entities.PrimaryKeys.CustomerPK;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Table("customer_test_ycql")
@Data
public class Customer implements Serializable, Comparable<Customer> {
    @Serial
    private static final long serialVersionUID = 15L;

    @PrimaryKey
    private CustomerPK customerPK;

    @Column("c_first")
    private String firstName;

    @Column("c_middle")
    private String middleName;

    @Column("c_last")
    private String lastName;

    @Column("c_street_1")
    private String street1;

    @Column("c_street_2")
    private String street2;

    @Column("c_city")
    private String city;

    @Column("c_state")
    private String state;

    @Column("c_zip")
    private String zipcode;

    @Column("c_phone")
    private String phone;

    @Column("c_since")
    private Timestamp createTime;

    @Column("c_credit")
    private String creditStatus;

    @Column("c_credit_lim")
    private BigDecimal creditLimit;

    @Column("c_discount")
    private BigDecimal discountRate;

    //@Column("c_balance")
    //private BigDecimal balance;

    @Column("c_ytd_payment")
    private Float ytdPayment;

    @Column("c_payment_cnt")
    private Integer numOfPayment;

    @Column("c_delivery_cnt")
    private Integer numOfDelivery;

    @Column("c_data")
    private String extraData;

	@Override
	public int compareTo(Customer o) {
		// TODO Auto-generated method stub
		
		return o.customerPK.getBalance().compareTo(this.customerPK.getBalance());
	}
	
}
