package cs5424t.ysql.Entities.Xcnd46;

import cs5424t.ysql.Entities.PrimaryKeys.StockPK;
import lombok.Data;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "stock_ysql")
@IdClass(StockPK.class)
@Data
public class StockXcnd46 implements Serializable {

    @Serial
    private static final long serialVersionUID = 15L;

    @Id
    @Column(name = "s_w_id")
    private Integer warehouseId;

    @Id
    @Column(name = "s_i_id")
    private Integer itemId;

    @Column(name = "s_quantity")
    private BigDecimal stockNum;

    @Column(name = "s_ytd")
    private BigDecimal ytd;

    @Column(name = "s_order_cnt")
    private Integer numOfOrder;

    @Column(name = "s_remote_cnt")
    private Integer numOfRemoteOrder;

    @Column(name = "s_dist_01")
    private String district1;

    @Column(name = "s_dist_02")
    private String district2;

    @Column(name = "s_dist_03")
    private String district3;

    @Column(name = "s_dist_04")
    private String district4;

    @Column(name = "s_dist_05")
    private String district5;

    @Column(name = "s_dist_06")
    private String district6;

    @Column(name = "s_dist_07")
    private String district7;

    @Column(name = "s_dist_08")
    private String district8;

    @Column(name = "s_dist_09")
    private String district9;

    @Column(name = "s_dist_10")
    private String district10;

    @Column(name = "s_data")
    private String extraData;

}
