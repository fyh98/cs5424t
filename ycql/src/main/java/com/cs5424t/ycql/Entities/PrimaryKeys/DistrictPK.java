package com.cs5424t.ycql.Entities.PrimaryKeys;

import lombok.Data;

import java.io.Serializable;


@Data
public class DistrictPK implements Serializable {

    private Integer warehouseId;

    private Integer id;

    public DistrictPK(Integer warehouseId, Integer id) {
        this.warehouseId = warehouseId;
        this.id = id;
    }

    public DistrictPK(){}

    @Override
    public int hashCode() {
        return this.warehouseId.hashCode() + this.id.hashCode();
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        final DistrictPK other = (DistrictPK) obj;

        if (this.warehouseId == null) {
            if (other.getWarehouseId() != null) return false;
        } else if (!this.warehouseId.equals(other.getWarehouseId())) return false;

        if (this.id == null) {
            if (other.getId() != null) return false;
        } else if (!this.id.equals(other.getId())) return false;

        return true;
    }
}
