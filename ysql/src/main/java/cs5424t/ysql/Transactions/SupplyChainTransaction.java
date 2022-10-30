package cs5424t.ysql.Transactions;

import cs5424t.ysql.Entities.PrimaryKeys.CustomerPK;
import cs5424t.ysql.Entities.PrimaryKeys.DistrictPK;
import cs5424t.ysql.Entities.PrimaryKeys.OrderPK;
import cs5424t.ysql.Entities.Xcnd45.*;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface SupplyChainTransaction {
    void newOrder(Integer warehouseId, Integer districtId, Integer customerId,
                         Integer itemTotalNum,
                         List<Integer> itemNumber, List<Integer> supplierWarehouse,
                         List<Integer> quantity);


    void payment(Integer warehouseId, Integer districtId, Integer customerId,
                        BigDecimal paymentAmount);

    void delivery(Integer warehouseId, Integer carrierId);

    void orderStatus(Integer warehouseId, Integer districtId, Integer customerId);

    void stockLevel(Integer warehouseId, Integer districtId, BigDecimal threshold, Integer numLastOrders);

    void popularItem(Integer warehouseId, Integer districtId, Integer numLastOrders);

    void topBalance();

    void relatedCustomer(Integer warehouseId, Integer districtId, Integer customerId);

    List<Object> measurePerformance();
}
