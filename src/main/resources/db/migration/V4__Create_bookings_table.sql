CREATE TABLE bookings
(
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR(100) NOT NULL UNIQUE,
    name        VARCHAR(255) NOT NULL,
    artist      VARCHAR(255) NOT NULL,
    venue       VARCHAR(255) NOT NULL,
    datetime    VARCHAR(255) NOT NULL,
    ordertime   VARCHAR(255) NOT NULL,
    category    VARCHAR(50)  NOT NULL,
    base_price  BIGINT       NOT NULL,
    amount      INT          NOT NULL,
    price       BIGINT       NOT NULL,
    total_price BIGINT       NOT NULL,
    status      VARCHAR(50)  NOT NULL,

    user_id     BIGINT       NOT NULL,
    concert_id  BIGINT       NOT NULL,

    CONSTRAINT fk_booking_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_concert FOREIGN KEY (concert_id)
        REFERENCES concerts (id) ON DELETE CASCADE
);

CREATE INDEX idx_booking_user_id ON bookings (user_id);
CREATE INDEX idx_booking_concert_id ON bookings (concert_id);
CREATE INDEX idx_booking_code ON bookings (code);
