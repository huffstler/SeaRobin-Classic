-- CREATE DATABASE searobin;
-- \c searobin
-- SET search_path TO searobin,public;
-- can update schema later on to have org level rules as well as tournament level rules
drop table if exists anglers cascade;
drop table if exists catches cascade;
drop table if exists fish cascade;
drop table if exists membership cascade;
drop table if exists organization cascade;
drop table if exists tiers cascade;
drop table if exists tournaments cascade;

create table organization(
	id uuid primary key default gen_random_uuid(),
	org_name text unique not null, -- add index on this field
	created_on timestamp,
	modified_on timestamp
);
CREATE INDEX organization_name_idx on organization (org_name);

create table searobin.tournaments (
	id uuid primary key default gen_random_uuid(),
	organization_id uuid references organization (id),
	tournament_name text not null, -- add index on this field
	start_time timestamp,
	end_time timestamp,
	created_on timestamp,
	modified_on timestamp

	-- I'm thinking I want some sort of "global" score count here, ;
	-- but not sure what data type to have that as yet. Maybe json blob that's
	-- calculated once, and stored here after for faster results after tourney ends.
);
CREATE INDEX tournament_name_idx on tournaments (tournament_name);

create table anglers (
	id uuid primary key default gen_random_uuid(),
	username text unique, -- add index on this field
	-- not yet. Harden the system first. firstname text,
	-- not yet. Harden the system first. lastname text,
	-- not yet. Harden the system first. phonenumber text,
	-- not yet. Harden the system first. emailaddress text,
	created_on timestamp,
	modified_on timestamp
);
CREATE INDEX angler_name_idx on anglers (username);

-- used for mapping anglers to tournaments and vice versa
create table membership (
	id uuid primary key default gen_random_uuid(),
	tournament_id uuid references tournaments (id),
	organization_id uuid references organization (id),
	angler_id uuid references anglers (id),
	is_org_admin boolean default 'false',
	is_tourney_admin boolean default 'false',
	created_on timestamp default now(),
	modified_on timestamp default now()
);

create sequence tiers_id_seq;

create table tiers(
    id integer NOT NULL DEFAULT nextval('tiers_id_seq'::regclass),
    name text COLLATE pg_catalog."default",
    CONSTRAINT tiers_pkey PRIMARY KEY (id),
    CONSTRAINT tiers_name_check CHECK (name <> ''::text)
);

create table fish (
    name text COLLATE pg_catalog."default",
    tier_id integer,
    id integer NOT NULL DEFAULT nextval('tiers_id_seq'::regclass),
    CONSTRAINT fish_pkey PRIMARY KEY (id),
    CONSTRAINT fish_tier_id_fk FOREIGN KEY (tier_id)
        REFERENCES tiers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

create table catches (
    -- I don't think we need a new uuid for each catch. could probably make an index based on 3 or 4 other columns?
	id uuid primary key default gen_random_uuid(),

	membership_id uuid references membership (id),

	fish_id int references fish (id),
	fish_length real,
	used_lure boolean default 'false',
	catch_time timestamp default now()
);