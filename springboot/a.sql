CREATE TABLE account (
    id INT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(255) NOT NULL COMMENT '用户名',
    name VARCHAR(255) NOT NULL COMMENT '名称',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    role VARCHAR(255) NOT NULL COMMENT '角色标识',
    new_password VARCHAR(255) COMMENT '新密码',
    avatar VARCHAR(255) COMMENT '头像',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色用户表';



CREATE TABLE admin (
    id INT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(255) NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    name VARCHAR(255) NOT NULL COMMENT '姓名',
    avatar VARCHAR(255) COMMENT '头像',
    role VARCHAR(255) NOT NULL COMMENT '角色标识',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';


CREATE TABLE category (
    id INT NOT NULL AUTO_INCREMENT COMMENT '类别ID',
    name VARCHAR(255) NOT NULL COMMENT '类别名称',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='类别表';


CREATE TABLE comment (
    id INT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    film_id INT NOT NULL COMMENT '电影ID',
    score DOUBLE COMMENT '评分',
    comment TEXT NOT NULL COMMENT '评论内容',
    user_id INT NOT NULL COMMENT '用户ID',
    time DATETIME NOT NULL COMMENT '评论时间',
    type VARCHAR(50) COMMENT '评论类型',
    filmName VARCHAR(255) COMMENT '电影名称',
    userName VARCHAR(255) COMMENT '用户名称',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';



CREATE TABLE film (
    id INT NOT NULL AUTO_INCREMENT COMMENT '电影ID',
    name VARCHAR(255) NOT NULL COMMENT '电影名称',
    cover VARCHAR(255) COMMENT '封面图片URL',
    descr TEXT COMMENT '电影描述',
    year VARCHAR(10) COMMENT '上映年份',
    director VARCHAR(255) COMMENT '导演',
    actors VARCHAR(255) COMMENT '演员',
    category_id INT COMMENT '类别ID',
    country VARCHAR(100) COMMENT '国家',
    language VARCHAR(100) COMMENT '语言',
    date DATE COMMENT '发布日期',
    duration VARCHAR(50) COMMENT '时长',
    imdb VARCHAR(50) COMMENT 'IMDB链接',
    category_name VARCHAR(255) COMMENT '类别名称',
    comment_num INT DEFAULT 0 COMMENT '评论数量',
    score DOUBLE COMMENT '电影评分',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电影表';


CREATE TABLE notice (
    id INT NOT NULL AUTO_INCREMENT COMMENT '通知ID',
    title VARCHAR(255) NOT NULL COMMENT '通知标题',
    content TEXT NOT NULL COMMENT '通知内容',
    time DATETIME NOT NULL COMMENT '通知时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知表';


CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(255) NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    name VARCHAR(255) NOT NULL COMMENT '姓名',
    avatar VARCHAR(255) COMMENT '头像',
    role VARCHAR(255) NOT NULL COMMENT '角色标识',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='普通用户表';
