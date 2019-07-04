package cn.com.mysnake.dao;

import java.sql.SQLException;

public interface BaseDao<T> {
    int add(T t);

    boolean delete(T t) throws SQLException;
    boolean update(T t);
    T select(T t);
}
