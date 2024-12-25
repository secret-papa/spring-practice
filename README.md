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
```