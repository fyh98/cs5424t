package com.cs5424t.ycql.Entities.PrimaryKeys;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@PrimaryKeyClass
public class CustomerPK implements Serializable {

    @PrimaryKeyColumn(name = "c_w_id", type = PrimaryKeyType.PARTITIONED)
    private Integer warehouseId;
    
    @PrimaryKeyColumn(name = "c_d_id", type = PrimaryKeyType.CLUSTERED)
    private Integer districtId;

    @PrimaryKeyColumn(name = "c_id", type = PrimaryKeyType.CLUSTERED)
    private Integer id;
    
    @PrimaryKeyColumn(name = "c_balance", type = PrimaryKeyType.CLUSTERED)
    private BigDecimal balance;
    
    public CustomerPK(Integer wareHouseId, Integer districtId, Integer id) {
        this.warehouseId = wareHouseId;
        this.districtId = districtId;
        this.id = id;
    }

    public CustomerPK(){}

    @Override
    public int hashCode() {
        return this.warehouseId.hashCode() + this.id.hashCode()
                + this.districtId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        final CustomerPK other = (CustomerPK) obj;

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
