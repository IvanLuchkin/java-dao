CREATE DATABASE "mate-taxi"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE SCHEMA public
    AUTHORIZATION postgres;

GRANT ALL ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO postgres;

-- Table: public.manufacturers

-- DROP TABLE public.manufacturers;

CREATE TABLE public.manufacturers
(
    id bigint NOT NULL DEFAULT nextval('manufacturers_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    country character varying COLLATE pg_catalog."default" NOT NULL,
    deleted boolean NOT NULL DEFAULT false,
    CONSTRAINT manufacturers_pk PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.manufacturers
    OWNER to postgres;
-- Index: manufacturers_id_uindex

-- DROP INDEX public.manufacturers_id_uindex;

CREATE UNIQUE INDEX manufacturers_id_uindex
    ON public.manufacturers USING btree
    (id ASC NULLS LAST)
    TABLESPACE pg_default;
