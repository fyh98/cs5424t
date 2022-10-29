package com.cs5424t.ycql.Entities.PrimaryKeys;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@Data
@PrimaryKeyClass
public class OrderCustItemPK implements Serializable {

    @PrimaryKeyColumn(name = "o_w_id", type = PrimaryKeyType.PARTITIONED)
    private Integer warehouseId;

    @PrimaryKeyColumn(name = "o_d_id", type = PrimaryKeyType.CLUSTERED)
    private Integer districtId;

    @PrimaryKeyColumn(name = "o_id", type = PrimaryKeyType.CLUSTERED)
    private Integer id;

    public OrderCustItemPK(){}

    public OrderCustItemPK(Integer warehouseId, Integer districtId, Integer id) {
        this.warehouseId = warehouseId;
        this.districtId = districtId;
        this.id = id;
    }

    @Override
    public int hashCode() {
        return this.warehouseId.hashCode() + this.id.hashCode()
                + this.districtId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        final OrderCustItemPK other = (OrderCustItemPK) obj;

        if (this.warehouseId == null) {
            if (other.getWarehouseId() != null) return false;
        } else if (!this.warehouseId.equals(other.getWarehouseId())) return false;

        if (this.id == null) {
            if (other.getId() != null) return false;
        } else if (!this.id.equals(other.getId())) return false;

        if (this.districtId == null) {
            if (other.getDistrictId() != null) return false;
        } else if (!this.districtId.equals(other.getDistrictId())) return false;

        return true;
    }
}
