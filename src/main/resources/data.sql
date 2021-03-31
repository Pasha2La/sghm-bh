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

-- создаем роль admin с паролем admin
CREATE ROLE admin WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    NOREPLICATION
    ENCRYPTED PASSWORD 'md5f6fdffe48c908deb0f4c3bd36c032e72';

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

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
) TABLESPACE pg_default;

CREATE TABLE public."VIRTUAL_TABLES"
(
    "ID" uuid NOT NULL DEFAULT uuid_generate_v1(),
    "NAME" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "CREATION_DATE" timestamp with time zone,
    "MODIFIED_DATE" timestamp with time zone,
    "ORGANIZATION" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "VIRTUAL_TABLES_PK" PRIMARY KEY ("ID"),
    CONSTRAINT "VIRTUAL_TABLES_NAME" UNIQUE ("NAME")
) TABLESPACE pg_default;

CREATE TABLE public."STRUCTURE_ELEMENTS"
(
    "ID" uuid NOT NULL DEFAULT uuid_generate_v1(),
    "NAME" character varying(50) COLLATE pg_catalog."default" NOT NULL,
    "CREATION_DATE" timestamp with time zone,
    "MODIFIED_DATE" timestamp with time zone,
    "VIRTUAL_TABLE_ID" uuid NOT NULL,
    "IS_ROOT" character varying(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'N',
    CONSTRAINT "STRUCTURE_ELEMENTS_PK" PRIMARY KEY ("ID"),
    CONSTRAINT "STRUCTURE_ELEMENTS_ID" UNIQUE ("ID")
) TABLESPACE pg_default;

CREATE TABLE public."STRUCTURE_ELEMENT_LINKS"
(
    "ID" uuid NOT NULL DEFAULT uuid_generate_v1(),
    "PARENT_ID" uuid NOT NULL,
    "CHILD_ID" uuid NOT NULL,
    CONSTRAINT "STRUCTURE_ELEMENT_LINKS_pkey" PRIMARY KEY ("ID")
) TABLESPACE pg_default;

ALTER TABLE public."USERS"
    OWNER to admin;
ALTER TABLE public."VIRTUAL_TABLES"
    OWNER to admin;
ALTER TABLE public."STRUCTURE_ELEMENTS"
    OWNER to admin;
ALTER TABLE public."STRUCTURE_ELEMENT_LINKS"
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

INSERT INTO public."VIRTUAL_TABLES"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "ORGANIZATION")
VALUES ('4e938f2a-9145-11eb-b903-97e42ad2b3c4', 'testTableName', current_timestamp, current_timestamp, 'testOrganization');

INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('4e204e42-033b-4a91-84dc-3d540dc75f3c', 'element1', current_timestamp, current_timestamp, '4e938f2a-9145-11eb-b903-97e42ad2b3c4', 'Y');
INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('fd0f1829-1d37-4fdf-b05a-3dca259c45f5', 'element2', current_timestamp, current_timestamp, '4e938f2a-9145-11eb-b903-97e42ad2b3c4', 'Y');
INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('9db1bffb-2482-4e0b-a708-4b77ac625772', 'element1.1', current_timestamp, current_timestamp, '4e938f2a-9145-11eb-b903-97e42ad2b3c4', 'N');
INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('622809ac-ff9c-45ea-8e2c-aa0e014bead6', 'element1.2', current_timestamp, current_timestamp, '4e938f2a-9145-11eb-b903-97e42ad2b3c4', 'N');
INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('5fcc1dbd-4de8-4c67-999e-992cca66cd90', 'element1.1.1', current_timestamp, current_timestamp, '4e938f2a-9145-11eb-b903-97e42ad2b3c4', 'N');


INSERT INTO public."STRUCTURE_ELEMENT_LINKS"(
    "ID", "PARENT_ID", "CHILD_ID")
VALUES ('2ed17e61-877e-4802-9504-4f75d9330c67', '4e204e42-033b-4a91-84dc-3d540dc75f3c', '9db1bffb-2482-4e0b-a708-4b77ac625772');
INSERT INTO public."STRUCTURE_ELEMENT_LINKS"(
    "ID", "PARENT_ID", "CHILD_ID")
VALUES ('75ee965e-e852-4321-9e66-acd494aae3fb', '4e204e42-033b-4a91-84dc-3d540dc75f3c', '622809ac-ff9c-45ea-8e2c-aa0e014bead6');
INSERT INTO public."STRUCTURE_ELEMENT_LINKS"(
    "ID", "PARENT_ID", "CHILD_ID")
VALUES ('68cda95f-776a-4b86-b53e-ed7c38b72618', '9db1bffb-2482-4e0b-a708-4b77ac625772', '5fcc1dbd-4de8-4c67-999e-992cca66cd90');


-- шаблон виртуального стола
INSERT INTO public."VIRTUAL_TABLES"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "ORGANIZATION")
VALUES ('0af7d6dc-e657-475f-aecd-9047e358bb40', 'template', current_timestamp, current_timestamp, 'all')


INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('09cc737c-9f37-4c90-b932-6075d87f7af9', 'element1', current_timestamp, current_timestamp, '0af7d6dc-e657-475f-aecd-9047e358bb40', 'Y');

INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('7a064c8d-ed32-48c3-a59f-9a6f07b28c37', 'element2', current_timestamp, current_timestamp, '0af7d6dc-e657-475f-aecd-9047e358bb40', 'Y');

INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('ded08507-b249-42b8-9632-e4bfc1b17a94', 'element3', current_timestamp, current_timestamp, '0af7d6dc-e657-475f-aecd-9047e358bb40', 'Y');

INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('015a585d-12a0-4187-80e9-cfc98395000c', 'element4', current_timestamp, current_timestamp, '0af7d6dc-e657-475f-aecd-9047e358bb40', 'Y');

INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('6d69a277-bbaa-4048-87b6-c8caa79f5d31', 'element5', current_timestamp, current_timestamp, '0af7d6dc-e657-475f-aecd-9047e358bb40', 'Y');

INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('368eceb1-efcd-45c3-97e0-aabd332ab371', 'element1.1', current_timestamp, current_timestamp, '0af7d6dc-e657-475f-aecd-9047e358bb40', 'N');

INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('1f6dcb6d-88b1-4e70-904e-0bb94edd6d40', 'element1.2', current_timestamp, current_timestamp, '0af7d6dc-e657-475f-aecd-9047e358bb40', 'N');

INSERT INTO public."STRUCTURE_ELEMENTS"(
    "ID", "NAME", "CREATION_DATE", "MODIFIED_DATE", "VIRTUAL_TABLE_ID", "IS_ROOT")
VALUES ('185c0528-2fc1-4892-a8dd-76ec6f53c13e', 'element1.1.1', current_timestamp, current_timestamp, '0af7d6dc-e657-475f-aecd-9047e358bb40', 'N');

-- cвязи в шаблоне виртуального стола

INSERT INTO public."STRUCTURE_ELEMENT_LINKS"(
    "ID", "PARENT_ID", "CHILD_ID")
VALUES ('9c9b7fa0-b0aa-4758-b690-bcab1bb9ce0f', '09cc737c-9f37-4c90-b932-6075d87f7af9', '368eceb1-efcd-45c3-97e0-aabd332ab371');

INSERT INTO public."STRUCTURE_ELEMENT_LINKS"(
    "ID", "PARENT_ID", "CHILD_ID")
VALUES ('ae9af710-db49-44ec-b124-33dc749fc31f', '09cc737c-9f37-4c90-b932-6075d87f7af9', '1f6dcb6d-88b1-4e70-904e-0bb94edd6d40');

INSERT INTO public."STRUCTURE_ELEMENT_LINKS"(
    "ID", "PARENT_ID", "CHILD_ID")
VALUES ('3512aee1-de1f-441d-b8f9-08a845eb557f', '368eceb1-efcd-45c3-97e0-aabd332ab371', '185c0528-2fc1-4892-a8dd-76ec6f53c13e');


