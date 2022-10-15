CREATE KEYSPACE IF NOT EXISTS ycql WITH
REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '3' }
AND durable_writes = true;

USE ycql;

DROP TABLE IF EXISTS order_line_ycql;
DROP TABLE IF EXISTS "order_ycql";
DROP TABLE IF EXISTS customer_ycql;
DROP TABLE IF EXISTS stock_ycql;
DROP TABLE IF EXISTS district_ycql;
DROP TABLE IF EXISTS item_ycql;
DROP TABLE IF EXISTS warehouse_ycql;

create table if not exists warehouse_ycql(
    "w_id" int,
    "w_name" varchar,
    "w_street_1" varchar,
    "w_street_2" varchar,
    "w_city" varchar,
    "w_state" varchar,
    "w_zip" varchar,
    "w_tax" decimal,
    "w_ytd" decimal,
    primary key ("w_id")
)  WITH compaction = { 'class' : 'LeveledCompactionStrategy' };


create table if not exists district_ycql(
    "d_w_id" int,
    "d_id" int,
    "d_name" varchar,
    "d_street_1" varchar,
    "d_street_2" varchar,
    "d_city" varchar,
    "d_state" varchar,
    "d_zip" varchar,
    "d_tax" decimal,
    "d_ytd" decimal,
    "d_next_o_id" int,
    primary key (("d_w_id"), "d_id")
) WITH compaction = { 'class' : 'LeveledCompactionStrategy' };


create table if not exists item_ycql(
    "i_id" int,
    "i_name" varchar,
    "i_price" decimal,
    "i_im_id" int,
    "i_data" varchar,
    primary key ("i_id")
) WITH compaction = { 'class' : 'LeveledCompactionStrategy' };


create table if not exists stock_ycql(
    "s_w_id" int,
    "s_i_id" int,
    "s_quantity" decimal,
    "s_ytd" decimal,
    "s_order_cnt" int,
    "s_remote_cnt" int,
    "s_dist_01" varchar,
    "s_dist_02" varchar,
    "s_dist_03" varchar,
    "s_dist_04" varchar,
    "s_dist_05" varchar,
    "s_dist_06" varchar,
    "s_dist_07" varchar,
    "s_dist_08" varchar,
    "s_dist_09" varchar,
    "s_dist_10" varchar,
    "s_data" varchar,
    primary key (("s_w_id"), "s_i_id")
) WITH compaction = { 'class' : 'LeveledCompactionStrategy' };


create table if not exists customer_ycql(
    "c_w_id" int,
    "c_d_id" int,
    "c_id" int,
    "c_first" varchar,
    "c_middle" varchar,
    "c_last" varchar,
    "c_street_1" varchar,
    "c_street_2" varchar,
    "c_city" varchar,
    "c_state" varchar,
    "c_zip" varchar,
    "c_phone" varchar,
    "c_since" TIMESTAMP,
    "c_credit" varchar,
    "c_credit_lim" decimal,
    "c_discount" decimal,
    "c_balance" decimal,
    "c_ytd_payment" float,
    "c_payment_cnt" int,
    "c_delivery_cnt" int,
    "c_data" text,
    primary key (("c_w_id"), "c_d_id", "c_id")
) WITH compaction = { 'class' : 'LeveledCompactionStrategy' };


create table if not exists "order_ycql"(
    "o_w_id" int,
    "o_d_id" int,
    "o_id" int,
    "o_c_id" int,
    "o_carrier_id" int,
    "o_ol_cnt" decimal,
    "o_all_local" decimal,
    "o_entry_d" timestamp,
    primary key (("o_w_id"), "o_d_id", "o_id")
) WITH compaction = { 'class' : 'LeveledCompactionStrategy' };


create table if not exists order_line_ycql(
    "ol_w_id" int,
    "ol_d_id" int,
    "ol_o_id" int,
    "ol_number" int,
    "ol_i_id" int,
    "ol_delivery_id" timestamp,
    "ol_amount" decimal,
    "ol_supply_w_id" int,
    "ol_quantity" decimal,
    "ol_dist_info" varchar,
    primary key (("ol_w_id"), "ol_d_id", "ol_o_id", "ol_number")
) WITH compaction = { 'class' : 'LeveledCompactionStrategy' };


-- copy warehouse_ycql from '/temp/project_files/data_files/warehouse.csv' with header = false;
-- copy district_ycql from '/temp/project_files/data_files/district.csv' with header = false;
-- copy item_ycql from '/temp/project_files/data_files/item.csv' with header = false;
-- copy customer_ycql from '/temp/project_files/data_files/customer.csv' with header = false;
-- copy stock_ycql from '/temp/project_files/data_files/stock.csv' with header = false and INGESTRATE = 10000;
-- copy order_ycql from '/temp/project_files/data_files/order.csv' with header = false and null = null and INGESTRATE = 5000;
-- copy order_line_ycql from '/temp/project_files/data_files/order-line.csv' with header = false and null = null and INGESTRATE = 5000;
