package cs5424t.ysql.Entities.Xcnd48;

import cs5424t.ysql.Entities.PrimaryKeys.OrderPK;
import lombok.Data;

import javax.persistence.*;
//import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "order_ysql")
@IdClass(OrderPK.class)
@Data
public class OrderXcnd48 implements Serializable {
//    @Serial
    private static final long serialVersionUID = 15L;

    @Id
    @Column(name = "o_w_id")
    private Integer warehouseId;

    @Id
    @Column(name = "o_d_id")
    private Integer districtId;

    @Id
    @Column(name = "o_id")
    private Integer id;

    @Column(name = "o_c_id")
    private Integer customerId;

    @Column(name = "o_carrier_id")
    private Integer carrierId;

    @Column(name = "o_ol_cnt")
    private BigDecimal numOfItems;

    @Column(name = "o_all_local")
    private BigDecimal status;

    @Column(name = "o_entry_d")
    private Timestamp createTime;

}
