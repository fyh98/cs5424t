package cs5424t.ysql.Entities.Xcnd47;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "item_ysql")
@Data
public class ItemXcnd47 {

    @Id
    @Column(name = "i_id")
    private Integer id;

    @Column(name = "i_name")
    private String name;

    @Column(name = "i_price")
    private BigDecimal price;

    @Column(name = "i_im_id")
    private Integer imageId;

    @Column(name = "i_data")
    private String brandInfo;
}
