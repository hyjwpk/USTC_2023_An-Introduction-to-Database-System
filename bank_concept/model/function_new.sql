Delimiter //
Drop Function IF EXISTS test_ID;
Create Function test_ID(ID char(18), SEX char(1))
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
    Select Cast(SUBSTRING(ID, 7, 4) AS SIGNED) INTO born_year;
	Select Cast(SUBSTRING(ID, 11, 2) AS SIGNED) INTO born_month;
    Select Cast(SUBSTRING(ID, 13, 2) AS SIGNED) INTO born_day;
    Select Cast(SUBSTRING(ID, 17, 1) AS SIGNED) INTO sex_no;
    IF born_month > 12 THEN
		set status = -1;
        return status;
	END IF;
    CASE
		WHEN born_month = 1 AND born_day > 31 THEN 
			set status = -1;
            return status;
        WHEN born_month = 3 AND born_day > 31 THEN 
			set status = -1;
            return status;
		WHEN born_month = 5 AND born_day > 31 THEN 
			set status = -1;
            return status;
		WHEN born_month = 7 AND born_day > 31 THEN 
			set status = -1;
            return status;
		WHEN born_month = 8 AND born_day > 31 THEN 
			set status = -1;
            return status;
		WHEN born_month = 10 AND born_day > 31 THEN 
			set status = -1;
            return status;
		WHEN born_month = 12 AND born_day > 31 THEN 
			set status = -1;
            return status;
		WHEN born_month = 4 AND born_day > 30 THEN 
			set status = -1;
            return status;
		WHEN born_month = 6 AND born_day > 30 THEN 
			set status = -1;
            return status;
		WHEN born_month = 9 AND born_day > 30 THEN 
			set status = -1;
            return status;
		WHEN born_month = 11 AND born_day > 30 THEN 
			set status = -1;
            return status;
		WHEN born_month = 2 AND born_day > 29 AND born_year % 4 = 0 THEN 
			set status = -1;
            return status;
		WHEN born_month = 2 AND born_day > 28 AND born_year % 4 != 0 THEN 
			set status = -1;
            return status;
		ELSE
			set status = 1;
    END CASE;
    IF SEX != 'X' THEN
		IF sex_no%2 = 1 and SEX != 'M' Then
			set status = -1;
			return status;
		END IF;
		IF sex_no%2 = 0 and SEX != 'W' Then
			set status = -1;
			return status;
		END IF;
	END IF;
    set total_num = 0;
    WHILE j <= 17 DO
		Select Cast(SUBSTRING(ID, j, 1) AS SIGNED) INTO curr_num;
		CASE
			WHEN j = 1 THEN		set total_num = total_num+curr_num*7;
            WHEN j = 2 THEN		set total_num = total_num+curr_num*9;
            WHEN j = 3 THEN		set total_num = total_num+curr_num*10;
            WHEN j = 4 THEN		set total_num = total_num+curr_num*5;
            WHEN j = 5 THEN		set total_num = total_num+curr_num*8;
            WHEN j = 6 THEN		set total_num = total_num+curr_num*4;
            WHEN j = 7 THEN		set total_num = total_num+curr_num*2;
            WHEN j = 8 THEN		set total_num = total_num+curr_num*1;
            WHEN j = 9 THEN		set total_num = total_num+curr_num*6;
            WHEN j = 10 THEN	set total_num = total_num+curr_num*3;
            WHEN j = 11 THEN	set total_num = total_num+curr_num*7;
            WHEN j = 12 THEN	set total_num = total_num+curr_num*9;
            WHEN j = 13 THEN	set total_num = total_num+curr_num*10;
            WHEN j = 14 THEN	set total_num = total_num+curr_num*5;
            WHEN j = 15 THEN	set total_num = total_num+curr_num*8;
            WHEN j = 16 THEN	set total_num = total_num+curr_num*4;
            ELSE				set total_num = total_num+curr_num*2;
        END CASE;
		set j = j+1;
	END WHILE;
    set total_num = total_num % 11;
--     return total_num;
    CASE
		WHEN total_num = 0 THEN		set flag = '1';
        WHEN total_num = 1 THEN		set flag = '0';
        WHEN total_num = 2 THEN		set flag = 'x';
        WHEN total_num = 3 THEN		set flag = '9';
        WHEN total_num = 4 THEN		set flag = '8';
        WHEN total_num = 5 THEN		set flag = '7';
        WHEN total_num = 6 THEN		set flag = '6';
        WHEN total_num = 7 THEN		set flag = '5';
        WHEN total_num = 8 THEN		set flag = '4';
        WHEN total_num = 9 THEN		set flag = '3';
        WHEN total_num = 10 THEN	set flag = '2';
    END CASE;
    IF flag != SUBSTRING(ID, 18, 1) THEN
		set status = -1;
        return status;
	END IF;
    return status;
    
END //
Delimiter ;

select bank.test_ID('123123200203150006', 'W');
select bank.test_ID('120106200202200033', 'M');

Delimiter //
Drop Procedure IF EXISTS Employ;
Create Procedure Employ (IN depart_no Integer, IN bank_name CHAR(30), IN mem_name CHAR(30), IN mem_sex CHAR(1), IN personal_ID CHAR(18), IN phone CHAR(11), IN address CHAR(30), OUT status INT)
BEGIN
	DECLARE max_no INT DEFAULT 0;
    DECLARE next_no INT DEFAULT 0;
    DECLARE test_ID INT DEFAULT 0;
    DECLARE test_phone INT DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR 1048 set max_no = 0; 
    set test_ID = Length(personal_ID);
    set test_phone = Length(phone);
    set status = 1;
	IF test_ID <> 18 or ((SELECT personal_ID REGEXP '[^0-9]') = 1) THEN
		set status = 18;
	END IF;
    IF mem_sex != 'M' and mem_sex != 'W' THEN
		set status = 2;
	END IF;
    IF status = 1 THEN
		IF (select bank.test_ID(personal_ID, mem_sex)) = -1 THEN
			set status = 18;
		END IF;
    END IF;
    IF test_phone <> 11 or ((SELECT phone REGEXP '[^0-9]') = 1) THEN
		set status = 11;
	END IF;
	IF NOT EXISTS (select * FROM bank.department WHERE department.depart_no = depart_no and department.bank_name = bank_name) THEN
		set status = 10;
	END IF;
	IF status = 1 THEN
		IF (Select COUNT(*) From member) != 0 THEN
			Select MAX(ID) From member INTO max_no;
		END IF;
		set next_no = max_no+1;
		INSERT INTO `bank`.`member` (`ID`, `Dep_depart_no`, `bank_name`, `name`, `sex`, `Person_ID`, `Phone`, `Address`, `Salary`, `Begin_date`, `level`) VALUES (next_no, depart_no, bank_name, mem_name, mem_sex, personal_ID, phone, address, '5000', (Curdate()), '1');
	END IF;
END //
Delimiter ;

Call Employ ( '1', 'xx1支行', 'b', 'X', '120106200202200033', '10000000001', 'abcD1-2-304', @status);
select 'a20106200202200033' REGEXP '[^0-9]' = 1;
Call Employ ( '', '', '', '', '', '', '', @status);

SELECT @status;

Delimiter //
Drop Procedure IF EXISTS Register;
Create Procedure Register (IN Client_ID CHAR(18), IN Real_name CHAR(30), IN Client_phone CHAR(11), IN Client_address CHAR(30),  IN Client_email CHAR(30), OUT status INT)
BEGIN
	DECLARE test_ID INT DEFAULT 0;
    DECLARE test_phone INT DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR 1062 set status = 1062; 
    set test_ID = Length(Client_ID);
    set test_phone = Length(Client_phone);
    set status = 1;
	IF test_ID <> 18 or ((SELECT Client_ID REGEXP '[^0-9]') = 1) THEN
		set status = 18;
	END IF;
    IF status = 1 THEN
		IF (select bank.test_ID(Client_ID, 'X')) = -1 THEN
			set status = 18;
		END IF;
	END IF;
    IF test_phone <> 11 or ((SELECT client_phone REGEXP '[^0-9]') = 1)THEN
		set status = 11;
	END IF;
    IF status = 1 THEN 
		INSERT INTO `bank`.`client` (`Client_ID`, `Real_name`, `Client_phone`, `Client_address`, `Client_email`) VALUES (Client_ID, Real_name, Client_phone, Client_address, Client_email);
 	END IF;
END //
Delimiter ;

call Register ('120106200202200033', 'wjy', '12345123456', '天津', 'yuki@qq.com', @status);



Delimiter //
Drop Procedure IF EXISTS Borrow_loan;
Create Procedure Borrow_loan (IN Client_ID CHAR(18), IN bank_name CHAR(30), IN loan_total INT, IN loan_rate FLOAT,  OUT status INT)
BEGIN
    DECLARE max_no INT DEFAULT 0;
    DECLARE next_no INT DEFAULT 0;
    DECLARE test_ID INT DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR 1048 set max_no = 0;
    DECLARE CONTINUE HANDLER FOR 1452 set status = 1452;
	set status = 1;
    set test_ID = Length(Client_ID);
    START TRANSACTION;
    IF test_ID <> 18 THEN
		set status = 18;
	END IF;
    IF status = 1 THEN
		IF EXISTS(Select * From loan Where Client_ID = loan.Client_ID and bank_name = loan.bank_name and remain_loan > 0 ) THEN
			set status = 100;
		END IF;
	END IF;
	IF (Select COUNT(*) From loan) != 0 THEN
		Select MAX(Loan_ID) From loan INTO max_no;
	END IF;
	set next_no = max_no+1;
    IF loan_total > (Select sub_bank.asset From sub_bank Where sub_bank.bank_name = bank_name) THEN
		set status = 99;
	END IF;
	INSERT INTO `bank`.`loan` (`loan_ID`, `Client_ID`, `bank_name`, `loan_total`, `remain_loan`, `loan_Date`, `loan_rate`) VALUES (next_no, Client_ID, bank_name, loan_total, loan_total, (Curdate()), loan_rate);
    UPDATE `bank`.`sub_bank` set asset = asset - loan_total WHERE sub_bank.bank_name = bank_name;
    IF status = 1 THEN
		COMMIT;
	ELSE
		ROLLBACK;
	END IF;
END //
Delimiter ;
call Borrow_loan('302300000000010000', 'xx2支行', '10000', '0.05', @status);
SELECT @status;

Delimiter //
Drop Procedure IF EXISTS return_loan;
Create Procedure return_loan (IN Loan_ID CHAR(30), IN pay_money INT,  OUT status INT)
BEGIN
    DECLARE max_no INT DEFAULT 0;
    DECLARE next_no INT DEFAULT 0;
    DECLARE loan_remain INT DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR 1452 set status = -1452;
    START TRANSACTION;
    SET @enable_trigger = 1;
	IF (Select COUNT(*) From pay_status) != 0 THEN
		Select MAX(Pay_ID) From pay_status INTO max_no;
	END IF;
    SELECT loan.remain_loan FROM loan WHERE loan.Loan_ID = Loan_ID INTO loan_remain;
	set next_no = max_no+1;
    set status = -1;
    IF pay_money > loan_remain THEN
		SET status = loan_remain;
	END IF;
	INSERT INTO `bank`.`pay_status` (`Pay_ID`, `Loan_ID`, `Pay_money`, `Pay_date`) VALUES (next_no, Loan_ID, pay_money, (Curdate()));
    IF status = -1 THEN
		COMMIT;
	ELSE
		ROLLBACK;
	END IF;
END //
Delimiter ;


Delimiter //
Drop Trigger IF EXISTS Decrease;
Create Trigger Decrease AFTER Insert ON Pay_status FOR EACH ROW 
BEGIN
    Update Loan Set Loan.remain_loan = (Loan.remain_loan - NEW.Pay_money) Where Loan.Loan_ID = NEW.Loan_ID;
    UPDATE `bank`.`sub_bank`, `bank`.`Loan` set asset = asset + NEW.Pay_money WHERE sub_bank.bank_name = Loan.bank_name and Loan.Loan_ID = NEW.Loan_ID;
END //
Delimiter ;

call return_loan('10', '550', @status);
SELECT @status;

-- 方法一
SELECT @@event_scheduler;
-- 方法二
SHOW VARIABLES LIKE 'event%';

Delimiter //
Drop Procedure IF EXISTS calculate_loan_formal;
Create Procedure calculate_loan_formal ()
BEGIN
	DECLARE state INT DEFAULT 0;
    DECLARE curr_loan INT DEFAULT 0;
    DECLARE curr_remain INT DEFAULT 0;
    DECLARE curr_rate FLOAT DEFAULT 0;
    DECLARE curr_ID INT DEFAULT 0;
    DECLARE borr_date DATE;
	DECLARE cs_loan CURSOR FOR (SELECT Loan_ID, remain_loan, Loan_rate FROM loan);
	DECLARE cs_saving CURSOR FOR (SELECT Account_ID, Remaining, interest_rate FROM saving_account);
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET state = 1;
    OPEN cs_loan;
    REPEAT
		FETCH cs_loan INTO curr_ID, curr_loan, curr_rate;
        IF state = 0 and curr_loan > 0 THEN
			SELECT Loan_date FROM loan WHERE Loan.Loan_ID = curr_ID INTO borr_date;
			IF datediff((Curdate()), borr_date) % 30 = 0 THEN
				UPDATE  `bank`.`Loan` Set Loan.remain_loan = (curr_loan + CAST(curr_loan*curr_rate as signed)) WHERE Loan.Loan_ID = curr_ID;
			END IF;
		END IF;
        UNTIL state = 1
	END REPEAT;
    CLOSE cs_loan;
    set state = 0;
    OPEN cs_saving;
    REPEAT
		FETCH cs_saving INTO curr_ID, curr_remain, curr_rate;
        IF state = 0 THEN
			SELECT Open_date FROM saving_account WHERE saving_account.Account_ID = curr_ID INTO borr_date;
			IF datediff((Curdate()), borr_date) % 30 = 0 THEN
				START TRANSACTION;
				SET @enable_trigger = 0;
				UPDATE  `bank`.`saving_account` Set saving_account.Remaining = (curr_remain + CAST(curr_remain*curr_rate as signed)) WHERE saving_account.Account_ID = curr_ID;
				SET @enable_trigger = 1;
                COMMIT;
			END IF;
		END IF;
        UNTIL state = 1
	END REPEAT;
    CLOSE cs_saving;
END //
Delimiter ;

DROP EVENT IF EXISTS update_event_formal;
CREATE EVENT update_event_formal
ON SCHEDULE EVERY 1 day STARTS NOW() ON COMPLETION PRESERVE DO 
CALL calculate_loan_formal ();

Delimiter //
Drop Procedure IF EXISTS calculate_loan;
Create Procedure calculate_loan ()
BEGIN
	DECLARE state INT DEFAULT 0;
    DECLARE curr_loan INT DEFAULT 0;
    DECLARE curr_remain INT DEFAULT 0;
    DECLARE curr_rate FLOAT DEFAULT 0;
    DECLARE curr_ID INT DEFAULT 0;
    DECLARE borr_date DATE;
	DECLARE cs_loan CURSOR FOR (SELECT Loan_ID, remain_loan, Loan_rate FROM loan);
	DECLARE cs_saving CURSOR FOR (SELECT Account_ID, Remaining, interest_rate FROM saving_account);
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET state = 1;
    OPEN cs_loan;
    REPEAT
		FETCH cs_loan INTO curr_ID, curr_loan, curr_rate;
        IF state = 0 and curr_loan > 0 THEN
			UPDATE  `bank`.`Loan` Set Loan.remain_loan = (curr_loan + CAST(curr_loan*curr_rate as signed)) WHERE Loan.Loan_ID = curr_ID;
		END IF;
        UNTIL state = 1
	END REPEAT;
    CLOSE cs_loan;
    set state = 0;
    OPEN cs_saving;
    REPEAT
		FETCH cs_saving INTO curr_ID, curr_remain, curr_rate;
        IF state = 0 THEN
			START TRANSACTION;
            SET @enable_trigger = 0;
            UPDATE  `bank`.`saving_account` Set saving_account.Remaining = (curr_remain + CAST(curr_remain*curr_rate as signed)) WHERE saving_account.Account_ID = curr_ID;
            SET @enable_trigger = 1;
            COMMIT;
		END IF;
        UNTIL state = 1
	END REPEAT;
    CLOSE cs_saving;
END //
Delimiter ;

DROP EVENT IF EXISTS update_event;
CREATE EVENT update_event
ON SCHEDULE EVERY 10 second STARTS NOW() ON COMPLETION PRESERVE DO 
CALL calculate_loan ();

ALTER EVENT update_event ON COMPLETION PRESERVE DISABLE;
ALTER EVENT update_event ON COMPLETION PRESERVE ENABLE;

Delimiter //
Drop Procedure IF EXISTS get_card;
Create Procedure get_card (IN card_type INT, IN Client_ID CHAR(18), IN bank_name CHAR(30), IN card_password CHAR(20), IN Remain INT, IN overdraft INT, IN interest_rate FLOAT, OUT status INT)
BEGIN
    DECLARE curr_max INT DEFAULT 0;
    DECLARE max_no INT DEFAULT 0;
    DECLARE next_no INT DEFAULT 1;
    DECLARE test_ID INT DEFAULT 0;
    DECLARE CONTINUE HANDLER FOR 1048 set curr_max = 0;
    DECLARE CONTINUE HANDLER FOR 1452 set status = -1452;
    set status = 1;
    
    IF card_type = 1 and EXISTS(Select * From saving_unique Where Client_ID = saving_unique.Client_ID and bank_name = saving_unique.bank_name) THEN
		set status = 2;
	END IF;
    IF card_type = 2 and EXISTS(Select * From credit_unique Where Client_ID = credit_unique.Client_ID and bank_name = credit_unique.bank_name) THEN
		set status = 2;
	END IF;
    
    IF NOT EXISTS(SELECT * FROM client WHERE client.Client_ID = Client_ID) THEN
		set status = 3;
	END IF;
    IF NOT EXISTS(SELECT * FROM sub_bank WHERE sub_bank.bank_name = bank_name) THEN
		set status = 4;
	END IF;
    
    IF (Select COUNT(*) From saving_account) != 0 THEN
		Select MAX(account_ID) From saving_account INTO curr_max;
	END IF;
    set max_no = curr_max;
    IF (Select COUNT(*) From credit_account) != 0 THEN
		Select MAX(account_ID) From credit_account INTO curr_max;
	END IF;
    IF curr_max > max_no THEN
		set max_no = curr_max;
	END IF;
	set next_no = max_no+1;
    
    set test_ID = Length(Client_ID);
    START TRANSACTION;
    IF test_ID <> 18 THEN
		set status = 18;
	END IF;
	IF card_type = 1 and status = 1 THEN
		INSERT INTO saving_account (`Account_ID`, `Client_ID`, `bank_name`, `Password`, `Remaining`, `Open_date`, `interest_rate`) VALUES (next_no, Client_ID, bank_name, card_password, Remain, (Curdate()), interest_rate);
        INSERT INTO saving_unique (`Account_ID`, `bank_name`, `Client_ID`) VALUES (next_no, bank_name, Client_ID);
        UPDATE sub_bank SET asset = asset + Remain WHERE sub_bank.bank_name = bank_name;
	END IF;
    IF card_type = 2 and status = 1 THEN
		INSERT INTO credit_account (`Account_ID`, `Client_ID`, `bank_name`, `Password`, `Remaining`, `Open_date`, `overdraft`) VALUES (next_no, Client_ID, bank_name, card_password, '0', (Curdate()), overdraft);
        INSERT INTO credit_unique (`Account_ID`, `bank_name`, `Client_ID`) VALUES (next_no, bank_name, Client_ID);
    END IF;
    IF status = 1 THEN
		COMMIT;
	ELSE
		ROLLBACK;
	END IF;
END //
Delimiter ;

call get_card(2,'120106200202200033', 'xx1支行', '123123',NULL ,'10000', NULL, @status);
SELECT @status;

Delimiter //
Drop Procedure IF EXISTS delete_card;
Create Procedure delete_card (IN card_type INT, IN card_ID INT, OUT status INT)
BEGIN
    DECLARE remaining INT DEFAULT 0;
    set status = 1;
    
    IF card_type = 1 THEN
		SELECT saving_account.Remaining FROM saving_account WHERE saving_account.Account_ID = card_ID INTO remaining;
        IF remaining != 0 THEN
			SET status = 2;
		ELSE
			DELETE FROM `bank`.`saving_unique` WHERE `Account_ID` = card_ID;
            DELETE FROM `bank`.`saving_account` WHERE `Account_ID` = card_ID;
		END IF;
	ELSE
		SELECT credit_account.Remaining FROM credit_account WHERE credit_account.Account_ID = card_ID INTO remaining;
        IF remaining != 0 THEN
			SET status = 2;
		ELSE
			DELETE FROM `bank`.`credit_unique` WHERE `Account_ID` = card_ID;
            DELETE FROM `bank`.`credit_account` WHERE `Account_ID` = card_ID;
		END IF;
	END IF;
END //
Delimiter ;
call delete_card (2, 4, @status);
SELECT @status;

Delimiter //
Drop Procedure IF EXISTS saving_interact;
Create Procedure saving_interact (IN type INT, IN card_ID CHAR(18), IN money INT, OUT status INT)
BEGIN
    DECLARE remaining INT DEFAULT 0;
    DECLARE bank_remaining INT DEFAULT 0;
    set status = 1;
    START TRANSACTION;
    SET @enable_trigger = 1;
    IF NOT EXISTS (SELECT * FROM saving_account WHERE saving_account.Account_ID = card_ID) THEN
		SET status = 2;
	END IF;
    IF status = 1 THEN
		IF type = 1 THEN
			UPDATE saving_account SET saving_account.remaining = saving_account.remaining + money WHERE saving_account.Account_ID = card_ID;
		ELSE
			SELECT sub_bank.asset FROM sub_bank, saving_account WHERE saving_account.Account_ID = card_ID and saving_account.bank_name = sub_bank.bank_name INTO bank_remaining;
			SELECT saving_account.remaining FROM saving_account WHERE saving_account.Account_ID = card_ID INTO remaining;
            IF bank_remaining < money THEN
				SET status = 4;
			END IF;
			IF remaining < money THEN
				SET status = 3;
			END IF;
			UPDATE saving_account SET saving_account.remaining = saving_account.remaining - money WHERE saving_account.Account_ID = card_ID;
		END IF;
	END IF;
    IF status = 1 THEN
		COMMIT;
	ELSE
		ROLLBACK;
	END IF;
END //
Delimiter ;
call saving_interact(2, 3, 1000, @status);
SELECT @status;

Delimiter //
Drop Trigger IF EXISTS saving_bank;
Create Trigger saving_bank AFTER UPDATE ON saving_account FOR EACH ROW 
BEGIN
	IF @enable_trigger = 1 THEN
		UPDATE `bank`.`sub_bank` set asset = asset + NEW.remaining - OLD.remaining WHERE sub_bank.bank_name = NEW.bank_name;
	END IF;
END //
Delimiter ;

call saving_interact(1, 2, 1000, @status);
SELECT @status;

Delimiter //
Drop Procedure IF EXISTS credit_interact;
Create Procedure credit_interact (IN type INT, IN card_ID CHAR(18), IN money INT, OUT status INT)
BEGIN
    DECLARE remaining INT DEFAULT 0;
    DECLARE overdraft INT DEFAULT 0;
	DECLARE bank_remaining INT DEFAULT 0;
    set status = -1;
    START TRANSACTION;
    SET @enable_trigger = 1;
    IF NOT EXISTS (SELECT * FROM credit_account WHERE credit_account.Account_ID = card_ID) THEN
		SET status = -2;
	END IF;
    IF status = -1 THEN
		IF type = 2 THEN
			SELECT credit_account.remaining FROM credit_account WHERE credit_account.Account_ID = card_ID INTO remaining;
            SELECT credit_account.overdraft FROM credit_account WHERE credit_account.Account_ID = card_ID INTO overdraft;
            SELECT sub_bank.asset FROM sub_bank, credit_account WHERE credit_account.Account_ID = card_ID and credit_account.bank_name = sub_bank.bank_name INTO bank_remaining;
			IF bank_remaining < money THEN
				SET status = -4;
			END IF;
            IF remaining + money > overdraft THEN
				SET status = -3;
			END IF;
			UPDATE credit_account SET credit_account.remaining = credit_account.remaining + money WHERE credit_account.Account_ID = card_ID;
		ELSE
			SELECT credit_account.remaining FROM credit_account WHERE credit_account.Account_ID = card_ID INTO remaining;
			IF remaining < money THEN
				SET status = remaining;
			END IF;
			UPDATE credit_account SET credit_account.remaining = credit_account.remaining - money WHERE credit_account.Account_ID = card_ID;
		END IF;
	END IF;
    IF status = -1 THEN
		COMMIT;
	ELSE
		ROLLBACK;
	END IF;
END //
Delimiter ;

Delimiter //
Drop Trigger IF EXISTS credit_bank;
Create Trigger credit_bank AFTER UPDATE ON credit_account FOR EACH ROW 
BEGIN
	IF @enable_trigger = 1 THEN
		UPDATE `bank`.`sub_bank` set asset = asset - NEW.remaining + OLD.remaining WHERE sub_bank.bank_name = NEW.bank_name;
	END IF;
END //
Delimiter ;

Delimiter //
Drop Procedure IF EXISTS bank_rename;
Create Procedure bank_rename (IN old_name CHAR(30), IN new_name CHAR(30), OUT status INT)
BEGIN
	SET status = 1;
	IF EXISTS (SELECT * FROM sub_bank WHERE sub_bank.bank_name = new_name) and old_name != new_name THEN
		SET status = 2;
	END IF;
    IF status = 1 THEN
		SET FOREIGN_KEY_CHECKS = 0;
        UPDATE department SET department.bank_name = new_name WHERE department.bank_name = old_name;
        UPDATE loan SET loan.bank_name = new_name WHERE loan.bank_name = old_name;
        UPDATE member SET member.bank_name = new_name WHERE member.bank_name = old_name;
        UPDATE credit_account SET credit_account.bank_name = new_name WHERE credit_account.bank_name = old_name;
        UPDATE credit_unique SET credit_unique.bank_name = new_name WHERE credit_unique.bank_name = old_name;
        UPDATE saving_account SET saving_account.bank_name = new_name WHERE saving_account.bank_name = old_name;
        UPDATE saving_unique SET saving_unique.bank_name = new_name WHERE saving_unique.bank_name = old_name;
        UPDATE sub_bank SET sub_bank.bank_name = new_name WHERE sub_bank.bank_name = old_name;
        SET FOREIGN_KEY_CHECKS = 1;
    END IF;
END //
Delimiter ;

call bank_rename("xx1支行",  "abd", @status);
SELECT @status;

Delimiter //
Drop Procedure IF EXISTS edit_member;
Create Procedure edit_member (IN ID INT, IN depart_no INT, IN depart_no_new INT, IN Dep_depart_no INT, IN Dep_depart_no_new INT, IN bank_name CHAR(30), IN bank_name_new CHAR(30), IN name_new CHAR(30), IN phone_new CHAR(11), IN address_new CHAR(30), IN salary INT, IN salary_new INT, IN level INT, IN level_new INT, OUT status INT)
BEGIN
	DECLARE money INT DEFAULT salary_new;
    DECLARE depart INT DEFAULT Dep_depart_no_new;
    DECLARE manage_dep INT DEFAULT depart_no_new;
	SET status = 1;
    IF manage_Dep = 0 THEN
		SET manage_Dep = NULL;
	END IF;
    IF depart_no_new != 0 and (depart_no != depart_no_new or ISNULL(depart_no)) THEN
		SET depart = manage_Dep;
	END IF;
	IF NOT EXISTS (SELECT * FROM sub_bank WHERE sub_bank.bank_name = bank_name_new)  THEN
		SET status = 2;
	END IF;
    IF NOT EXISTS (SELECT * FROM department WHERE department.Depart_no = depart)  THEN
		SET status = 3;
	END IF;
    IF depart_no_new != 0  and NOT EXISTS (SELECT * FROM department WHERE department.Depart_no = depart_no_new)  THEN
		SET status = 3;
	END IF;
    IF status = 1 THEN
		IF NOT EXISTS (SELECT * FROM department WHERE department.Depart_no = depart and department.bank_name = bank_name_new) THEN
			SET status = 4;
        END IF;
        IF NOT ISNULL(depart_no_new) and NOT ISNULL(depart_no) and depart_no_new != Dep_depart_no_new and depart_no_new = depart_no THEN
			SET status = 5;
		END IF;
    END IF;
    IF status = 1 THEN
        IF depart_no_new != depart_no or ISNULL(depart_no) THEN
			UPDATE member, department SET member.depart_no = manage_dep WHERE member.ID = ID;
            UPDATE department SET department.ID = NULL WHERE department.Depart_no = depart_no;
			IF depart_no_new != 0 THEN
				UPDATE member, department SET member.depart_no = NULL WHERE member.ID = department.ID and department.depart_no = depart_no_new;
                UPDATE department SET department.ID = ID WHERE department.Depart_no = depart_no_new;
            END IF;
        END IF;
        IF level != level_new and salary = salary_new THEN
			SET money = salary + 3000*(level_new-level);
        END IF;
        UPDATE member SET member.Dep_depart_no = depart, member.bank_name = bank_name_new, member.name = name_new, member.phone = phone_new, member.address = address_new, member.salary = money, member.level = level_new WHERE member.ID = ID;
    END IF;
END //
Delimiter ;
CALL edit_member (1, null, 1, 2, 2, 2, 2, 'c', '20000000000', '4-5-607', 103000, 103000, 8, 8, @status);
SELECT @status;
