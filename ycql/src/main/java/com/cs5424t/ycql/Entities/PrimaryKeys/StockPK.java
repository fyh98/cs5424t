package com.cs5424t.ycql.Entities.PrimaryKeys;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@Data
@PrimaryKeyClass
public class StockPK implements Serializable {

    @PrimaryKeyColumn(name = "s_w_id", type = PrimaryKeyType.PARTITIONED)
    private Integer warehouseId;

    @PrimaryKeyColumn(name = "s_i_id", type = PrimaryKeyType.CLUSTERED)
    private Integer itemId;

    public StockPK(){}

    public StockPK(Integer warehouseId, Integer itemId) {
        this.warehouseId = warehouseId;
        this.itemId = itemId;
    }

    @Override
    public int hashCode() {
        return this.warehouseId.hashCode() + this.itemId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        final StockPK other = (StockPK) obj;

        if (this.warehouseId == null) {
            if (other.getWarehouseId() != null) return false;
        } else if (!this.warehouseId.equals(other.getWarehouseId())) return false;

        if (this.itemId == null) {
            if (other.getItemId() != null) return false;
        } else if (!this.itemId.equals(other.getItemId())) return false;

        return true;
    }
}
