--liquibase formatted sql

--changeset ibardych:0000000000042-1

CREATE SEQUENCE invocation_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE TABLE invocation
(
  id           BIGINT PRIMARY KEY DEFAULT nextval('invocation_id_seq'),
  created_at   TIMESTAMP WITH TIME ZONE,
  initiated_at TIMESTAMP WITH TIME ZONE,
  completed_at TIMESTAMP WITH TIME ZONE,
  outcome      VARCHAR(255),
  reason       TEXT
);
