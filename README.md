```sql
create table member
(
    id varchar(255) primary key,
    name varchar(255),
    email varchar(255),
    birthDate date,
    gender varchar(50),
)

create table membership
(
    code bigint auto_increment primary key,
    name varchar(255) unique,
)

create table member_membership
(
  member varchar(255),
    membership bigint,
    foreign key (member) references member (id),
    foreign key (membership) references membership (code),
    primary key (member, membership)
)

create table product
(
    id varchar(255),
    name varchar(255),
    price int,
    primary key (id)
)

create table `order` (
    `id` VARCHAR(255) NOT NULL,
    `totalPrice` INT NOT NULL,
    `memberId` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`memberId`) REFERENCES `member`(`id`)
);
```

### apis
맴버
- `GET /apis/members`: 전체 맴버 조회
- `GET /apis/members/{id}`: 단일 맴버 조회
- `GET /apis/members/{id}/membership`: 가입한 맴버쉽 조회
- `POST /apis/members`: 맴버 생성

맴버쉽
- `GET /apis/membership`: 전체 맴버쉽 조회
- `GET /apis/membership/members`: 전체 맴버쉽 별 가입 맴버
- `POST /apis/membership`: 맴버쉽 생성
- `POST /apis/membership/sign-up`: 맴버쉽 가입