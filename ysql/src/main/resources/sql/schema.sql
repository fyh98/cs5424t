DROP TABLE IF EXISTS order_line_ysql;
DROP TABLE IF EXISTS "order_ysql";
DROP TABLE IF EXISTS customer_ysql;
DROP TABLE IF EXISTS stock_ysql;
DROP TABLE IF EXISTS district_ysql;
DROP TABLE IF EXISTS item_ysql;
DROP TABLE IF EXISTS warehouse_ysql;

create table if not exists warehouse_ysql(
    "w_id" int,
    "w_name" varchar(10),
    "w_street_1" varchar(20),
    "w_street_2" varchar(20),
    "w_city" varchar(20),
    "w_state" char(2),
    "w_zip" char(9),
    "w_tax" decimal(4,4),
    "w_ytd" decimal(12,2),
    primary key ("w_id")
) partition by range("w_id");

CREATE TABLE IF NOT EXISTS warehouse_ysql_s1_e2 PARTITION OF warehouse_ysql FOR VALUES FROM (1) TO (3);
CREATE TABLE IF NOT EXISTS warehouse_ysql_s3_e4 PARTITION OF warehouse_ysql FOR VALUES FROM (3) TO (5);
CREATE TABLE IF NOT EXISTS warehouse_ysql_s5_e6 PARTITION OF warehouse_ysql FOR VALUES FROM (5) TO (7);
CREATE TABLE IF NOT EXISTS warehouse_ysql_s7_e8 PARTITION OF warehouse_ysql FOR VALUES FROM (7) TO (9);
CREATE TABLE IF NOT EXISTS warehouse_ysql_s9_e10 PARTITION OF warehouse_ysql FOR VALUES FROM (9) TO (11);

create table if not exists district_ysql(
     "d_w_id" int,
     "d_id" int,
     "d_name" varchar(10),
     "d_street_1" varchar(20),
     "d_street_2" varchar(20),
     "d_city" varchar(20),
     "d_state" char(2),
     "d_zip" char(9),
     "d_tax" decimal(4,4),
     "d_ytd" decimal(12,2),
     "d_next_o_id" int,
     primary key ("d_w_id", "d_id")
) partition by range("d_w_id");

CREATE TABLE IF NOT EXISTS district_ysql_s1_e2 PARTITION OF district_ysql FOR VALUES FROM (1) TO (3);
CREATE TABLE IF NOT EXISTS district_ysql_s3_e4 PARTITION OF district_ysql FOR VALUES FROM (3) TO (5);
CREATE TABLE IF NOT EXISTS district_ysql_s5_e6 PARTITION OF district_ysql FOR VALUES FROM (5) TO (7);
CREATE TABLE IF NOT EXISTS district_ysql_s7_e8 PARTITION OF district_ysql FOR VALUES FROM (7) TO (9);
CREATE TABLE IF NOT EXISTS district_ysql_s9_e10 PARTITION OF district_ysql FOR VALUES FROM (9) TO (11);

create table if not exists item_ysql(
     "i_id" int,
     "i_name" varchar(24),
     "i_price" decimal(5,2),
     "i_im_id" int,
     "i_data" varchar(50),
     primary key ("i_id")
) partition by range("i_id");

CREATE TABLE IF NOT EXISTS item_ysql_s1_e2 PARTITION OF item_ysql FOR VALUES FROM (1) TO (20001);
CREATE TABLE IF NOT EXISTS item_ysql_s3_e4 PARTITION OF item_ysql FOR VALUES FROM (20001) TO (40001);
CREATE TABLE IF NOT EXISTS item_ysql_s5_e6 PARTITION OF item_ysql FOR VALUES FROM (40001) TO (60001);
CREATE TABLE IF NOT EXISTS item_ysql_s7_e8 PARTITION OF item_ysql FOR VALUES FROM (60001) TO (80001);
CREATE TABLE IF NOT EXISTS item_ysql_s9_e10 PARTITION OF item_ysql FOR VALUES FROM (80001) TO (100001);


create table if not exists stock_ysql(
      "s_w_id" int,
      "s_i_id" int,
      "s_quantity" decimal(4,0),
      "s_ytd" decimal(8,2),
      "s_order_cnt" int,
      "s_remote_cnt" int,
      "s_dist_01" char(24),
      "s_dist_02" char(24),
      "s_dist_03" char(24),
      "s_dist_04" char(24),
      "s_dist_05" char(24),
      "s_dist_06" char(24),
      "s_dist_07" char(24),
      "s_dist_08" char(24),
      "s_dist_09" char(24),
      "s_dist_10" char(24),
      "s_data" varchar(50),
      primary key ("s_w_id", "s_i_id")
) partition by range("s_w_id");

CREATE TABLE stock_ysql_s1_e2 PARTITION OF stock_ysql FOR VALUES FROM (1) TO (3);
CREATE TABLE stock_ysql_s3_e4 PARTITION OF stock_ysql FOR VALUES FROM (3) TO (5);
CREATE TABLE stock_ysql_s5_e6 PARTITION OF stock_ysql FOR VALUES FROM (5) TO (7);
CREATE TABLE stock_ysql_s7_e8 PARTITION OF stock_ysql FOR VALUES FROM (7) TO (9);
CREATE TABLE stock_ysql_s9_e10 PARTITION OF stock_ysql FOR VALUES FROM (9) TO (11);

create table if not exists customer_ysql(
       "c_w_id" int,
       "c_d_id" int,
       "c_id" int,
       "c_first" varchar(16),
       "c_middle" char(2),
       "c_last" varchar(16),
       "c_street_1" varchar(20),
       "c_street_2" varchar(20),
       "c_city" varchar(20),
       "c_state" char(2),
       "c_zip" char(9),
       "c_phone" char(16),
       "c_since" TIMESTAMP,
       "c_credit" CHAR(2),
       "c_credit_lim" decimal(12,2),
       "c_discount" decimal(4,4),
       "c_balance" decimal(12,2),
       "c_ytd_payment" float,
       "c_payment_cnt" int,
       "c_delivery_cnt" int,
       "c_data" varchar(500),
       primary key ("c_w_id", "c_d_id", "c_id")
) partition by range("c_w_id");

CREATE TABLE IF NOT EXISTS customer_ysql_s1_e2 PARTITION OF customer_ysql FOR VALUES FROM (1) TO (3);
CREATE TABLE IF NOT EXISTS customer_ysql_s3_e4 PARTITION OF customer_ysql FOR VALUES FROM (3) TO (5);
CREATE TABLE IF NOT EXISTS customer_ysql_s5_e6 PARTITION OF customer_ysql FOR VALUES FROM (5) TO (7);
CREATE TABLE IF NOT EXISTS customer_ysql_s7_e8 PARTITION OF customer_ysql FOR VALUES FROM (7) TO (9);
CREATE TABLE IF NOT EXISTS customer_ysql_s9_e10 PARTITION OF customer_ysql FOR VALUES FROM (9) TO (11);


create table if not exists "order_ysql"(
       "o_w_id" int,
       "o_d_id" int,
       "o_id" int,
       "o_c_id" int,
       "o_carrier_id" int,
       "o_ol_cnt" decimal(2,0),
       "o_all_local" decimal(1,0),
       "o_entry_d" timestamp,
       primary key ("o_w_id", "o_d_id", "o_id")
) partition by range("o_w_id");

CREATE TABLE IF NOT EXISTS order_ysql_s1_e2 PARTITION OF "order_ysql" FOR VALUES FROM (1) TO (3);
CREATE TABLE IF NOT EXISTS order_ysql_s3_e4 PARTITION OF "order_ysql" FOR VALUES FROM (3) TO (5);
CREATE TABLE IF NOT EXISTS order_ysql_s5_e6 PARTITION OF "order_ysql" FOR VALUES FROM (5) TO (7);
CREATE TABLE IF NOT EXISTS order_ysql_s7_e8 PARTITION OF "order_ysql" FOR VALUES FROM (7) TO (9);
CREATE TABLE IF NOT EXISTS order_ysql_s9_e10 PARTITION OF "order_ysql" FOR VALUES FROM (9) TO (11);

create table if not exists order_line_ysql(
       "ol_w_id" int,
       "ol_d_id" int,
       "ol_o_id" int,
       "ol_number" int,
       "ol_i_id" int,
       "ol_delivery_id" timestamp,
       "ol_amount" decimal(7,2),
       "ol_supply_w_id" int,
       "ol_quantity" decimal(2,0),
       "ol_dist_info" char(24),
       primary key ("ol_w_id", "ol_d_id", "ol_o_id", "ol_number")
) partition by range("ol_w_id");

CREATE TABLE IF NOT EXISTS order_line_ysql_s1_e2 PARTITION OF "order_line_ysql" FOR VALUES FROM (1) TO (3);
CREATE TABLE IF NOT EXISTS order_line_ysql_s3_e4 PARTITION OF "order_line_ysql" FOR VALUES FROM (3) TO (5);
CREATE TABLE IF NOT EXISTS order_line_ysql_s5_e6 PARTITION OF "order_line_ysql" FOR VALUES FROM (5) TO (7);
CREATE TABLE IF NOT EXISTS order_line_ysql_s7_e8 PARTITION OF "order_line_ysql" FOR VALUES FROM (7) TO (9);
CREATE TABLE IF NOT EXISTS order_line_ysql_s9_e10 PARTITION OF "order_line_ysql" FOR VALUES FROM (9) TO (11);

copy warehouse_ysql from '/temp/project_files/data_files/warehouse.csv' delimiter ',';
copy district_ysql from '/temp/project_files/data_files/district.csv' delimiter ',';
copy item_ysql from '/temp/project_files/data_files/item.csv' delimiter ',';
copy customer_ysql from '/temp/project_files/data_files/customer.csv' delimiter ',';
create index index_customer_ysql_balance on customer_ysql(c_balance desc);
copy stock_ysql from '/temp/project_files/data_files/stock.csv' delimiter ',';
copy order_ysql from '/temp/project_files/data_files/order.csv' with(delimiter ',', format csv , null 'null');
copy order_line_ysql from '/temp/project_files/data_files/order-line.csv' with(delimiter ',', format csv , null 'null');
