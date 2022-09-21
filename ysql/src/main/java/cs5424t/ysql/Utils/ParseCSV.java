package cs5424t.ysql.Utils;

import cs5424t.ysql.DAO.*;
import cs5424t.ysql.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParseCSV {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    public void loadData() throws Exception{
        System.out.println("Started inserting data, do not terminate program...");
        long start = System.currentTimeMillis();

        Thread t1 = new Thread(() -> loadDataForItem("D:\\Courses\\CS5424 Distributed Database" +
                "\\project\\project_files\\data_files\\item.csv"));

        Thread t2 = new Thread(() -> loadDataForWarehouse("D:\\Courses\\CS5424 Distributed Database" +
                "\\project\\project_files\\data_files\\warehouse.csv"));

        Thread t3 = new Thread(() -> loadDataForDistrict("D:\\Courses\\CS5424 Distributed Database" +
                "\\project\\project_files\\data_files\\district.csv"));

        Thread t4 = new Thread(() -> loadDataForCustomer("D:\\Courses\\CS5424 Distributed Database" +
                "\\project\\project_files\\data_files\\customer.csv"));

        Thread t5 = new Thread(() -> loadDataForOrder("D:\\Courses\\CS5424 Distributed Database" +
                "\\project\\project_files\\data_files\\order.csv"));

        Thread t6 = new Thread(() -> loadDataForOrderline("D:\\Courses\\CS5424 Distributed Database" +
                "\\project\\project_files\\data_files\\order-line.csv"));

        Thread t7 = new Thread(() -> loadDataForStock("D:\\Courses\\CS5424 Distributed Database" +
                "\\project\\project_files\\data_files\\stock.csv"));

        t1.start();
        t4.start();
        t5.start();

        t1.join();
        t4.join();
        t5.join();

        t2.start();
        t3.start();
        t6.start();
        t7.start();

        t2.join();
        t3.join();
        t6.join();
        t7.join();

        long end = System.currentTimeMillis();
        System.out.println("Finished inserting data...");
        System.out.println("Consuming time: " + (end - start));
    }

    public void loadDataForOrderline(String filePath){
        File inFile = new File(filePath);
        List<OrderLine> ls = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("Orderline started...");
        try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {

            while (reader.ready()) {
                String[] fields = reader.readLine().split(",");
                if(fields.length == 0) continue;
                OrderLine orderLine = new OrderLine();
                orderLine.setWarehouseId(Integer.valueOf(fields[0]));
                orderLine.setDistrictId(Integer.valueOf(fields[1]));
                orderLine.setOrderId(Integer.valueOf(fields[2]));
                orderLine.setId(Integer.valueOf(fields[3]));
                orderLine.setItemId(Integer.valueOf(fields[4]));

                if(fields[5].equals("null")){
                    orderLine.setDeliveryDate(null);
                } else {
                    long time = sdf.parse(fields[5]).getTime();
                    orderLine.setDeliveryDate(new Timestamp(time));
                }

                orderLine.setTotalPrice(BigDecimal.valueOf(Double.parseDouble(fields[6])));
                orderLine.setSupplyWarehouseId(Integer.valueOf(fields[7]));
                orderLine.setQuantity(BigDecimal.valueOf(Double.parseDouble(fields[8])));
                orderLine.setExtraData(fields[9]);
                ls.add(orderLine);

                if(ls.size() >= 5000){
                    orderLineRepository.saveAll(ls);
                    ls.clear();
                    System.out.println("order line insert once...");
                }
            }
            if(ls.size() > 0){
                orderLineRepository.saveAll(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Orderline ended...");
    }

    public void loadDataForOrder(String filePath){
        File inFile = new File(filePath);
        List<Order> ls = new ArrayList<>();
        System.out.println("Order started...");
        try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {

            while (reader.ready()) {
                String[] fields = reader.readLine().split(",");
                if(fields.length == 0) continue;
                Order order = new Order();
                order.setWarehouseId(Integer.valueOf(fields[0]));
                order.setDistrictId(Integer.valueOf(fields[1]));
                order.setId(Integer.valueOf(fields[2]));
                order.setCustomerId(Integer.valueOf(fields[3]));

                if(fields[4].equals("null")){
                    order.setCarrierId(null);
                } else {
                    order.setCarrierId(Integer.valueOf(fields[4]));
                }

                order.setNumOfItems(BigDecimal.valueOf(Double.parseDouble(fields[5])));
                order.setStatus(BigDecimal.valueOf(Double.parseDouble(fields[6])));
                order.setCreateTime(Timestamp.valueOf(fields[7]));
                ls.add(order);
                if(ls.size() >= 5000){
                    orderRepository.saveAll(ls);
                    ls.clear();
                    System.out.println("order insert once...");
                }
            }
            if(ls.size() > 0){
                orderRepository.saveAll(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Order ended...");
    }

    public void loadDataForCustomer(String filePath){
        File inFile = new File(filePath);
        List<Customer> ls = new ArrayList<>();
        System.out.println("Customer started...");
        try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {

            while (reader.ready()) {
                String[] fields = reader.readLine().split(",");
                if(fields.length == 0) continue;
                Customer customer = new Customer();
                customer.setWarehouseId(Integer.valueOf(fields[0]));
                customer.setDistrictId(Integer.valueOf(fields[1]));
                customer.setId(Integer.valueOf(fields[2]));
                customer.setFirstName(fields[3]);
                customer.setMiddleName(fields[4]);
                customer.setLastName(fields[5]);
                customer.setStreet1(fields[6]);
                customer.setStreet2(fields[7]);
                customer.setCity(fields[8]);
                customer.setState(fields[9]);
                customer.setZipcode(fields[10]);
                customer.setPhone(fields[11]);
                customer.setCreateTime(Timestamp.valueOf(fields[12]));
                customer.setCreditStatus(fields[13]);
                customer.setCreditLimit(BigDecimal.valueOf(Double.parseDouble(fields[14])));
                customer.setDiscountRate(BigDecimal.valueOf(Double.parseDouble(fields[15])));
                customer.setBalance(BigDecimal.valueOf(Double.parseDouble(fields[16])));
                customer.setYtdPayment(Double.valueOf(fields[17]));
                customer.setNumOfPayment(Integer.valueOf(fields[18]));
                customer.setNumOfDelivery(Integer.valueOf(fields[19]));
                customer.setExtraData(fields[20]);
                ls.add(customer);
                if(ls.size() >= 5000){
                    customerRepository.saveAll(ls);
                    ls.clear();
                    System.out.println("customer insert once...");
                }
            }
            if(ls.size() > 0){
                customerRepository.saveAll(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Customer ended...");
    }

    public void loadDataForStock(String filePath){
        File inFile = new File(filePath);
        List<Stock> ls = new ArrayList<>();
        System.out.println("Stock started...");
        try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {

            while (reader.ready()) {
                String[] fields = reader.readLine().split(",");
                if(fields.length == 0) continue;
                Stock stock = new Stock();
                stock.setWarehouseId(Integer.valueOf(fields[0]));
                stock.setItemId(Integer.valueOf(fields[1]));
                stock.setStockNum(BigDecimal.valueOf(Integer.valueOf(fields[2])));
                stock.setYtd(BigDecimal.valueOf(Double.parseDouble(fields[3])));
                stock.setNumOfOrder(Integer.valueOf(fields[4]));
                stock.setNumOfRemoteOrder(Integer.valueOf(fields[5]));
                stock.setDistrict1(fields[6]);
                stock.setDistrict2(fields[7]);
                stock.setDistrict3(fields[8]);
                stock.setDistrict4(fields[9]);
                stock.setDistrict5(fields[10]);
                stock.setDistrict6(fields[11]);
                stock.setDistrict7(fields[12]);
                stock.setDistrict8(fields[13]);
                stock.setDistrict9(fields[14]);
                stock.setDistrict10(fields[15]);
                stock.setExtraData(fields[16]);
                ls.add(stock);

                if(ls.size() >= 5000){
                    stockRepository.saveAll(ls);
                    ls.clear();
                    System.out.println("stock insert once...");
                }
            }
            if(ls.size() > 0){
                stockRepository.saveAll(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Stock ended...");
    }

    public void loadDataForDistrict(String filePath){
        File inFile = new File(filePath);
        List<District> ls = new ArrayList<>();
        System.out.println("District started...");
        try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {
            while (reader.ready()) {
                String[] fields = reader.readLine().split(",");
                if(fields.length == 0) continue;
                District district = new District();
                district.setWarehouseId(Integer.valueOf(fields[0]));
                district.setId(Integer.valueOf(fields[1]));
                district.setName(fields[2]);
                district.setStreet1(fields[3]);
                district.setStreet2(fields[4]);
                district.setCity(fields[5]);
                district.setState(fields[6]);
                district.setZipcode(fields[7]);
                district.setTax(BigDecimal.valueOf(Double.parseDouble(fields[8])));
                district.setYtd(BigDecimal.valueOf(Double.parseDouble(fields[9])));
                district.setNextAvailOrderNum(Integer.valueOf(fields[10]));
                ls.add(district);

                if(ls.size() >= 5000){
                    districtRepository.saveAll(ls);
                    ls.clear();
                    System.out.println("district insert once...");
                }
            }
            if(ls.size() > 0){
                districtRepository.saveAll(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("District ended...");
    }

    public void loadDataForWarehouse(String filePath) {
        File inFile = new File(filePath);
        List<Warehouse> ls = new ArrayList<>();
        System.out.println("Warehouse started...");
        try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {

            while (reader.ready()) {
                String[] fields = reader.readLine().split(",");
                if(fields.length == 0) continue;
                Warehouse warehouse = new Warehouse();
                warehouse.setId(Integer.valueOf(fields[0]));
                warehouse.setName(fields[1]);
                warehouse.setStreet1(fields[2]);
                warehouse.setStreet2(fields[3]);
                warehouse.setCity(fields[4]);
                warehouse.setState(fields[5]);
                warehouse.setZipcode(fields[6]);
                warehouse.setTax(BigDecimal.valueOf(Double.parseDouble(fields[7])));
                warehouse.setYtd(BigDecimal.valueOf(Double.parseDouble(fields[8])));
                ls.add(warehouse);

                if(ls.size() >= 5000){
                    warehouseRepository.saveAll(ls);
                    ls.clear();
                    System.out.println("warehouse insert once...");
                }
            }
            if(ls.size() > 0){
                warehouseRepository.saveAll(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Warehouse ended...");
    }

    public void loadDataForItem(String filePath) {
        File inFile = new File(filePath);
        List<Item> ls = new ArrayList<>();
        System.out.println("Item started...");
        try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {

            while (reader.ready()) {
                String[] fields = reader.readLine().split(",");
                if(fields.length == 0) continue;
                Item item = new Item();
                item.setId(Integer.valueOf(fields[0]));
                item.setName(fields[1]);
                item.setPrice(BigDecimal.valueOf(Double.parseDouble(fields[2])));
                item.setImageId(Integer.valueOf(fields[3]));
                item.setBrandInfo(fields[4]);
                ls.add(item);

                if(ls.size() >= 5000){
                    itemRepository.saveAll(ls);
                    ls.clear();
                    System.out.println("item insert once...");
                }
            }

            if(ls.size() > 0){
                itemRepository.saveAll(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Item ended...");
    }
}
