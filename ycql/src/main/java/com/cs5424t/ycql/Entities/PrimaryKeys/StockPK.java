package com.cs5424t.ycql.Entities.PrimaryKeys;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockPK implements Serializable {
    private Integer warehouseId;

    private Integer itemId;

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
