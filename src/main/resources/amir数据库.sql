##订单1--多产品型号
##产品型号1--多工序
##产品型号1--多耗材
##多工序 多-- 多 车间

#字典表
CREATE TABLE t_Dictionary
(
    id    int primary key auto_increment,
    type  int comment '数据类型：1 材料类别  2材料单位',
    value varchar(50)
);

INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('1', '泡棉类');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('1', '主要面料类');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('1', '标类');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('1', '辅料类');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('1', '包装材料类');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '双');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '套');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '箱');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '只');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '件');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '米');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', 'SF');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '并');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '张');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', 'KG');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '个');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '卷');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '跟');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '片');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', 'm2');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '颗');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '付');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '顶');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '条');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '块');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('2', '包');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('3', '小手套');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('3', '大手套');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('3', '格斗手套');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('3', '靶类');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('3', '护具类');
INSERT INTO `t_Dictionary` (`type`, `value`)
VALUES ('3', '其他类');


##部门
CREATE TABLE t_SysDept
(
    id         int primary key auto_increment,
    deptName   varchar(50),
    deptOrder  int,
    isWorkShop int NOT NULL comment '0 不是车间   1 是车间'
);

INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('经理室', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('人事部', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('劳资部', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('出货部', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('后勤部', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('验货部', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('外加工部', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('质检部-成品质检', NULL, 1);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('外协质检', NULL, 1);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('行政部', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('技术部', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('生产部', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('采购部', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('下料车间', NULL, 1);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('一车间', NULL, 1);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('二车间', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('信息中心', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('后道车间-车工', NULL, 1);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('后道车间-中辅工', NULL, 1);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('后道车间-大辅工', NULL, 1);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('包装车间', NULL, 1);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('财务部', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('仓库', NULL, 1);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('样品间', NULL, 0);
INSERT INTO `t_SysDept` (`deptName`, `deptOrder`, `isWorkShop`)
VALUES ('后道车间-小辅工', NULL, 1);

update t_SysDept
set deptOrder = id;


INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('车间主任', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('组长', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('车工', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('辅工', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('大辅工', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('检验', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('杂工', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('下料工', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('印刷', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('剪辅料', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('切包边条', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('分料', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('电脑车工', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('包装工', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('新学徒车工', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('新熟练车工', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('机修工', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('小辅工', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('整形，打包', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('门卫', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('食堂', NOW(), NOW(), 0);
INSERT into t_Emp_work (name, createTime, lastModifyTime, isDelete)
VALUES ('中辅工', NOW(), NOW(), 0);

##后台登录账号
CREATE TABLE t_SysUser
(
    id        int primary key auto_increment,
    deptId    int,
    roleId    int,
    loginName varchar(50)  NULL,
    loginPwd  varchar(100) NULL,
    userName  varchar(50)  NULL
);
INSERT INTO `t_SysUser` (`deptId`, `roleId`, `loginName`, `loginPwd`, `userName`)
VALUES ('1', '1', 'admin', '123456', '系统管理员');


##角色
CREATE TABLE t_SysRole
(
    id          int(11) primary key AUTO_INCREMENT,
    name        varchar(20) NOT NULL,
    description varchar(255) DEFAULT NULL,
    roleOrder   int
);
INSERT INTO `t_SysRole` (`name`, `description`, `roleOrder`)
VALUES ('管理员权限', NULL, '1');

##用户角色表
CREATE TABLE t_SysUserRole
(
    id     int(11) primary key AUTO_INCREMENT,
    userId int NOT NULL,
    roleId int NOT NULL
);

##菜单表
CREATE TABLE t_SysMenu
(
    id         int(11) primary key AUTO_INCREMENT,
    name       varchar(100) NOT NULL,
    url        varchar(255),
    parentId   int,
    createTime datetime,
    isDelete   int          NOT NULL
);
##角色菜单权限表
CREATE TABLE t_SysRoleMenu
(
    id     int(11) primary key AUTO_INCREMENT,
    roleId int NOT NULL,
    menuId int NOT NULL
);

##材料
CREATE TABLE t_PM
(
    id             int(11) primary key AUTO_INCREMENT,
    pmNo           varchar(100) NOT NULL comment '物料编号',
    name           varchar(100) NOT NULL comment '物料名称',
    type           varchar(100) NOT NULL comment '类别 泡棉类、主要面料类、标类、辅料类、包装材料类',
    num            decimal(12, 4) comment '数量',
    unit           varchar(30)  NOT NULL comment '单位 双、套、箱、只、件、米、SF、并、张、KG、个、卷、跟、片、m2、颗、付、顶、条、块',
    remark         varchar(200) comment '备注',
    operator       varchar(100) NOT NULL comment '添加人',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int          DEFAULT '0',
    colour         varchar(100) DEFAULT '' COMMENT '颜色',
    norms          varchar(100) DEFAULT '' COMMENT '规格',
    material       varchar(100) DEFAULT '' COMMENT '材质',
    callStr        varchar(100) DEFAULT '' COMMENT '称呼'
);
INSERT INTO `t_PM` (`pmNo`, `name`, `type`, `num`, `unit`, `remark`, `operator`, `createTime`,
                    `lastModifyTime`, `isDelete`)
VALUES ('TEST1', '测试1', '辅料类', '20', '双', 'SSS', '1', '2019-07-30 21:41:53', NULL, '0');
INSERT INTO `t_PM` (`pmNo`, `name`, `type`, `num`, `unit`, `remark`, `operator`, `createTime`,
                    `lastModifyTime`, `isDelete`)
VALUES ('DDD0', '002', '包装材料类', '0', '个', NULL, '1', '2019-07-30 21:57:22', NULL, '0');


##产品   原型中看不出产品信息
CREATE TABLE t_Product
(
    id             int(11) primary key AUTO_INCREMENT,
    productNo      varchar(100) NOT NULL comment '产品型号',
    name           varchar(100) NOT NULL comment '产品名称',
    type           varchar(100) NOT NULL comment '类别 小手套 大手套 格斗手套 靶类 护具类 其他类',
    unit           varchar(30)  NOT NULL comment '单位 双、套、箱、只、件、米、SF、并、张、KG、个、卷、跟、片、m2、颗、付、顶、条、块',
    remark         varchar(200) comment '备注',
    operator       varchar(100) NOT NULL comment '添加人',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int          NOT NULL
);

##产品型号 耗材
CREATE TABLE t_ProductPM
(
    id             int(11) primary key AUTO_INCREMENT,
    productId      int(11),
    productNo      varchar(100) NOT NULL comment '产品型号',
    pmId           int(11),
    pmNo           varchar(100) NOT NULL comment '物料编号',
    pmName         varchar(100) NOT NULL comment '物料名称',
    type           varchar(100) NOT NULL comment '类别 a面料 b内胆 c辅料 d商标 e包装',
    unit           varchar(30)  NOT NULL comment '单位 双、套、箱、只、件、米、SF、并、张、KG、个、卷、跟、片、m2、颗、付、顶、条、块',
    remark         varchar(200) comment '备注',
    status         int(11) comment '0未确认  1确认',
    num            decimal(12, 4) comment '数量(耗料/双)',
    unitNum        decimal(12, 4) comment '数量(双/单位)',
    operator       varchar(100) NOT NULL comment '添加人',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int DEFAULT '0',
    sequence       int(11)      NOT NULL COMMENT '序号'
);
##材料入库单
CREATE TABLE t_PMIn
(
    id             int(11) primary key AUTO_INCREMENT,
    pmId           int(11),
    pmNo           varchar(100) NOT NULL comment '物料编号',
    pmName         varchar(100) NOT NULL comment '物料名称',
    type           varchar(100) NOT NULL comment '类别 a面料 b内胆 c辅料 d商标 e包装',
    unit           varchar(30)  NOT NULL comment '单位 双、套、箱、只、件、米、SF、并、张、KG、个、卷、跟、片、m2、颗、付、顶、条、块',
    remark         varchar(200) comment '备注',
    supplier       varchar(200) comment '供应商',
    inDate         date         NULL COMMENT '入库时间',
    num            decimal(12, 4) comment '入库数量',
    perNum         decimal(12, 4) comment '入库前数量',
    backNum        decimal(12, 4) comment '入库后数量',
    operator       varchar(100) NOT NULL comment '添加人',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int          NOT NULL
);

INSERT INTO `t_PMIn` (`pmId`, `pmNo`, `pmName`, `type`, `unit`, `remark`, `supplier`, `inDate`, `num`,
                      `perNum`, `backNum`, `operator`, `createTime`, `lastModifyTime`, `isDelete`)
VALUES ('1', 'TEST1', '测试1', '辅料类', '10', 'AAA', '！！！！', '2019-07-30', '10', '0', '10', '1', '2019-07-30 21:43:13',
        NULL, '0');
INSERT INTO `t_PMIn` (`pmId`, `pmNo`, `pmName`, `type`, `unit`, `remark`, `supplier`, `inDate`, `num`,
                      `perNum`, `backNum`, `operator`, `createTime`, `lastModifyTime`, `isDelete`)
VALUES ('1', 'TEST1', '测试1', '辅料类', '10', 'AAA', '！！！！', '2019-07-31', '10', '10', '20', '1', '2019-07-30 21:43:13',
        NULL, '0');


##领料单  耗料上限出库  好像可以直接用订单做主表
CREATE TABLE t_PMout_Bill
(
    id              int(11) primary key AUTO_INCREMENT,
    orderId         int(11) comment '订单ID',
    orderNo         varchar(100) comment '订单编号',
    productId       int(11),
    productNo       varchar(100) comment '产品型号',
    billNo          varchar(30)  NOT NULL comment '编号',
    type            int          NOT NULL comment '1 开领料单 2 订单号开单 3 计划外出库',
    workshop        varchar(100) comment '车间',
    distributionNum int comment '分配数量',
    groupNmae       varchar(30) comment '领用组',
    leader          varchar(30) comment '负责人',
    pmType          varchar(50) comment '材料类别',
    pmCheckItem     varchar(50) comment '材料检测',
    stamp           varchar(50) comment '电子章',
    operator        varchar(100) NOT NULL comment '添加人',
    createTime      datetime,
    lastModifyTime  datetime,
    isDelete        int          NOT NULL
);
##领料单 详情（具体材料申领情况）
CREATE TABLE t_PMout_Bill_Detail
(
    id             int(11) primary key AUTO_INCREMENT,
    billId         int          NOT NULL comment '领料单ID',
    pmId           int(11),
    pmNo           varchar(100) NOT NULL comment '物料编号',
    pmName         varchar(100) NOT NULL comment '物料名称',
    pmBatchNo      varchar(100) comment '物料批次编号',
    num            decimal(12, 4) comment '数量',
    perNum         decimal(12, 4) comment '出库前数量',
    backNum        decimal(12, 4) comment '出库后数量',
    unit           varchar(30)  NOT NULL comment '单位 双、套、箱、只、件、米、SF、并、张、KG、个、卷、跟、片、m2、颗、付、顶、条、块',
    remark         varchar(200) comment '备注',
    outInfo        varchar(200) comment '出库说明',
    receiver       varchar(100) comment '领用人',
    sender         varchar(100) comment '发料人',
    operator       varchar(100) NOT NULL comment '添加人',
    outDate        date         NULL COMMENT '出库时间',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int          NOT NULL
);
##客户
CREATE TABLE t_Customer
(
    id             int(11) primary key AUTO_INCREMENT,
    name           varchar(100) NOT NULL comment '客户名称',
    industry       varchar(100) comment '行业',
    salesman       varchar(100) comment '业务员',
    source         varchar(100) comment '客户来源',
    level          varchar(100) comment '客户级别',
    product        varchar(100) comment '主营产品',
    linkMan        varchar(50) comment '联系人',
    phone          varchar(30) comment '手机号',
    tel            varchar(30) comment '联系电话',
    fax            varchar(30) comment '传真',
    email          varchar(100) comment '邮箱',
    address        varchar(100) comment '地址',
    qq             varchar(100),
    msn            varchar(100),
    bankName       varchar(100) comment '开户银行',
    bankNo         varchar(100) comment '银行账号',
    legalPerson    varchar(100) comment '法人代表',
    duty           varchar(100) comment '税号',
    companyAddress varchar(100) comment '公司地址',
    remark         varchar(100) comment '备注',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int          NOT NULL
);

##订单
CREATE TABLE t_Order
(
    id             int(11) primary key AUTO_INCREMENT,
    orderNo        varchar(100) NOT NULL comment '订单编号',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int          NOT NULL
);
##订单 型号表
CREATE TABLE t_Order_Product
(
    id             int(11) primary key AUTO_INCREMENT,
    orderId        int(11) comment '订单ID',
    orderNo        varchar(100) NOT NULL comment '订单编号',
    productId      int(11),
    productNo      varchar(100) NOT NULL comment '产品型号',
    productType    varchar(30)  NOT NULL comment '类别 小手套 大手套 格斗手套 靶类 护具类 其他类',
    num            int comment '数量',
    assignedNum    int comment '已分配数额',
    notAssignNum   int comment '未分配数额 （已分配数量+未分配数量 = 总数量）',
    maxNum         int comment '上限数量',
    unit           varchar(30)  NOT NULL comment '单位 双、套、箱、只、件、米、SF、并、张、KG、个、卷、跟、片、m2、颗、付、顶、条、块',
    completeDate   date comment '出货日期',
    customerId     int comment '客户Id',
    customerName   varchar(30) comment '客户',
    isMore         int comment '是否追加 1追加 2不追加',
    urgentLevel    int comment '紧急程度 1 正常 2 紧急',
    leader         varchar(30) comment '负责人',
    pmType         varchar(50) comment '材料类别',
    pmCheckItem    varchar(50) comment '材料检测',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int          NOT NULL
);

##生产单表
CREATE TABLE t_Production_Order
(
    id               int(11) primary key AUTO_INCREMENT,
    productionNo     varchar(100) NOT NULL COMMENT '生产单编号',
    orderId          int(11)      NOT NULL comment '订单ID',
    orderNo          varchar(100) NOT NULL comment '订单编号',
    productNo        varchar(100) NOT NULL comment '产品型号',
    orderProductId   int(11)      NOT NULL comment '订单 型号表ID',
    maxNum           int comment '上限数量',
    assignNum        int comment '分配数额',
    workshop         varchar(30) comment '车间',
    workshopDirector varchar(100) comment '车间主任',
    isLuo            int          NOT NULL COMMENT '是否按罗分 0否 1 是',
    luoNum           int(11) COMMENT '一罗的数量',
    printer          varchar(100) COMMENT '打印人',
    printTime        DATE COMMENT '打印时间',
    `printCount`     int(11) DEFAULT '0' COMMENT '打印次数',
    codeUrl          varchar(255) comment '二维码地址',
    createTime       datetime,
    lastModifyTime   datetime,
    isDelete         int          NOT NULL
);


##生产单-工序表
CREATE TABLE t_Production_Procedure
(
    id            int(11) primary key AUTO_INCREMENT,
    productionNo  varchar(100) NOT NULL COMMENT '生产单编号',
    orderId       int(11)      NOT NULL comment '订单ID',
    orderNo       varchar(100) NOT NULL comment '订单编号',
    productNo     varchar(100) NOT NULL comment '产品型号',
    procedureId   int(11) comment '工序ID',
    procedureName varchar(255) comment '工序名称',
    createTime    datetime,
    sort          int(11)      NOT NULL comment '工序序号',
    isDelete      int          NOT NULL,
    assignNum     int(11) DEFAULT 0 comment '分配数量',
    luoId         int(11) DEFAULT NULL comment '生产单罗ID'
);

##生产单-工序-人员 多对多表 员工自己扫码的结果
CREATE TABLE t_Production_Procedure_Scan
(
    id            int(11) primary key AUTO_INCREMENT,
    orderNo       varchar(100) NOT NULL comment '订单编号',
    productNo     varchar(100) NOT NULL comment '产品型号',
    productionNo  varchar(50) COMMENT '生产单编号',
    luoId         int(11) comment '生产单罗ID',
    pmOutBillNo   varchar(50) comment '领料单编号',
    procedureId   int(11) comment '工序ID',
    procedureName varchar(255) comment '工序名称',
    empId         int(11) comment '员工ID',
    num           int(11) comment '员工计件数目',
    price         decimal(8, 2) comment '价格',
    money         decimal(10, 2) comment '工资',
    createTime    datetime,
    isDelete      int          NOT NULL,
    status        int comment '0 未质检 1 已质检'
);
##生产单-工序-人员 多对多表 质检之后的结果 车间主任上报结果
CREATE TABLE t_Production_Procedure_Confirm
(
    id             int(11) primary key AUTO_INCREMENT,
    orderNo        varchar(100) NOT NULL comment '订单编号',
    productNo      varchar(100) NOT NULL comment '产品型号',
    procedureId    int(11) comment '工序ID',
    procedureName  varchar(255) comment '工序名称',
    empId          int(11) comment '员工ID',
    num            decimal(8, 2) comment '员工计件数目',
    price          decimal(8, 2) comment '价格',
    money          decimal(10, 2) comment '工资',
    completeTime   datetime comment '员工扫码时间',
    createTime     datetime comment '质检时间',
    lastModifyTime datetime,
    type           int(11) comment '1 员工质检  2 车间主任上报',
    operator       varchar(100) NOT NULL comment '添加人',
    productionNo   varchar(50) COMMENT '生产单编号',
    luoId          int(11) comment '生产单罗ID',
    pmOutBillNo    varchar(50) comment '领料单编号',
    isDelete       int          NOT NULL,
    isChange       int          NOT NULL comment '0未调整 1主任调整  2已结算',
    workshop       varchar(30) comment '车间',
    inspectionor   varchar(255) DEFAULT NULL comment '质检员'
);


##生产单-罗表
CREATE TABLE t_Production_Luo
(
    id           int(11) primary key AUTO_INCREMENT,
    productionNo varchar(100) NOT NULL COMMENT '生产单编号',
    orderId      int(11)      NOT NULL comment '订单ID',
    orderNo      varchar(100) NOT NULL comment '订单编号',
    productNo    varchar(100) NOT NULL comment '产品型号',
    num          int(11)      NOT NULL comment '数量',
    maxNum       int comment '上限数量',
    codeUrl      varchar(255) DEFAULT NULL comment '二维码地址',
    createTime   datetime,
    isDelete     int          NOT NULL,
    sort         int          NOT NULL
);


-- 暂时不用
##订单型号 车间
CREATE TABLE t_Order_Product_Workshop
(
    id             int(11) primary key AUTO_INCREMENT,
    orderProductId int(11) comment '订单型号表ID',
    workshop       varchar(30) comment '车间',
    leader         varchar(30) comment '负责人',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int NOT NULL
);

## 工序单价管理列表
##产品 工序 车间
CREATE TABLE t_Product_Procedure_Workshop
(
    id             int(11) primary key AUTO_INCREMENT,
    productId      int(11),
    productNo      varchar(100) NOT NULL comment '产品型号',
    sort           int(11) comment '排序',
    workshop       varchar(30) comment '车间',
    procedureId    int(11) comment '工序ID',
    procedureName  varchar(255) comment '工序名称',
    price          decimal(5, 2) comment '价格',
    remark         varchar(200) comment '备注',
    operator       varchar(100) NOT NULL comment '添加人',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int          NOT NULL,
    KEY `index_workName` (`workshop`) USING HASH
);

##产品 工序 价格
CREATE TABLE t_Product_Procedure
(
    id             int(11) primary key AUTO_INCREMENT,
    procedureName  varchar(255) comment '工序名称',
    productId      int(11),
    productNo      varchar(100) NOT NULL comment '产品型号',
    sort           int(11) comment '排序',
    price          decimal(8, 2) comment '价格',
    operator       varchar(100) NOT NULL comment '添加人',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int          NOT NULL,
    isConfirm      int(11) DEFAULT 0 comment '是否确认'
);


##员工
CREATE TABLE t_Emp
(
    id             int(11) primary key AUTO_INCREMENT,
    name           varchar(100) NOT NULL comment '姓名',
    deptId         int comment '部门ID',
    isLeader       int comment '是否部门负责人 1是 2 不是',
    sex            int comment '性别 1男 2女',
    workId         int comment '工种ID',
    type           int comment '类别 1计件工 2固定工',
    nativeSource   varchar(100) comment '籍贯',
    Nation         varchar(50) comment '名族',
    birthday       varchar(30) comment '出生日期',
    idCard         varchar(30) comment '身份证号',
    entryDate      varchar(30) comment '入职时间',
    school         varchar(100) comment '毕业学校',
    subject        varchar(100) comment '专业',
    education      varchar(100) comment '学历',
    linkMan        varchar(50) comment '联系人',
    phone          varchar(30) comment '手机号',
    tel            varchar(30) comment '联系电话',
    address        varchar(255) comment '地址',
    salary         decimal(8, 2) comment '工资',
    ylbx           decimal(8, 2) comment '养老保险',
    sybx           decimal(8, 2) comment '失业保险',
    yiliaobx       decimal(8, 2) comment '医疗保险',
    gjj            decimal(8, 2) comment '住房公积金',
    dhbt           decimal(8, 2) comment '工资',
    phoneSubsidy   decimal(8, 2) comment '电话费补贴',
    waterSubsidy   decimal(8, 2) comment '开水费',
    foodSubsidy    decimal(8, 2) comment '夜餐费',
    socialSubsidy  decimal(8, 2) comment '社保补贴',
    remark         varchar(200) comment '备注',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int          NOT NULL,
    password       varchar(255) DEFAULT NULL COMMENT '登入密码'
);

##员工 工种
CREATE TABLE t_Emp_work
(
    id             int(11) primary key AUTO_INCREMENT,
    name           varchar(100) NOT NULL comment '工种名称',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int          NOT NULL
);

##工资月份数据
CREATE TABLE t_Salary_Monthly
(
    id             int(11) primary key AUTO_INCREMENT,
    yearMonth      varchar(30) comment '年月',
    expectWorkDay  int comment '规定上班天数',
    realityWorkDay int comment '正常上班天数',
    basicSalary    decimal(8, 2) comment '基本工资',
    hourlyWage     decimal(4, 2) comment '时薪',
    isMore         int(11) comment '1大于500万  2 小于500万',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int NOT NULL
);

## 员工 月度考勤表
CREATE TABLE t_Emp_Salary_Monthly
(
    id              int(11) primary key AUTO_INCREMENT,
    yearMonth       varchar(30) comment '年月',
    empId           int comment '员工ID',
    empName         varchar(30) comment '名字',
    deptId          int comment '部门ID',
    deptName        varchar(30) comment '部门名字',
    workName        varchar(30) comment '工种',
    salary          decimal(8, 2) comment '工资',
    workDay         decimal(4, 1) comment '上班天数',
    dayWork         decimal(4, 1) comment '正常白班',
    nightWork       decimal(4, 1) comment '正常晚班',
    score           decimal(8, 2) comment '考核分',
    restDay         decimal(4, 1) comment '公休',
    sumDay          decimal(4, 1) comment '总天数',
    daySalary       decimal(8, 2) comment '日工资',
    realSalary      decimal(8, 2) comment '应发工资',
    createTime      datetime,
    lastModifyTime  datetime,
    dayWorkHoliday  decimal(4, 1) comment '节假日白班',
    nightWorkHoliay decimal(4, 1) comment '节假日晚班',
    dayWorkLegal    decimal(4, 1) comment '法假日白班',
    nigthWorkLegal  decimal(4, 1) comment '法假日晚班',
    isDelete        int NOT NULL,
    type            int comment '类别 1计件工 2固定工',
    nigthSnack      decimal(4, 1) DEFAULT NULL COMMENT '夜餐',
    isCalculation   int comment '是否结算 0  未结算 1 已结算'
);

-- 考勤分明细
CREATE TABLE t_Score
(
    id                int(11) primary key AUTO_INCREMENT,
    yearMonth         varchar(30) NOT NULL comment '年月',
    empId             int         NOT NULL comment '员工ID',
    empName           varchar(30) NOT NULL comment '名字',
    deptId            int comment '部门ID',
    deptName          varchar(30) comment '部门名字',
    fiveScore         decimal(8, 2) comment '5s分',
    coordinationScore decimal(8, 2) comment '配合分',
    qualityScore      decimal(8, 2) comment '质量分',
    score             decimal(8, 2) comment '考核分',
    checkworkScore    decimal(8, 2) comment '考勤分',
    isDelete          int         NOT NULL
);


## 员工 月度各种补贴、扣款表
CREATE TABLE t_Emp_Subsidy_Monthly
(
    id             int(11) primary key AUTO_INCREMENT,
    yearMonth      varchar(30) comment '年月',
    empId          int comment '员工ID',
    empName        varchar(30) comment '名字',
    deptId         int comment '部门ID',
    deptName       varchar(30) comment '部门名字',
    workName       varchar(30) comment '工种',
    type           int comment '类型 1电话费补贴 2固定工住房补贴 3计件工住房补贴 4工资补贴 5社保补贴 6工龄补贴 7车工出勤补贴 8新车工出勤补贴 9其他扣款',
    money          decimal(8, 2) comment '补贴（扣除）金额',
    billNo         varchar(30) comment '票据编号',
    reason         varchar(300) comment '补贴事项、扣款原因',
    drawer         varchar(300) comment '开票人',
    drawTime       datetime comment '开票时间',
    remark         varchar(200) comment '备注',
    isConfirm      int comment '是否确认 1确认 2未确认',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int NOT NULL
);

## 员工 计时工资表
CREATE TABLE t_Emp_TimeSalary_Monthly
(
    id             int(11) primary key AUTO_INCREMENT,
    yearMonth      varchar(30) comment '年月',
    empId          int comment '员工ID',
    empName        varchar(30) comment '名字',
    deptId         int comment '部门ID',
    deptName       varchar(30) comment '部门名字',
    workName       varchar(30) comment '工种',
    num            decimal(8, 3) comment '计算数量',
    price          decimal(8, 3) comment '单价',
    money          decimal(10, 2) comment '金额',
    billNo         varchar(30) comment '票据编号',
    content        varchar(300) comment '工作内容',
    drawer         varchar(300) comment '开票人',
    drawTime       datetime comment '开票时间',
    remark         varchar(200) comment '备注',
    unit           varchar(50) comment '单位',
    isConfirm      int comment '是否确认 1确认 2未确认',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int NOT NULL
);


## 工资月度数据汇总
CREATE TABLE t_Summary_Salary_Monthly
(
    id                    int(11) primary key AUTO_INCREMENT,
    yearMonth             varchar(30) comment '年月',
    empId                 int comment '员工ID',
    empName               varchar(30) comment '名字',
    deptId                int comment '部门ID',
    deptName              varchar(30) comment '部门名字',
    workName              varchar(30) comment '工种',
    dayWork               decimal(4, 1) comment '正常白班',
    workDay               decimal(4, 1) comment '上班天数',
    nightWork             decimal(4, 1) comment '正常晚班',
    score                 decimal(8, 2) comment '考核分',
    sumWorkHour           decimal(8, 2) comment '总工时',
    hourSalary            decimal(8, 2) comment '时薪=应发工资/总工时',
    restDay               decimal(4, 1) comment '公休',
    sumDay                decimal(4, 1) comment '总天数',
    daySalary             decimal(8, 2) comment '日工资 = 固定工资/总天数',
    dhbt                  decimal(8, 2) comment '工资',
    type                  int comment '类别 1计件工 2固定工',
    timeSalary            decimal(8, 2) comment '计时工资',
    nigthSnack            decimal(4, 1) DEFAULT NULL comment '夜餐',
    phoneSubsidy          decimal(8, 2) comment '电话补贴',
    mealSubsidy           decimal(8, 2) comment '餐补',
    socialSubsidy         decimal(8, 2) comment '社保补贴',
    hourSubsidy           decimal(8, 2) comment '住房补贴',
    otherSubsidy          decimal(8, 2) comment '其他补贴',
    sumSusbsidy           decimal(12, 2) comment '补贴合计',
    realSalary            decimal(8, 2) comment '应发工资',
    basicSalary           decimal(8, 2) comment '基本工资',
    ylbx                  decimal(8, 2) comment '养老保险',
    sybx                  decimal(8, 2) comment '失业保险',
    yiliaobx              decimal(8, 2) comment '医疗保险',
    gjj                   decimal(8, 2) comment '住房公积金',
    otherDeduction        decimal(8, 2) comment '其他扣款',
    sumDeduction          decimal(8, 2) comment '扣款合计',
    trueSalary            decimal(8, 2) comment '实发工资',
    normalOvertime        decimal(4, 2) comment '正常加班工时',
    holiayOvertime        decimal(4, 2) comment '假日加班工时',
    legalOvertime         decimal(4, 2) comment '法假加班工时',
    createTime            datetime,
    lastModifyTime        datetime,
    isDelete              int NOT NULL,
    workYearSubsidy       decimal(4, 2) comment '工龄补贴',
    percentSubsidy        decimal(4, 2) comment '计件10% 补贴',
    latheWorkerSubsidy    decimal(4, 2) comment '车工 补贴',
    newLatheWorkerSubsidy decimal(4, 2) comment '新车工 补贴',
    twoSideSubsidy        decimal(4, 2) comment '覆面 补贴'
);

## 文件
CREATE TABLE t_File
(
    id             int(11) primary key AUTO_INCREMENT,
    url            varchar(200) comment '存储目录',
    fileName       varchar(50) comment '文件名',
    type           int comment '文件类型 1员工信息 2 考勤信息 。。。。',
    operator       varchar(30) comment '操作人',
    createTime     datetime,
    lastModifyTime datetime,
    isDelete       int NOT NULL
);


## 单号生成器
CREATE TABLE t_BillNo
(
    id   int(11) primary key AUTO_INCREMENT,
    type int comment '单据类型 1 领料单  2订单号开单 3 订单 4 生产单',
    date varchar(30) comment '日期 20190819',
    num  int comment '编号'
);

CREATE TABLE `t_ShortUrlMapping`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `shortUrl` varchar(50)  DEFAULT NULL COMMENT '短链',
    `longUrl`  varchar(255) DEFAULT NULL COMMENT '真实请求路径',
    PRIMARY KEY (`id`),
    UNIQUE KEY `index_short` (`shortUrl`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `t_Multiple_Production`
(
    `id`               int(11) NOT NULL AUTO_INCREMENT,
    `productionNo`     varchar(100) DEFAULT NULL COMMENT '生产单编号',
    `productionId`     int(11)      DEFAULT NULL COMMENT '生产单id',
    `workshopDirector` varchar(100) DEFAULT NULL COMMENT '车间主任',
    `orderId`          int(11)      DEFAULT NULL COMMENT '订单id',
    `orderNo`          varchar(100) DEFAULT NULL COMMENT '订单编号',
    `workshop`         varchar(30)  DEFAULT NULL COMMENT '车间',
    `fristNum`         int(11)      DEFAULT NULL COMMENT '第一个工序的数量',
    `createTime`       datetime     DEFAULT NULL,
    `lastModifyTime`   datetime     DEFAULT NULL,
    `isDelete`         int(11)      DEFAULT NULL,
    `productNo`        varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


ALTER TABLE t_Production_Procedure
    add multipleProductionId int(11) DEFAULT NULL COMMENT '多型号生产单id';
-- type = 2的生产单订单信息不存在
ALTER TABLE t_Production_Order
    MODIFY orderId int(11) DEFAULT NULL comment '订单ID';
ALTER TABLE t_Production_Order
    MODIFY orderNo varchar(100) DEFAULT NULL comment '订单编号';
ALTER TABLE t_Production_Order
    MODIFY productNo varchar(100) DEFAULT NULL comment '产品型号';
ALTER TABLE t_Production_Order
    MODIFY orderProductId int(11) DEFAULT NULL comment '订单 型号表ID';
ALTER TABLE t_Production_Order
    add type int(11) DEFAULT 0 comment '订单 型号表ID';
update t_Production_Order
set type = 1;



ALTER TABLE t_Order_Product
    add remark varchar(255) DEFAULT NULL COMMENT '备注';

ALTER table t_Salary_Monthly
    add twoSideRate decimal(4, 2) DEFAULT NULL COMMENT '负面补贴系数';
ALTER table t_Salary_Monthly
    add assistRate decimal(4, 2) DEFAULT NULL COMMENT '大复工补贴系数';

-- 索引
ALTER TABLE t_Production_Procedure_Scan
    ADD INDEX order_productNo_procedureName (`orderNo`, `productNo`, `procedureName`);
ALTER TABLE t_Production_Procedure_Confirm
    ADD INDEX order_productNo_procedureName (`orderNo`, `productNo`, `procedureName`);
ALTER TABLE t_Product_Procedure_Workshop
    ADD INDEX productNo_workshop (`productNo`, `workshop`);


ALTER TABLE t_Order_Product
    add blanking decimal(8, 2) default 0.0 COMMENT '下料车间 工序数';
ALTER TABLE t_Order_Product
    add frontFm decimal(8, 2) default 0.0 COMMENT '前道车间 --负面';
ALTER TABLE t_Order_Product
    add frontCheck decimal(8, 2) default 0.0 COMMENT '前道车间 质检';
ALTER TABLE t_Order_Product
    add backBigAssist decimal(8, 2) default 0.0 COMMENT '后到大复工';

ALTER TABLE t_Order_Product
    add backCenterAssist decimal(8, 2) default 0.0 COMMENT '后道中中复工';
ALTER TABLE t_Order_Product
    add assist decimal(8, 2) default 0.0 COMMENT '外协质检';
ALTER TABLE t_Order_Product
    add inspection decimal(8, 2) default 0.0 COMMENT '成品质检';

# 原料厂商表
create table t_Raw_Material_Vendor
(
    id                   int auto_increment comment '自增主键'
        primary key,
    vendorName           varchar(100)  not null comment '工厂名称',
    productType          int           null comment '生产类别，ID，取自t_Dictionary表的type=1',
    contactPerson        varchar(100)  null comment '联系人',
    factoryAddress       varchar(1000) null comment '工厂地址',
    contactPhone         varchar(100)  null comment '联系方式',
    contactFax           varchar(100)  null comment '传真',
    contactEMail         varchar(100)  null comment '电子邮箱',
    legalRepresentative  varchar(100)  null comment '法人代表',
    bankName             varchar(100)  null comment '开户银行',
    bankAccountNum       varchar(100)  null comment '银行账号',
    vendorShortName      varchar(100)  not null comment '厂商简称',
    layerOneCorrugated   varchar(100)  null comment '单瓦双层',
    layerTwoCorrugated   varchar(100)  null comment '双瓦五层',
    layerThreeCorrugated varchar(100)  null comment '三瓦七层',
    remark               varchar(1000) null comment '备注',
    shortNameIndex       int default 0 not null comment '简称索引',
    createTime           datetime      null comment '创建时间',
    updateTime           datetime      null comment '更新时间',
    constraint t_Raw_Material_Vendor_vendorName_uindex
        unique (vendorName)
)
    comment '原料厂商';

# 唛头图片数据
create table t_shipping_mark_image
(
    id            int auto_increment
        primary key,
    fileName      varchar(255) not null comment '文件名称，不含类型后缀',
    fileType      varchar(10)  not null comment '文件类型，jpg、png、jpeg，其他格式暂不支持',
    contentType   varchar(255) not null comment '文件的MIME',
    fileStoreName varchar(255) not null comment '文件存储名称，字符串，需保证唯一性',
    createTime    datetime     null comment '创建时间',
    updateTime    datetime     null comment '更新时间',
    constraint t_shipping_mark_image_fileName_uindex
        unique (fileName),
    constraint t_shipping_mark_image_fileStoreName_uindex
        unique (fileStoreName)
)
    comment '唛头图片数据';

# 唛头数据
create table t_shipping_mark
(
    id            int auto_increment
        primary key,
    clientId      int          not null comment '客户ID',
    productNo     varchar(100) null comment '特殊型号，对应t_Product->productNo',
    outsideMarkId int          null comment '外箱唛头图片ID',
    outsideMark   varchar(100) null comment '外箱唛头图片名称',
    insideMarkId  int          null comment '内箱唛头图片ID',
    insideMark    varchar(100) null comment '内箱唛头图片名称',
    createTime    datetime     null comment '创建时间',
    updateTime    datetime     null comment '更新时间'
)
    comment '唛头数据';