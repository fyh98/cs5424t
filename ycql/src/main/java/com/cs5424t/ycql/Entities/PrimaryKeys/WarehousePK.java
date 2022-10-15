package com.cs5424t.ycql.Entities.PrimaryKeys;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@Data
@PrimaryKeyClass
public class WarehousePK {
    @PrimaryKeyColumn(value = "w_id", type = PrimaryKeyType.PARTITIONED)
    private Integer id;

    public WarehousePK(){}

    public WarehousePK(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        final ItemPK other = (ItemPK) obj;

        if (this.id == null) {
            return other.getId() == null;
        } else return this.id.equals(other.getId());
    }
}
