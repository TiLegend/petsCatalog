CREATE TABLE public.oauth_client_details
(
    client_id character varying(255),
    resource_ids character varying(255),
    client_secret character varying(255),
    scope character varying(255),
    authorized_grant_types character varying(255),
    web_server_redirect_uri character varying(255),
    authorities character varying(255),
    access_token_validity integer,
    refresh_token_validity integer,
    additional_information character varying(4096),
    autoapprove character varying(255),
    PRIMARY KEY (client_id)
);

create table public.oauth_client_token (
  token_id character varying(255),
  token bytea,
  authentication_id character varying(255),
  user_name character varying(255),
  client_id character varying(255),
  PRIMARY KEY(authentication_id)
);

create table public.oauth_access_token (
  token_id character varying(255),
  token bytea,
  authentication_id character varying(255),
  user_name character varying(255),
  client_id character varying(255),
  authentication bytea,
  refresh_token character varying(255),
  PRIMARY KEY(authentication_id)
);

create table public.oauth_refresh_token (
  token_id character varying(255),
  token bytea,
  authentication bytea
);

create table public.oauth_code (
  code character varying(255),
  authentication bytea
);

create table public.oauth_approvals (
    userId character varying(255),
    clientId character varying(255),
    scope character varying(255),
    status character varying(10),
    expiresAt TIMESTAMP,
    lastModifiedAt TIMESTAMP
);

create table public.ClientDetails (
  appId character varying(255),
  resourceIds character varying(255),
  appSecret character varying(255),
  scope character varying(255),
  grantTypes character varying(255),
  redirectUrl character varying(255),
  authorities character varying(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation character varying(4096),
  autoApproveScopes character varying(255),
  PRIMARY KEY(appId)
);

---------------------------------------
CREATE TABLE public.users
(
    id serial,
    username character varying(30) NOT NULL,
    password  TEXT NOT NULL,
    first_wrong_login_attempt_start TIMESTAMP,
    login_attempt_count smallint,
    PRIMARY KEY (id),
    CONSTRAINT username_unq UNIQUE (username)
)
WITH (
    OIDS = FALSE
);
----------------------------------------
CREATE TABLE public.pet_types
(
    id serial,
    pet_type_name character varying(20) NOT NULL,
    PRIMARY KEY (id)
);
-------------------------------------------
CREATE TABLE public."pets"
(
    id bigserial,
    sex integer,
    pet_type_id integer,
    owner_id integer,
    nickname character varying NOT NULL,
    birth_day date,
    PRIMARY KEY (id),
    CONSTRAINT pet_type_fk FOREIGN KEY (pet_type_id)
        REFERENCES public.pet_types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT owner_fk FOREIGN KEY (owner_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
         CONSTRAINT nickname_unq UNIQUE (nickname)
);