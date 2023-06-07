INSERT INTO `bank`.`sub_bank` (`bank_name`, `bank_location`, `asset`) VALUES ('xx1支行', '合肥', '20000000');
INSERT INTO `bank`.`sub_bank` (`bank_name`, `bank_location`, `asset`) VALUES ('xx2支行', '上海', '20000000');

INSERT INTO `bank`.`department` (`depart_no`, `bank_name`, `depart_name`, `depart_type`) VALUES ('1', 'xx1支行', '服务部门', '服务');
INSERT INTO `bank`.`department` (`depart_no`, `bank_name`, `depart_name`, `depart_type`) VALUES ('2', 'xx1支行', '记账部门', '记账');
INSERT INTO `bank`.`department` (`depart_no`, `bank_name`, `depart_name`, `depart_type`) VALUES ('3', 'xx2支行', '服务部门', '服务');
INSERT INTO `bank`.`department` (`depart_no`, `bank_name`, `depart_name`, `depart_type`) VALUES ('4', 'xx2支行', '记账部门', '记账');

INSERT INTO `bank`.`member` (`ID`, `depart_no`, `Dep_depart_no`, `bank_name`, `name`, `sex`, `Person_ID`, `Phone`, `Address`, `Salary`, `Begin_date`, `level`) VALUES ('1', '1', '1', 'xx1支行', 'b', 'M', '100000000000000001', '10000000001', 'abcD1-2-304', '100000', '2019-1-1', '7');
INSERT INTO `bank`.`member` (`ID`, `depart_no`, `Dep_depart_no`, `bank_name`, `name`, `sex`, `Person_ID`, `Phone`, `Address`, `Salary`, `Begin_date`, `level`) VALUES ('2', '2', '2', 'xx1支行', 'c', 'W', '100000000000000000', '20000000000', 'ac 4-5-607', '100000', '2018-01-01', '8');
INSERT INTO `bank`.`member` (`ID`, `depart_no`, `Dep_depart_no`, `bank_name`, `name`, `sex`, `Person_ID`, `Phone`, `Address`, `Salary`, `Begin_date`, `level`) VALUES ('3', '3', '3', 'xx2支行', 'x', 'W', '200000000000000000', '12300000001', '下北泽', '900000', '2018-07-01', '7');
INSERT INTO `bank`.`member` (`ID`, `depart_no`, `Dep_depart_no`, `bank_name`, `name`, `sex`, `Person_ID`, `Phone`, `Address`, `Salary`, `Begin_date`, `level`) VALUES ('4', '4', '4', 'xx2支行', 'y', 'M', '200000000000000001', '12300000002', '编不出来了', '900000', '2018-08-01', '7');
INSERT INTO `bank`.`member` (`ID`, `Dep_depart_no`, `bank_name`, `name`, `sex`, `Person_ID`, `Phone`, `Address`, `Salary`, `Begin_date`, `level`) VALUES ('100', '1', 'xx1支行', 'a', 'M', '100000000000000011', '10000000000', 'abc1-2-304', '5000', '2023-05-29', '1');
INSERT INTO `bank`.`member` (`ID`, `Dep_depart_no`, `bank_name`, `name`, `sex`, `Person_ID`, `Phone`, `Address`, `Salary`, `Begin_date`, `level`) VALUES ('200', '4', 'xx2支行', 'z', 'W', '200000000000000022', '12300000000', '电波塔', '6000', '2022-04-02',  '2');

UPDATE `bank`.`department` SET `ID` = '1' WHERE (`depart_no` = '1');
UPDATE `bank`.`department` SET `ID` = '2' WHERE (`depart_no` = '2');
UPDATE `bank`.`department` SET `ID` = '3' WHERE (`depart_no` = '3');
UPDATE `bank`.`department` SET `ID` = '4' WHERE (`depart_no` = '4');

INSERT INTO `bank`.`client` (`Client_ID`, `Real_name`, `Client_phone`, `Client_address`, `Client_email`) VALUES ('123123200203150000', '泽村斯潘塞英梨梨', '11223344556', '我家', 'eriri@qq.com');
INSERT INTO `bank`.`client` (`Client_ID`, `Real_name`, `Client_phone`, `Client_address`, `Client_email`) VALUES ('234234000000000000', '水上由岐', '12345123456', '泽衣村', 'yuki@qq.com');
INSERT INTO `bank`.`client` (`Client_ID`, `Real_name`, `Client_phone`, `Client_address`, `Client_email`) VALUES ('280800000000000000', '锦木千束', '12345678901', 'LycoReco', 'chisato@qq.com');
INSERT INTO `bank`.`client` (`Client_ID`, `Real_name`, `Client_phone`, `Client_address`, `Client_email`) VALUES ('302300000000000000', '井之上泷奈', '12345678909', 'LycoReco', 'takina@qq.com');

INSERT INTO `bank`.`loan` (`Loan_ID`, `Client_ID`, `bank_name`, `Loan_total`, `remain_loan`, `Loan_date`, `Loan_rate`) VALUES ('10', '302300000000000000', 'xx1支行', '10000', '10000', '2022-11-11', '0.05');

INSERT INTO `bank`.`pay_status` (`Pay_ID`, `Loan_ID`, `Pay_money`, `Pay_date`) VALUES ('11', '10', '1000', '2022-12-12');
INSERT INTO `bank`.`pay_status` (`Pay_ID`, `Loan_ID`, `Pay_money`, `Pay_date`) VALUES ('12', '10', '3000', '2023-3-1');

INSERT INTO `bank`.`credit_account` (`Account_ID`, `Client_ID`, `bank_name`, `Password`, `Remaining`, `Open_date`, `overdraft`) VALUES ('1', '123123200203150000', 'xx1支行', '123123', '3000', '2023-3-1', '10000');

INSERT INTO `bank`.`credit_unique` (`bank_name`, `Account_ID`, `Client_ID`) VALUES ('xx1支行', '1', '123123200203150000');

INSERT INTO `bank`.`saving_account` (`Account_ID`, `Client_ID`, `bank_name`, `Password`, `Remaining`, `Open_date`, `interest_rate`) VALUES ('2', '280800000000000000', 'xx1支行', '123456', '5000', '2023-2-1', '0.03');

INSERT INTO `bank`.`saving_unique` (`Account_ID`, `bank_name`, `Client_ID`) VALUES ('2', 'xx1支行', '280800000000000000');

CREATE table user
(  
   name            char(20)                       not null,
   password        char(20)                       not null,
   constraint pk_user primary key (name)
) ;

INSERT INTO `bank`.`user` (`name`, `password`) VALUES ('admin', '123456');
INSERT INTO `bank`.`user` (`name`, `password`) VALUES ('user', '111111');
