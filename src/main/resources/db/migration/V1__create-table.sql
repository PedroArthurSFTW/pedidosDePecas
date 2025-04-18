CREATE TABLE PECAS(
    ID INTEGER PRIMARY KEY,
    NOME_PECA TEXT NOT NULL,
    COD_PECA_DATASUL INT NOT NULL,
    PART_NUMBER TEXT NOT NULL,
    FABRICANTE TEXT,
    ACTIVE BOOLEAN
);

CREATE TABLE USUARIOS(
    ID INTEGER PRIMARY KEY,
    USERNAME TEXT NOT NULL UNIQUE,
    PASSWORD TEXT NOT NULL,
    ROLE TEXT NOT NULL,
    non_locked_name TEXT,
    non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE PEDIDOS(
    ID INTEGER PRIMARY KEY,
    PECA_ID INTEGER NOT NULL,
    USUARIOS_ID_ABERTURA INTEGER NOT NULL,
    USUARIOS_ID_FECHAMENTO INTEGER,
    DATA_PEDIDOS TIMESTAMP,
    DATA_PEDIDOS_FECHAMENTO TIMESTAMP,
    STATUS TEXT NOT NULL,
    ORDEM_SERVICO INTEGER,
    FOREIGN KEY (PECA_ID) REFERENCES PECAS (ID),
    FOREIGN KEY (USUARIOS_ID_ABERTURA) REFERENCES USUARIOS (ID),
    FOREIGN KEY (USUARIOS_ID_FECHAMENTO) REFERENCES USUARIOS (ID)
);

CREATE TABLE PAGE(
    ID int primary key,
    NOME TEXT NOT NULL,
    ATT_PAGE NUMERIC
);


CREATE SEQUENCE pecas_seq START 1;

CREATE SEQUENCE usuarios_seq START 1;

CREATE SEQUENCE pedidos_seq START 1;

CREATE SEQUENCE page_seq START 1;

insert into usuarios (id, username, password, role, non_locked_name)
values (1, 'admin', '$2a$10$LgYjtxpY5WrVVpNHljW2e.Siw084AZqsBNn4dJdNOSSkPoOhSz2wW', 'ADMIN', 'ATIVO');
SELECT setval('usuarios_seq', (SELECT MAX(id) FROM usuarios));
insert into PAGE (id, nome, att_page) VALUES (1, 'Pecas Pedentes', 4);
insert into PAGE (id, nome, att_page) VALUES (2, 'Faturamentos Pedentes', 4);