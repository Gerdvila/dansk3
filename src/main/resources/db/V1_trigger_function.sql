CREATE OR REPLACE FUNCTION update_account_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_account_trigger
    BEFORE UPDATE ON account
    FOR EACH ROW
EXECUTE FUNCTION update_account_timestamp();