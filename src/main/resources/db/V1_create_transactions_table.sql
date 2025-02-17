CREATE TABLE transaction (
                             id VARCHAR(40) PRIMARY KEY,
                             account_id VARCHAR(40) NOT NULL REFERENCES account(id) ON DELETE CASCADE,
                             transaction_type VARCHAR(20) NOT NULL CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAWAL')),
                             amount DECIMAL(19, 4) NOT NULL CHECK (amount > 0),
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);