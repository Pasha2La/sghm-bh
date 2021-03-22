DROP DATABASE IF EXISTS "sghm-db";
DROP ROLE IF EXISTS admin;
DROP TABLE IF EXISTS public."USERS";


CREATE DATABASE "sghm-db"
WITH
    OWNER = admin
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE ROLE admin WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    NOREPLICATION
    ENCRYPTED PASSWORD 'md5f6fdffe48c908deb0f4c3bd36c032e72';

CREATE TABLE public."USERS"
(
    "USERNAME" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "PASSWORD" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "ID" uuid NOT NULL DEFAULT uuid_generate_v1(),
    "EMAIL" character varying(50) COLLATE pg_catalog."default",
    "FIRST_NAME" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "LAST_NAME" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "PATRONYMIC" character varying(50) COLLATE pg_catalog."default",
    "ROLE" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "USERS_pkey" PRIMARY KEY ("ID"),
    CONSTRAINT "USERS_ID" UNIQUE ("ID"),
    CONSTRAINT "USERS_USERNAME" UNIQUE ("USERNAME")
)

    TABLESPACE pg_default;

ALTER TABLE public."USERS"
    OWNER to admin;

INSERT INTO public."USERS"(
    "USERNAME", "PASSWORD", "EMAIL", "FIRST_NAME", "LAST_NAME", "PATRONYMIC", "ROLE")
VALUES ('testUsername3', '12345', 'test@test.test', 'fNameTest3', 'lNameTest3', 'patrTest3', 'USER');

INSERT INTO public."USERS"(
    "USERNAME", "PASSWORD", "EMAIL", "FIRST_NAME", "LAST_NAME", "PATRONYMIC", "ROLE")
VALUES ('testUsername1', '12345', 'test@test.test', 'fNameTest1', 'lNameTest1', 'patrTest1', 'ADMIN');

INSERT INTO public."USERS"(
    "USERNAME", "PASSWORD", "EMAIL", "FIRST_NAME", "LAST_NAME", "PATRONYMIC", "ROLE")
VALUES ('testUsername2', '12345', 'test@test.test', 'fNameTest2', 'lNameTest2', 'patrTest2', 'OTTB_USER');