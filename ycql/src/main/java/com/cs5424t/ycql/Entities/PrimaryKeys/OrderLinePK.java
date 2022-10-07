package com.cs5424t.ycql.Entities.PrimaryKeys;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@Data
@PrimaryKeyClass
public class OrderLinePK implements Serializable {

    @PrimaryKeyColumn(name = "ol_w_id", type = PrimaryKeyType.PARTITIONED)
    private Integer warehouseId;

    @PrimaryKeyColumn(name = "ol_d_id", type = PrimaryKeyType.CLUSTERED)
    private Integer districtId;

    @PrimaryKeyColumn(name = "ol_o_id", type = PrimaryKeyType.CLUSTERED)
    private Integer orderId;

    @PrimaryKeyColumn(name = "ol_number", type = PrimaryKeyType.CLUSTERED)
    private Integer id;

    public OrderLinePK() {}

    public OrderLinePK(Integer warehouseId, Integer districtId, Integer orderId, Integer id) {
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.orderId = orderId;
        this.id = id;
    }

    @Override
    public int hashCode() {
        return this.warehouseId.hashCode() + this.id.hashCode()
                + this.districtId.hashCode() + this.orderId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        final OrderLinePK other = (OrderLinePK) obj;

        if (this.warehouseId == null) {
            if (other.getWarehouseId() != null) return false;
        } else if (!this.warehouseId.equals(other.getWarehouseId())) return false;

        if (this.id == null) {
            if (other.getId() != null) return false;
        } else if (!this.id.equals(other.getId())) return false;

        if (this.districtId == null) {
            if (other.getDistrictId() != null) return false;
        } else if (!this.districtId.equals(other.getDistrictId())) return false;

        if (this.orderId == null) {
            if (other.getOrderId() != null) return false;
        } else if (!this.orderId.equals(other.getOrderId())) return false;

        return true;
    }
}
