CREATE TABLE account (
                         id VARCHAR(40) PRIMARY KEY,
                         customer_id VARCHAR(40) NOT NULL REFERENCES customer(id),
                         balance DECIMAL(19, 4) NOT NULL DEFAULT 0.00,
                         status VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE', 'CLOSED')),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);