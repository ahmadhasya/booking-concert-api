CREATE TABLE concerts
(
    id                         BIGSERIAL PRIMARY KEY,
    name                       VARCHAR(255) NOT NULL,
    artist                     VARCHAR(255) NOT NULL,
    venue                      VARCHAR(255) NOT NULL,
    datetime                   VARCHAR(255) NOT NULL,
    status                     VARCHAR(50)  NOT NULL,

    vip_price                  BIGINT,
    vip_capacity               INT,
    vip_sold                   INT          NOT NULL DEFAULT 0,

    standard_price             BIGINT,
    standard_capacity          INT,
    standard_sold              INT          NOT NULL DEFAULT 0,

    general_admission_price    BIGINT,
    general_admission_capacity INT,
    general_admission_sold     INT          NOT NULL DEFAULT 0
);
