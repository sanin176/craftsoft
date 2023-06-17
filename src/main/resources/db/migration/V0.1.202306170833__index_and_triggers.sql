CREATE INDEX idx_userId ON transaction_history (user_id); -- we need to research it because I think it will be useful

-- trigger for checking suspiciouus more then 5 in a hour and put 'is_suspicious = true'

CREATE OR REPLACE FUNCTION check_suspicious_more_5() RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*) FROM transaction_history
        WHERE user_id = NEW.user_id
          AND created_date >= NOW() - INTERVAL '1 hour') > 4 THEN
            NEW.is_suspicious = TRUE;
    END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER suspicious_trigger
BEFORE UPDATE ON wallet
FOR EACH ROW
EXECUTE FUNCTION check_suspicious_more_5();

-- trigger for checking suspiciouus more then 10 in a hour and user will be blocked -> 'is_bocked = true'

CREATE OR REPLACE FUNCTION check_suspicious_more_10() RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*) FROM transaction_history
        WHERE user_id = NEW.user_id
          AND created_date >= NOW() - INTERVAL '1 hour') > 9 THEN
            NEW.is_block = TRUE;
    END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER block_trigger
    BEFORE UPDATE ON wallet
    FOR EACH ROW
    EXECUTE FUNCTION check_suspicious_more_10();