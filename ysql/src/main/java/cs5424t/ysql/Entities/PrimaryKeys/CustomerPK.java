package cs5424t.ysql.Entities.PrimaryKeys;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerPK implements Serializable {

    private Integer warehouseId;

    private Integer districtId;

    private Integer id;

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
