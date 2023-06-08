DELIMITER //
CREATE procedure bank_rename (IN old_name CHAR(30), IN new_name CHAR(30), OUT status INT)
BEGIN
	SET status = 1;
	IF EXISTS (SELECT * FROM sub_bank WHERE sub_bank.bank_name = new_name) AND old_name != new_name THEN
		SET status = 2;
	END IF;
    IF status = 1 THEN
		START TRANSACTION;
		SET FOREIGN_KEY_CHECKS = 0;
        UPDATE department SET department.bank_name = new_name WHERE department.bank_name = old_name;
        UPDATE loan SET loan.bank_name = new_name WHERE loan.bank_name = old_name;
        UPDATE member SET member.bank_name = new_name WHERE member.bank_name = old_name;
        UPDATE credit_account SET credit_account.bank_name = new_name WHERE credit_account.bank_name = old_name;
        UPDATE saving_account SET saving_account.bank_name = new_name WHERE saving_account.bank_name = old_name;
        UPDATE sub_bank SET sub_bank.bank_name = new_name WHERE sub_bank.bank_name = old_name;
        SET FOREIGN_KEY_CHECKS = 1;
        COMMIT;
    END IF;
END //
DELIMITER ;

DELIMITER //
CREATE procedure edit_member (IN id INT, IN depart_no_new INT, IN dep_depart_no_new INT, IN bank_name_new CHAR(30), IN name_new CHAR(30), IN phone_new CHAR(11), IN address_new CHAR(30), IN salary_new INT, IN level_new INT, OUT status INT)
BEGIN
	DECLARE money INT DEFAULT salary_new;
    DECLARE depart_no INT;
    DECLARE dep_depart_no INT;
    DECLARE bank_name CHAR(30);
    DECLARE salary INT;
    DECLARE level INT;
	SET status = 1;
	IF NOT EXISTS (SELECT * FROM sub_bank WHERE sub_bank.bank_name = bank_name_new)  THEN
		SET status = 2;
	END IF;
	IF status = 1 AND depart_no_new != dep_depart_no_new AND NOT depart_no_new IS NULL THEN
		SET status = 3;
	END IF;
    IF status = 1 AND NOT EXISTS (SELECT * FROM department WHERE department.depart_no = dep_depart_no_new)  THEN
		SET status = 4;
	END IF;
	IF status = 1 AND NOT EXISTS (SELECT * FROM department WHERE department.depart_no = dep_depart_no_new AND department.bank_name = bank_name_new) THEN
		SET status = 5;
	END IF;
    IF status = 1 THEN
		START TRANSACTION;
		SET FOREIGN_KEY_CHECKS = 0;
        SELECT member.depart_no, member.dep_depart_no, member.bank_name, member.salary, member.level FROM member WHERE member.id = id INTO depart_no, dep_depart_no, bank_name, salary, level;
        IF level != level_new AND salary = salary_new THEN
			SET money = salary + 3000*(level_new-level);
        END IF;
        UPDATE department SET department.id = NULL WHERE department.depart_no = depart_no;
        UPDATE member, department SET member.depart_no = NULL WHERE member.id = department.id AND department.depart_no = depart_no_new;
		UPDATE department SET department.id = id WHERE department.depart_no = depart_no_new;
        UPDATE member SET member.depart_no = depart_no_new, member.dep_depart_no = dep_depart_no_new, member.bank_name = bank_name_new, member.name = name_new, member.phone = phone_new, member.address = address_new, member.salary = money, member.level = level_new WHERE member.id = id;
		SET FOREIGN_KEY_CHECKS = 1;
        COMMIT;
    END IF;
END //
DELIMITER ;

DELIMITER //
CREATE Function test_id(id CHAR(18), sex CHAR(1))
RETURNS INT
READS SQL DATA
BEGIN
	DECLARE curr_num INT DEFAULT 0;
    DECLARE total_num INT DEFAULT 0;
    DECLARE flag CHAR(1) DEFAULT '0';
    DECLARE born_year INT DEFAULT 0;
    DECLARE born_month INT DEFAULT 0;
    DECLARE born_day INT DEFAULT 0;
    DECLARE sex_no INT DEFAULT 0;
    DECLARE j INT DEFAULT 1;
    DECLARE status INT DEFAULT 0;
    SELECT Cast(SUBSTRING(id, 7, 4) AS SIGNED) INTO born_year;
	SELECT Cast(SUBSTRING(id, 11, 2) AS SIGNED) INTO born_month;
    SELECT Cast(SUBSTRING(id, 13, 2) AS SIGNED) INTO born_day;
    SELECT Cast(SUBSTRING(id, 17, 1) AS SIGNED) INTO sex_no;
    IF born_month > 12 THEN
		SET status = -1;
        return status;
	END IF;
    CASE
		WHEN born_month = 1 AND born_day > 31 THEN 
			SET status = -1;
            return status;
        WHEN born_month = 3 AND born_day > 31 THEN 
			SET status = -1;
            return status;
		WHEN born_month = 5 AND born_day > 31 THEN 
			SET status = -1;
            return status;
		WHEN born_month = 7 AND born_day > 31 THEN 
			SET status = -1;
            return status;
		WHEN born_month = 8 AND born_day > 31 THEN 
			SET status = -1;
            return status;
		WHEN born_month = 10 AND born_day > 31 THEN 
			SET status = -1;
            return status;
		WHEN born_month = 12 AND born_day > 31 THEN 
			SET status = -1;
            return status;
		WHEN born_month = 4 AND born_day > 30 THEN 
			SET status = -1;
            return status;
		WHEN born_month = 6 AND born_day > 30 THEN 
			SET status = -1;
            return status;
		WHEN born_month = 9 AND born_day > 30 THEN 
			SET status = -1;
            return status;
		WHEN born_month = 11 AND born_day > 30 THEN 
			SET status = -1;
            return status;
		WHEN born_month = 2 AND born_day > 29 AND born_year % 4 = 0 THEN 
			SET status = -1;
            return status;
		WHEN born_month = 2 AND born_day > 28 AND born_year % 4 != 0 THEN 
			SET status = -1;
            return status;
		ELSE
			SET status = 1;
    END CASE;
    IF sex != 'X' THEN
		IF sex_no%2 = 1 AND sex != 'M' Then
			SET status = -1;
			return status;
		END IF;
		IF sex_no%2 = 0 AND sex != 'W' Then
			SET status = -1;
			return status;
		END IF;
	END IF;
    SET total_num = 0;
    WHILE j <= 17 DO
		SELECT Cast(SUBSTRING(id, j, 1) AS SIGNED) INTO curr_num;
		CASE
			WHEN j = 1 THEN		SET total_num = total_num+curr_num*7;
            WHEN j = 2 THEN		SET total_num = total_num+curr_num*9;
            WHEN j = 3 THEN		SET total_num = total_num+curr_num*10;
            WHEN j = 4 THEN		SET total_num = total_num+curr_num*5;
            WHEN j = 5 THEN		SET total_num = total_num+curr_num*8;
            WHEN j = 6 THEN		SET total_num = total_num+curr_num*4;
            WHEN j = 7 THEN		SET total_num = total_num+curr_num*2;
            WHEN j = 8 THEN		SET total_num = total_num+curr_num*1;
            WHEN j = 9 THEN		SET total_num = total_num+curr_num*6;
            WHEN j = 10 THEN	SET total_num = total_num+curr_num*3;
            WHEN j = 11 THEN	SET total_num = total_num+curr_num*7;
            WHEN j = 12 THEN	SET total_num = total_num+curr_num*9;
            WHEN j = 13 THEN	SET total_num = total_num+curr_num*10;
            WHEN j = 14 THEN	SET total_num = total_num+curr_num*5;
            WHEN j = 15 THEN	SET total_num = total_num+curr_num*8;
            WHEN j = 16 THEN	SET total_num = total_num+curr_num*4;
            ELSE				SET total_num = total_num+curr_num*2;
        END CASE;
		SET j = j+1;
	END WHILE;
    SET total_num = total_num % 11;
--     return total_num;
    CASE
		WHEN total_num = 0 THEN		SET flag = '1';
        WHEN total_num = 1 THEN		SET flag = '0';
        WHEN total_num = 2 THEN		SET flag = 'x';
        WHEN total_num = 3 THEN		SET flag = '9';
        WHEN total_num = 4 THEN		SET flag = '8';
        WHEN total_num = 5 THEN		SET flag = '7';
        WHEN total_num = 6 THEN		SET flag = '6';
        WHEN total_num = 7 THEN		SET flag = '5';
        WHEN total_num = 8 THEN		SET flag = '4';
        WHEN total_num = 9 THEN		SET flag = '3';
        WHEN total_num = 10 THEN	SET flag = '2';
    END CASE;
    IF flag != SUBSTRING(id, 18, 1) THEN
		SET status = -1;
        return status;
	END IF;
    return status;
    
END //
DELIMITER ;

DELIMITER //
CREATE procedure employ (IN dep_depart_no Integer, IN bank_name CHAR(30), IN name CHAR(30), IN sex CHAR(1), IN personal_id CHAR(18), IN phone CHAR(11), IN address CHAR(30), OUT status INT)
BEGIN
	DECLARE max_no INT DEFAULT 0;
    DECLARE next_no INT DEFAULT 0;
    DECLARE test_id INT DEFAULT 0;
    DECLARE test_phone INT DEFAULT 0;
    SET test_id = Length(personal_id);
    SET test_phone = Length(phone);
    SET status = 1;
	IF status = 1 AND (test_id <> 18 OR ((SELECT personal_id REGEXP '[^0-9x]') = 1)) THEN
		SET status = 2;
	END IF;
    IF status = 1 AND (sex != 'M' AND sex != 'W') THEN
		SET status = 3;
	END IF;
	IF status = 1 AND ((SELECT test_id(personal_id, sex)) = -1) THEN
		SET status = 2;
	END IF;
    IF status = 1 AND (test_phone <> 11 OR ((SELECT phone REGEXP '[^0-9]') = 1)) THEN
		SET status = 4;
	END IF;
	IF status = 1 AND NOT EXISTS (SELECT * FROM department WHERE department.depart_no = dep_depart_no AND department.bank_name = bank_name) THEN
		SET status = 5;
	END IF;
	IF status = 1 THEN
		IF (SELECT COUNT(*) FROM member) != 0 THEN
			SELECT MAX(id) FROM member INTO max_no;
		END IF;
		SET next_no = max_no+1;
		INSERT INTO `bank`.`member` (`id`, `dep_depart_no`, `bank_name`, `name`, `sex`, `person_id`, `phone`, `address`, `salary`, `begin_date`, `level`) VALUES (next_no, dep_depart_no, bank_name, name, sex, personal_id, phone, address, '5000', (Curdate()), '1');
	END IF;
END //
DELIMITER ;

DELIMITER //
CREATE procedure register (IN client_id CHAR(18), IN real_name CHAR(30), IN client_phone CHAR(11), IN client_address CHAR(30),  IN client_email CHAR(30), OUT status INT)
BEGIN
	DECLARE test_id INT DEFAULT 0;
    DECLARE test_phone INT DEFAULT 0;
    SET test_id = Length(client_id);
    SET test_phone = Length(client_phone);
    SET status = 1;
	IF status = 1 AND (test_id <> 18 OR ((SELECT client_id REGEXP '[^0-9x]') = 1)) THEN
		SET status = 2;
	END IF;
	IF status = 1 AND ((SELECT bank.test_id(client_id, 'X')) = -1) THEN
		SET status = 2;
	END IF;
    IF status = 1 AND (test_phone <> 11 OR ((SELECT client_phone REGEXP '[^0-9]') = 1)) THEN
		SET status = 3;
	END IF;
	IF status = 1 AND EXISTS (SELECT * FROM client WHERE client.client_id = client_id) THEN
		SET status = 4;
	END IF;
    IF status = 1 THEN 
		INSERT INTO `bank`.`client` (`client_id`, `real_name`, `client_phone`, `client_address`, `client_email`) VALUES (client_id, real_name, client_phone, client_address, client_email);
 	END IF;
END //
DELIMITER ;

DELIMITER //
CREATE procedure get_card (IN card_type INT, IN client_id CHAR(18), IN bank_name CHAR(30), IN card_password CHAR(20), IN remain INT, IN overdraft INT, IN interest_rate FLOAT, OUT status INT)
BEGIN
    DECLARE curr_max INT DEFAULT 0;
    DECLARE max_no INT DEFAULT 0;
    DECLARE next_no INT DEFAULT 1;
    DECLARE test_id INT DEFAULT 0;
    SET status = 1;
	SET test_id = Length(client_id);
    IF card_type = 1 AND EXISTS(SELECT * FROM saving_account Where client_id = saving_account.client_id AND bank_name = saving_account.bank_name) THEN
		SET status = 2;
	END IF;
    IF card_type = 2 AND EXISTS(SELECT * FROM credit_account Where client_id = credit_account.client_id AND bank_name = credit_account.bank_name) THEN
		SET status = 2;
	END IF;
    IF status = 1 AND NOT EXISTS(SELECT * FROM client WHERE client.client_id = client_id) THEN
		SET status = 3;
	END IF;
    IF status = 1 AND NOT EXISTS(SELECT * FROM sub_bank WHERE sub_bank.bank_name = bank_name) THEN
		SET status = 4;
	END IF;
    
    IF (SELECT COUNT(*) FROM saving_account) != 0 THEN
		SELECT MAX(account_id) FROM saving_account INTO curr_max;
	END IF;
    SET max_no = curr_max;
    IF (SELECT COUNT(*) FROM credit_account) != 0 THEN
		SELECT MAX(account_id) FROM credit_account INTO curr_max;
	END IF;
    IF curr_max > max_no THEN
		SET max_no = curr_max;
	END IF;
	SET next_no = max_no+1;
    
    IF status = 1 THEN
		START TRANSACTION;
		IF card_type = 1 THEN
			INSERT INTO saving_account (`account_id`, `client_id`, `bank_name`, `password`, `remaining`, `open_date`, `interest_rate`) VALUES (next_no, client_id, bank_name, card_password, remain, (Curdate()), interest_rate);
			UPDATE sub_bank SET asset = asset + remain WHERE sub_bank.bank_name = bank_name;
		END IF;
		IF card_type = 2 THEN
			INSERT INTO credit_account (`account_id`, `client_id`, `bank_name`, `password`, `remaining`, `open_date`, `overdraft`) VALUES (next_no, client_id, bank_name, card_password, '0', (Curdate()), overdraft);
		END IF;
		COMMIT;
	END IF;
END //
DELIMITER ;

DELIMITER //
CREATE procedure delete_card (IN card_type INT, IN card_id INT, OUT status INT)
BEGIN
    DECLARE remaining INT DEFAULT 0;
    SET status = 1;
    IF card_type = 1 THEN
		SELECT saving_account.remaining FROM saving_account WHERE saving_account.account_id = card_id INTO remaining;
        IF remaining != 0 THEN
			SET status = 2;
		ELSE
            DELETE FROM `bank`.`saving_account` WHERE `account_id` = card_id;
		END IF;
	ELSE
		SELECT credit_account.remaining FROM credit_account WHERE credit_account.account_id = card_id INTO remaining;
        IF remaining != 0 THEN
			SET status = 2;
		ELSE
            DELETE FROM `bank`.`credit_account` WHERE `account_id` = card_id;
		END IF;
	END IF;
END //
DELIMITER ;

DELIMITER //
CREATE procedure credit_interact (IN type INT, IN card_id CHAR(18), IN money INT, OUT status INT)
BEGIN
    DECLARE remaining INT DEFAULT 0;
    DECLARE overdraft INT DEFAULT 0;
	DECLARE bank_remaining INT DEFAULT 0;
    SET status = -1;
	IF NOT EXISTS (SELECT * FROM credit_account WHERE credit_account.account_id = card_id) THEN
		SET status = -2;
	END IF;
    IF status = -1 THEN
		IF type = 2 THEN
			SELECT credit_account.remaining FROM credit_account WHERE credit_account.account_id = card_id INTO remaining;
            SELECT credit_account.overdraft FROM credit_account WHERE credit_account.account_id = card_id INTO overdraft;
            SELECT sub_bank.asset FROM sub_bank, credit_account WHERE credit_account.account_id = card_id AND credit_account.bank_name = sub_bank.bank_name INTO bank_remaining;
			IF bank_remaining < money THEN
				SET status = -4;
			END IF;
            IF remaining + money > overdraft THEN
				SET status = -3;
			END IF;
            IF status = -1 THEN
				UPDATE credit_account SET credit_account.remaining = credit_account.remaining + money WHERE credit_account.account_id = card_id;
			END IF;
		ELSE
			SELECT credit_account.remaining FROM credit_account WHERE credit_account.account_id = card_id INTO remaining;
			IF remaining < money THEN
				SET status = remaining;
			END IF;
            IF status = -1 THEN
				UPDATE credit_account SET credit_account.remaining = credit_account.remaining - money WHERE credit_account.account_id = card_id;
            END IF;
		END IF;
	END IF;
END //
DELIMITER ;

DELIMITER //
CREATE procedure saving_interact (IN type INT, IN card_id CHAR(18), IN money INT, OUT status INT)
BEGIN
    DECLARE remaining INT DEFAULT 0;
    DECLARE bank_remaining INT DEFAULT 0;
    SET status = 1;
    IF NOT EXISTS (SELECT * FROM saving_account WHERE saving_account.account_id = card_id) THEN
		SET status = 2;
	END IF;
    IF status = 1 THEN
		IF type = 1 THEN
			UPDATE saving_account SET saving_account.remaining = saving_account.remaining + money WHERE saving_account.account_id = card_id;
		ELSE
			SELECT sub_bank.asset FROM sub_bank, saving_account WHERE saving_account.account_id = card_id AND saving_account.bank_name = sub_bank.bank_name INTO bank_remaining;
			SELECT saving_account.remaining FROM saving_account WHERE saving_account.account_id = card_id INTO remaining;
            IF bank_remaining < money THEN
				SET status = 4;
			END IF;
			IF remaining < money THEN
				SET status = 3;
			END IF;
            IF status = 1 THEN
				UPDATE saving_account SET saving_account.remaining = saving_account.remaining - money WHERE saving_account.account_id = card_id;
			END IF;
		END IF;
	END IF;
END //
DELIMITER ;

DELIMITER //
CREATE procedure borrow_loan (IN client_id CHAR(18), IN bank_name CHAR(30), IN loan_total INT, IN loan_rate FLOAT,  OUT status INT)
BEGIN
    DECLARE max_no INT DEFAULT 0;
    DECLARE next_no INT DEFAULT 0;
    DECLARE test_id INT DEFAULT 0;
	SET status = 1;
    SET test_id = Length(client_id);
    IF NOT EXISTS(SELECT * FROM client WHERE client.client_id = client_id) THEN
		SET status = 2;
	END IF;
    IF status = 1 AND EXISTS(SELECT * FROM loan Where client_id = loan.client_id AND bank_name = loan.bank_name AND remain_loan > 0 )  THEN
		SET status = 3;
	END IF;
	IF (SELECT COUNT(*) FROM loan) != 0 THEN
		SELECT MAX(loan_id) FROM loan INTO max_no;
	END IF;
	SET next_no = max_no+1;
    IF status = 1 AND loan_total > (SELECT sub_bank.asset FROM sub_bank Where sub_bank.bank_name = bank_name) THEN
		SET status = 4;
	END IF;
	IF status = 1 AND NOT EXISTS(SELECT * FROM sub_bank WHERE sub_bank.bank_name = bank_name) THEN
		SET status = 5;
	END IF;
    IF status = 1 THEN
		INSERT INTO `bank`.`loan` (`loan_id`, `client_id`, `bank_name`, `loan_total`, `remain_loan`, `loan_Date`, `loan_rate`) VALUES (next_no, client_id, bank_name, loan_total, loan_total, (Curdate()), loan_rate);
		UPDATE `bank`.`sub_bank` SET asset = asset - loan_total WHERE sub_bank.bank_name = bank_name;
	END IF;
END //
DELIMITER ;

DELIMITER //
CREATE procedure return_loan (IN loan_id CHAR(30), IN pay_money INT,  OUT status INT)
BEGIN
    DECLARE max_no INT DEFAULT 0;
    DECLARE next_no INT DEFAULT 0;
    DECLARE loan_remain INT DEFAULT 0;
    SET status = -1;
	IF (SELECT COUNT(*) FROM pay_status) != 0 THEN
		SELECT MAX(pay_id) FROM pay_status INTO max_no;
	END IF;
    IF NOT EXISTS(SELECT * FROM loan WHERE loan.loan_id = loan_id) THEN
		SET status = -2;
    END IF;
    SELECT loan.remain_loan FROM loan WHERE loan.loan_id = loan_id INTO loan_remain;
	SET next_no = max_no+1;
    IF pay_money > loan_remain THEN
		SET status = loan_remain;
	END IF;
    IF status = -1 THEN
		INSERT INTO `bank`.`pay_status` (`pay_id`, `loan_id`, `Pay_money`, `Pay_date`) VALUES (next_no, loan_id, pay_money, (Curdate()));
	END IF;
END //
DELIMITER ;

DELIMITER //
CREATE procedure calculate_loan_formal ()
BEGIN
	DECLARE state INT DEFAULT 0;
    DECLARE curr_loan INT DEFAULT 0;
    DECLARE curr_remain INT DEFAULT 0;
    DECLARE curr_rate FLOAT DEFAULT 0;
    DECLARE curr_id INT DEFAULT 0;
    DECLARE borr_date DATE;
	DECLARE cs_loan CURSOR FOR (SELECT loan_id, remain_loan, loan_rate FROM loan);
	DECLARE cs_saving CURSOR FOR (SELECT account_id, remaining, interest_rate FROM saving_account);
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET state = 1;
    OPEN cs_loan;
    REPEAT
		FETCH cs_loan INTO curr_id, curr_loan, curr_rate;
        IF state = 0 AND curr_loan > 0 THEN
			SELECT loan_date FROM loan WHERE loan.loan_id = curr_id INTO borr_date;
			IF datediff((Curdate()), borr_date) % 30 = 0 THEN
				UPDATE  `bank`.`loan` SET loan.remain_loan = (curr_loan + CAST(curr_loan*curr_rate as signed)) WHERE loan.loan_id = curr_id;
			END IF;
		END IF;
        UNTIL state = 1
	END REPEAT;
    CLOSE cs_loan;
    SET state = 0;
    OPEN cs_saving;
    REPEAT
		FETCH cs_saving INTO curr_id, curr_remain, curr_rate;
        IF state = 0 THEN
			SELECT open_date FROM saving_account WHERE saving_account.account_id = curr_id INTO borr_date;
			IF datediff((Curdate()), borr_date) % 30 = 0 THEN
				START TRANSACTION;
				SET @enable_trigger = 0;
				UPDATE  `bank`.`saving_account` SET saving_account.remaining = (curr_remain + CAST(curr_remain*curr_rate as signed)) WHERE saving_account.account_id = curr_id;
				SET @enable_trigger = 1;
                COMMIT;
			END IF;
		END IF;
        UNTIL state = 1
	END REPEAT;
    CLOSE cs_saving;
END //
DELIMITER ;

CREATE EVENT update_event_formal
ON SCHEDULE EVERY 1 day STARTS NOW() ON COMPLETION PRESERVE DO 
CALL calculate_loan_formal ();

DELIMITER //
CREATE procedure calculate_loan ()
BEGIN
	DECLARE state INT DEFAULT 0;
    DECLARE curr_loan INT DEFAULT 0;
    DECLARE curr_remain INT DEFAULT 0;
    DECLARE curr_rate FLOAT DEFAULT 0;
    DECLARE curr_id INT DEFAULT 0;
    DECLARE borr_date DATE;
	DECLARE cs_loan CURSOR FOR (SELECT loan_id, remain_loan, loan_rate FROM loan);
	DECLARE cs_saving CURSOR FOR (SELECT account_id, remaining, interest_rate FROM saving_account);
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET state = 1;
    OPEN cs_loan;
    REPEAT
		FETCH cs_loan INTO curr_id, curr_loan, curr_rate;
        IF state = 0 AND curr_loan > 0 THEN
			UPDATE  `bank`.`loan` SET loan.remain_loan = (curr_loan + CAST(curr_loan*curr_rate as signed)) WHERE loan.loan_id = curr_id;
		END IF;
        UNTIL state = 1
	END REPEAT;
    CLOSE cs_loan;
    SET state = 0;
    OPEN cs_saving;
    REPEAT
		FETCH cs_saving INTO curr_id, curr_remain, curr_rate;
        IF state = 0 THEN
			START TRANSACTION;
            SET @enable_trigger = 0;
            UPDATE  `bank`.`saving_account` SET saving_account.remaining = (curr_remain + CAST(curr_remain*curr_rate as signed)) WHERE saving_account.account_id = curr_id;
            SET @enable_trigger = 1;
            COMMIT;
		END IF;
        UNTIL state = 1
	END REPEAT;
    CLOSE cs_saving;
END //
DELIMITER ;

CREATE EVENT update_event
ON SCHEDULE EVERY 10 second STARTS NOW() ON COMPLETION PRESERVE DO 
CALL calculate_loan ();

ALTER EVENT update_event ON COMPLETION PRESERVE DISABLE;
ALTER EVENT update_event ON COMPLETION PRESERVE ENABLE;

DELIMITER //
CREATE trigger Decrease AFTER Insert ON Pay_status FOR EACH ROW 
BEGIN
    UPDATE loan SET loan.remain_loan = (loan.remain_loan - NEW.Pay_money) Where loan.loan_id = NEW.loan_id;
    UPDATE `bank`.`sub_bank`, `bank`.`loan` SET asset = asset + NEW.Pay_money WHERE sub_bank.bank_name = loan.bank_name AND loan.loan_id = NEW.loan_id;
END //
DELIMITER ;

DELIMITER //
CREATE trigger credit_bank AFTER UPDATE ON credit_account FOR EACH ROW 
BEGIN
	UPDATE `bank`.`sub_bank` SET asset = asset - NEW.remaining + OLD.remaining WHERE sub_bank.bank_name = NEW.bank_name;
END //
DELIMITER ;

DELIMITER //
CREATE trigger saving_bank AFTER UPDATE ON saving_account FOR EACH ROW 
BEGIN
	UPDATE `bank`.`sub_bank` SET asset = asset + NEW.remaining - OLD.remaining WHERE sub_bank.bank_name = NEW.bank_name;
END //
DELIMITER ;