create table products
(
    id         bigint not null
        constraint product_id_pk
            primary key,
    name varchar
        constraint product_name_pk
            unique not null ,
    price     decimal,
    quantity  Integer,
    active     boolean default true
);
alter table products
    owner to levan;

create unique index products_id_uindex
    on products (id);

create sequence "products_id_seq";

alter sequence "products_id_seq" owned by products.id;

alter table products
    alter column id set default nextval('products_id_seq'::regclass);