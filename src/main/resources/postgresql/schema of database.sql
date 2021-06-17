--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3
-- Dumped by pg_dump version 13.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: accountstatus; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.accountstatus AS ENUM (
    'APPROVED',
    'PENDING',
    'REVOKED'
);


ALTER TYPE public.accountstatus OWNER TO postgres;

--
-- Name: stockitemstatus; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.stockitemstatus AS ENUM (
    'ACTIVE',
    'INACTIVE'
);


ALTER TYPE public.stockitemstatus OWNER TO postgres;

--
-- Name: stocklevel; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.stocklevel AS ENUM (
    'LOW',
    'NORMAL'
);


ALTER TYPE public.stocklevel OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: accountauthentication; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accountauthentication (
    accountid character varying(64) NOT NULL,
    accountusername character varying(64),
    accountpassword character(60) NOT NULL,
    accountstatus public.accountstatus,
    lastlogin timestamp without time zone,
    lastactive timestamp without time zone,
    accountemail character varying(128)
);


ALTER TABLE public.accountauthentication OWNER TO postgres;

--
-- Name: accountrole; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accountrole (
    accountid character varying(64) NOT NULL,
    roleid character varying(64) NOT NULL
);


ALTER TABLE public.accountrole OWNER TO postgres;

--
-- Name: historicalsales2015; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.historicalsales2015 (
    "timestamp" date,
    itemid bigint,
    quantity bigint,
    salesid bigint
);


ALTER TABLE public.historicalsales2015 OWNER TO postgres;

--
-- Name: inventorystock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.inventorystock (
    itemid integer,
    skunumber character varying(64),
    quantitycount integer,
    lastrestockdate timestamp without time zone,
    stocklevel public.stocklevel,
    stockstatus public.stockitemstatus,
    lowindicator integer
);


ALTER TABLE public.inventorystock OWNER TO postgres;

--
-- Name: itemcategory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.itemcategory (
    categoryid integer NOT NULL,
    categoryname character varying(64)
);


ALTER TABLE public.itemcategory OWNER TO postgres;

--
-- Name: itemcategory_categoryid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.itemcategory_categoryid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.itemcategory_categoryid_seq OWNER TO postgres;

--
-- Name: itemcategory_categoryid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.itemcategory_categoryid_seq OWNED BY public.itemcategory.categoryid;


--
-- Name: itemlisting; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.itemlisting (
    itemid integer NOT NULL,
    skunumber character varying(64),
    itemname character varying(255),
    itemvolume character varying(64),
    itemdimension character varying(64),
    itemunitprice double precision,
    itemdescription character varying(255),
    imagepathfull character varying(255),
    itempath character varying(255),
    categoryid integer
);


ALTER TABLE public.itemlisting OWNER TO postgres;

--
-- Name: itemlisting_itemid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.itemlisting_itemid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.itemlisting_itemid_seq OWNER TO postgres;

--
-- Name: itemlisting_itemid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.itemlisting_itemid_seq OWNED BY public.itemlisting.itemid;


--
-- Name: restockhistory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.restockhistory (
    itemid integer,
    quantity integer,
    restocktime timestamp without time zone
);


ALTER TABLE public.restockhistory OWNER TO postgres;

--
-- Name: roledetails; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roledetails (
    roleid character varying(64) NOT NULL,
    rolename character varying(64),
    roledescription character varying(255)
);


ALTER TABLE public.roledetails OWNER TO postgres;

--
-- Name: saleshistory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.saleshistory (
    itemid integer,
    quantity integer,
    "timestamp" timestamp without time zone
);


ALTER TABLE public.saleshistory OWNER TO postgres;

--
-- Name: saleshistory2015; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.saleshistory2015 (
    "timestamp" date,
    itemid integer,
    quantity bigint,
    salesid integer NOT NULL
);


ALTER TABLE public.saleshistory2015 OWNER TO postgres;

--
-- Name: saleshistory2015_salesid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.saleshistory2015_salesid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.saleshistory2015_salesid_seq OWNER TO postgres;

--
-- Name: saleshistory2015_salesid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.saleshistory2015_salesid_seq OWNED BY public.saleshistory2015.salesid;


--
-- Name: year; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.year (
    month date NOT NULL
);


ALTER TABLE public.year OWNER TO postgres;

--
-- Name: itemcategory categoryid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itemcategory ALTER COLUMN categoryid SET DEFAULT nextval('public.itemcategory_categoryid_seq'::regclass);


--
-- Name: itemlisting itemid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itemlisting ALTER COLUMN itemid SET DEFAULT nextval('public.itemlisting_itemid_seq'::regclass);


--
-- Name: saleshistory2015 salesid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.saleshistory2015 ALTER COLUMN salesid SET DEFAULT nextval('public.saleshistory2015_salesid_seq'::regclass);


--
-- Name: itemcategory_categoryid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.itemcategory_categoryid_seq', 1, false);


--
-- Name: itemlisting_itemid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.itemlisting_itemid_seq', 4, true);


--
-- Name: saleshistory2015_salesid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.saleshistory2015_salesid_seq', 37686, true);


--
-- Name: accountauthentication accountauthentication_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accountauthentication
    ADD CONSTRAINT accountauthentication_pk PRIMARY KEY (accountid);


--
-- Name: accountrole accountrole_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accountrole
    ADD CONSTRAINT accountrole_pk PRIMARY KEY (roleid, accountid);


--
-- Name: itemcategory itemcategory_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itemcategory
    ADD CONSTRAINT itemcategory_pk PRIMARY KEY (categoryid);


--
-- Name: itemlisting itemlisting_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itemlisting
    ADD CONSTRAINT itemlisting_pk PRIMARY KEY (itemid);


--
-- Name: roledetails roledetails_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roledetails
    ADD CONSTRAINT roledetails_pk PRIMARY KEY (roleid);


--
-- Name: saleshistory2015 saleshistory2015_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.saleshistory2015
    ADD CONSTRAINT saleshistory2015_pk PRIMARY KEY (salesid);


--
-- Name: year year_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.year
    ADD CONSTRAINT year_pk PRIMARY KEY (month);


--
-- Name: accountauthentication_acccountid_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX accountauthentication_acccountid_uindex ON public.accountauthentication USING btree (accountid);


--
-- Name: accountauthentication_accountemail_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX accountauthentication_accountemail_uindex ON public.accountauthentication USING btree (accountemail);


--
-- Name: accountauthentication_accountusername_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX accountauthentication_accountusername_uindex ON public.accountauthentication USING btree (accountusername);


--
-- Name: accountrole__accID_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "accountrole__accID_index" ON public.accountrole USING btree (accountid);


--
-- Name: inventorystock_sku_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX inventorystock_sku_index ON public.inventorystock USING btree (skunumber);


--
-- Name: itemlisting__index_SKU; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX "itemlisting__index_SKU" ON public.itemlisting USING btree (skunumber);


--
-- Name: roledetails_roleid_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX roledetails_roleid_uindex ON public.roledetails USING btree (roleid);


--
-- Name: saleshistory2015_salesid_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX saleshistory2015_salesid_uindex ON public.saleshistory2015 USING btree (salesid);


--
-- Name: accountrole accountrole_accountauthentication_acccountid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accountrole
    ADD CONSTRAINT accountrole_accountauthentication_acccountid_fk FOREIGN KEY (accountid) REFERENCES public.accountauthentication(accountid) ON UPDATE CASCADE;


--
-- Name: accountrole accountrole_roledetails_roleid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accountrole
    ADD CONSTRAINT accountrole_roledetails_roleid_fk FOREIGN KEY (roleid) REFERENCES public.roledetails(roleid) ON UPDATE CASCADE;


--
-- Name: inventorystock inventorystock_itemlisting_itemid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventorystock
    ADD CONSTRAINT inventorystock_itemlisting_itemid_fk FOREIGN KEY (itemid) REFERENCES public.itemlisting(itemid) ON UPDATE CASCADE;


--
-- Name: itemlisting itemlisting_itemcategory_categoryid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itemlisting
    ADD CONSTRAINT itemlisting_itemcategory_categoryid_fk FOREIGN KEY (categoryid) REFERENCES public.itemcategory(categoryid) ON UPDATE CASCADE;


--
-- Name: restockhistory restockhistory_itemlisting_itemid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.restockhistory
    ADD CONSTRAINT restockhistory_itemlisting_itemid_fk FOREIGN KEY (itemid) REFERENCES public.itemlisting(itemid);


--
-- Name: saleshistory saleshistory_itemlisting_itemid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.saleshistory
    ADD CONSTRAINT saleshistory_itemlisting_itemid_fk FOREIGN KEY (itemid) REFERENCES public.itemlisting(itemid);


--
-- PostgreSQL database dump complete
--

