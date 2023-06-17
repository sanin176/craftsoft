create table wallet
(
    id                 bigserial    NOT NULL,
    user_id            bigint       NOT NULL,
    first_name         varchar(100) NOT NULL,
    last_name          varchar(100) NOT NULL,
    email              varchar(100) NOT NULL,
    phone_number       varchar(30)  NOT NULL,
    date_of_birth      timestamp    NOT NULL,
    address            varchar(100) NOT NULL,
    currency           varchar(10)  NOT NULL default 'EUR',
    balance            numeric      NOT NULL default 0.0,
    max_limit          numeric      NOT NULL default 0.0,
    is_block           boolean      NOT NULL default false,
    is_suspicious      boolean      NOT NULL default false,

    created_date       timestamp    NOT NULL default NOW(),
    created_by         varchar(200) NOT NULL default 'System', -- we can add this feature in future
    last_modified_date timestamp    NOT NULL default NOW(),    -- we can change it in future
    last_modified_by   varchar(200) NOT NULL default 'System', -- we can add this feature in future

    CONSTRAINT pk__wallet_id PRIMARY KEY (id),
    CONSTRAINT unq__wallet UNIQUE (user_id, currency)
);

create table transaction_history
(
    id             bigserial    NOT NULL,
    wallet_id      bigint       NOT NULL,
    user_id        bigint       NOT NULL,
    operation      varchar(200) NOT NULL,
    balance_before numeric,
    balance_after  numeric,
    amount         numeric      NOT NULL,
    is_suspicious  boolean      NOT NULL default false,

    created_date   timestamp    NOT NULL default NOW(),
    created_by     varchar(200) NOT NULL default 'System', -- we can add this feature in future

    CONSTRAINT pk__transaction_id PRIMARY KEY (id),
    CONSTRAINT fk__transaction_history_to_wallet FOREIGN KEY (wallet_id)
        REFERENCES wallet (id) ON DELETE CASCADE ON UPDATE CASCADE
);