-- Database: LiveCodeSpringBoot

-- DROP DATABASE IF EXISTS "LiveCodeSpringBoot";

CREATE DATABASE "LiveCodeSpringBoot"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en-US'
    LC_CTYPE = 'en-US'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- Table: public.m_course

-- DROP TABLE IF EXISTS public.m_course;

CREATE TABLE IF NOT EXISTS public.m_course
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    birth_date character varying(255) COLLATE pg_catalog."default" NOT NULL,
    birth_place character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    created_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    updated_at timestamp(6) without time zone,
    updated_by character varying(255) COLLATE pg_catalog."default",
    user_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT m_course_pkey PRIMARY KEY (id),
    CONSTRAINT ukkqdl6jqkcnhp12ak9olsx668p UNIQUE (user_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.m_course
    OWNER to postgres;

-- Table: public.m_role

-- DROP TABLE IF EXISTS public.m_role;

CREATE TABLE IF NOT EXISTS public.m_role
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT m_role_pkey PRIMARY KEY (id),
    CONSTRAINT ukjocup4k4l93c55lc4q47r3ves UNIQUE (name),
    CONSTRAINT m_role_name_check CHECK (name::text = ANY (ARRAY['ROLE_CUSTOMER'::character varying, 'ROLE_STAFF'::character varying, 'ROLE_ADMIN'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.m_role
    OWNER to postgres;

-- Table: public.m_transaction

-- DROP TABLE IF EXISTS public.m_transaction;

CREATE TABLE IF NOT EXISTS public.m_transaction
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    net_amount_paid double precision NOT NULL,
    payment_method character varying(255) COLLATE pg_catalog."default",
    payment_status character varying(255) COLLATE pg_catalog."default",
    total_amount_paid double precision NOT NULL,
    total_tax_paid double precision NOT NULL,
    transaction_time timestamp(6) without time zone NOT NULL,
    customer_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT m_transaction_pkey PRIMARY KEY (id),
    CONSTRAINT fkeenf6v9ffuha4yroxxido37yj FOREIGN KEY (customer_id)
        REFERENCES public.m_course (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT m_transaction_payment_method_check CHECK (payment_method::text = ANY (ARRAY['CASH'::character varying, 'CREDIT_CARD'::character varying, 'DEBIT_CARD'::character varying, 'BANK_TRANSFER'::character varying, 'E_WALLET'::character varying]::text[])),
    CONSTRAINT m_transaction_payment_status_check CHECK (payment_status::text = ANY (ARRAY['PAID'::character varying, 'NOT_PAID'::character varying, 'CANCELLED'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.m_transaction
    OWNER to postgres;

-- Table: public.m_transaction_detail

-- DROP TABLE IF EXISTS public.m_transaction_detail;

CREATE TABLE IF NOT EXISTS public.m_transaction_detail
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    net_price double precision NOT NULL,
    quantity integer NOT NULL,
    tax_amount double precision NOT NULL,
    total_price double precision NOT NULL,
    product_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    transaction_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT m_transaction_detail_pkey PRIMARY KEY (id),
    CONSTRAINT fkl3a9srdso1u9nygahrli7nw9e FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkstbirw3fmlkv3okgbva58l9nr FOREIGN KEY (transaction_id)
        REFERENCES public.m_transaction (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.m_transaction_detail
    OWNER to postgres;

-- Table: public.m_user

-- DROP TABLE IF EXISTS public.m_user;

CREATE TABLE IF NOT EXISTS public.m_user
(
    user_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT m_user_pkey PRIMARY KEY (user_id),
    CONSTRAINT ukrycw44p7cruupkosx3ibmj9q3 UNIQUE (email)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.m_user
    OWNER to postgres;

-- Table: public.product_tax

-- DROP TABLE IF EXISTS public.product_tax;

CREATE TABLE IF NOT EXISTS public.product_tax
(
    product_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    tax_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT product_tax_pkey PRIMARY KEY (product_id, tax_id),
    CONSTRAINT fkca2mf8vlcgf46xqt3rkwfwwwa FOREIGN KEY (tax_id)
        REFERENCES public.taxes (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkr2fk4exyimw2rtf5hg8vwqwcq FOREIGN KEY (product_id)
        REFERENCES public.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product_tax
    OWNER to postgres;

-- Table: public.products

-- DROP TABLE IF EXISTS public.products;

CREATE TABLE IF NOT EXISTS public.products
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    price double precision NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.products
    OWNER to postgres;

-- Table: public.taxes

-- DROP TABLE IF EXISTS public.taxes;

CREATE TABLE IF NOT EXISTS public.taxes
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    rate integer NOT NULL,
    CONSTRAINT taxes_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.taxes
    OWNER to postgres;

-- Table: public.user_roles

-- DROP TABLE IF EXISTS public.user_roles;

CREATE TABLE IF NOT EXISTS public.user_roles
(
    user_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk9fmdc3m9k2ecuhn059uxs9ua5 FOREIGN KEY (role_id)
        REFERENCES public.m_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fksf1r6l1ai08qifs6pc092mka2 FOREIGN KEY (user_id)
        REFERENCES public.m_user (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_roles
    OWNER to postgres;


--insert data

-- Insert data ke tabel m_course
INSERT INTO public.m_course (id, birth_date, birth_place, created_at, created_by, name, user_id)
VALUES 
('1', '1990-05-15', 'Jakarta', CURRENT_TIMESTAMP, 'admin', 'John Doe', 'johndoe123'),
('2', '1985-08-25', 'Bandung', CURRENT_TIMESTAMP, 'admin', 'Jane Smith', 'janesmith123');

-- Insert data ke tabel m_role
INSERT INTO public.m_role (id, name)
VALUES 
('1', 'ROLE_CUSTOMER'),
('2', 'ROLE_STAFF'),
('3', 'ROLE_ADMIN');

-- Insert data ke tabel m_transaction
INSERT INTO public.m_transaction (id, created_by, net_amount_paid, payment_method, payment_status, total_amount_paid, total_tax_paid, transaction_time, customer_id)
VALUES 
('txn1', 'admin', 200000.00, 'CASH', 'PAID', 220000.00, 20000.00, CURRENT_TIMESTAMP, '1'),
('txn2', 'admin', 350000.00, 'CREDIT_CARD', 'PAID', 385000.00, 35000.00, CURRENT_TIMESTAMP, '2');

-- Insert data ke tabel m_transaction_detail
INSERT INTO public.m_transaction_detail (id, net_price, quantity, tax_amount, total_price, product_id, transaction_id)
VALUES 
('td1', 100000.00, 2, 20000.00, 220000.00, 'prod1', 'txn1'),
('td2', 175000.00, 2, 35000.00, 385000.00, 'prod2', 'txn2');

-- Insert data ke tabel m_user
INSERT INTO public.m_user (user_id, email, password, name)
VALUES 
('johndoe123', 'johndoe@example.com', 'password123', 'John Doe'),
('janesmith123', 'janesmith@example.com', 'password456', 'Jane Smith');

-- Insert data ke tabel product_tax
INSERT INTO public.product_tax (product_id, tax_id)
VALUES 
('prod1', 'tax1'),
('prod2', 'tax2');

-- Insert data ke tabel products
INSERT INTO public.products (id, name, price)
VALUES 
('prod1', 'Laptop HP Pavilion', 1000000.00),
('prod2', 'Smartphone Samsung Galaxy', 1750000.00);

-- Insert data ke tabel taxes
INSERT INTO public.taxes (id, name, rate)
VALUES 
('tax1', 'PPN 10%', 10),
('tax2', 'SERVICE 10%', 10);

-- Insert data ke tabel user_roles
INSERT INTO public.user_roles (user_id, role_id)
VALUES 
('johndoe123', '1'),
('janesmith123', '2');
