package cn.com.mysnake.daoImpl;

import cn.com.mysnake.dao.BaseDao;
import cn.com.mysnake.dao.DaoHepler;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDaoImpl<T> implements BaseDao<T> {

    public static final String SQL_INSERT = "insert";
    public static final String SQL_UPDATE = "update";
    public static final String SQL_DELETE = "delete";
    public static final String SQL_SELECT = "select";

    private Class<T> EntityClass;

    private PreparedStatement statement;

    private String sql;


    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    private Object argType[];

    private ResultSet rs;

    DaoHepler JdbcDaoHelper = new DaoHepler();

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        EntityClass = (Class<T>) type.getActualTypeArguments()[0];
        // TODO Auto-generated constructor stub

    }



    @Override
    public int add(T t) {

        sql = this.getSql(SQL_INSERT);

        try {
            argType = setArgs(t, SQL_INSERT);
            statement = JdbcDaoHelper.getPreparedStatement(sql);
            statement = JdbcDaoHelper.setPreparedStatementParam(statement, argType);
            System.out.println("add");
           return statement.executeUpdate();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean delete(T t) throws SQLException {

        sql = this.getSql(SQL_DELETE);
        try {
            argType = this.setArgs(t, SQL_DELETE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        statement = JdbcDaoHelper.getPreparedStatement(sql);
        statement = JdbcDaoHelper.setPreparedStatementParam(statement, argType);
        statement.executeUpdate();

        return false;
    }

    @Override
    public boolean update(T t) {

        sql = this.getSql(SQL_UPDATE);
        int blen=0;
        try {
            argType = setArgs(t, SQL_UPDATE);
            System.out.println(argType.toString());
            statement = JdbcDaoHelper.getPreparedStatement(sql);
            statement = JdbcDaoHelper.setPreparedStatementParam(statement, argType);
            blen = statement.executeUpdate();
            System.out.println(blen);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return blen>0;
    }

    @Override
    public T select(T t) {

        sql = this.getSql(SQL_SELECT);
        T obj = null;
        try {
            argType = setArgs(t, SQL_SELECT);
            statement = JdbcDaoHelper.getPreparedStatement(sql);
            statement = JdbcDaoHelper.setPreparedStatementParam(statement, argType);
            rs = statement.executeQuery();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        java.lang.reflect.Field[] fields = EntityClass.getDeclaredFields();
        try {
            while (rs.next()) {

                obj = EntityClass.newInstance();

                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);

                    fields[i].set(obj, rs.getObject(fields[i].getName()));
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return obj;
    }


    private String getSql(String operator) {

        StringBuffer sql = new StringBuffer();
        // 通过反射获取实体类中的所有变量
        java.lang.reflect.Field[] fields = EntityClass.getDeclaredFields();

        // 插入操作
        if (operator.equals(SQL_INSERT)) {
            sql.append("insert into " + EntityClass.getSimpleName());
            sql.append("(");
            for (int i = 0; fields != null && i < fields.length; i++) {
                fields[i].setAccessible(true); // 这句话必须要有,否则会抛出异常.
                String column = fields[i].getName();
                sql.append(column).append(",");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(") values (");
            for (int i = 0; fields != null && i < fields.length; i++) {
                sql.append("?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            // 是否需要添加分号
            sql.append(")");
        } else if (operator.equals(SQL_UPDATE)) {
            sql.append("update " + EntityClass.getSimpleName() + " set ");
            for (int i = 0; fields != null && i < fields.length; i++) {
                fields[i].setAccessible(true);
                String column = fields[i].getName();
                if (column.equals("id")) {
                    continue;
                }
                sql.append(column).append("=").append("?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" where id=?");
        } else if (operator.equals(SQL_DELETE)) {
            sql.append("delete from " + EntityClass.getSimpleName() + " where id=?");
        } else if (operator.equals(SQL_SELECT)) {
            sql.append("select * from " + EntityClass.getSimpleName() + " where id=?");
        }
        return sql.toString();
    }

    private Object[] setArgs(T entity, String operator) throws IllegalArgumentException, IllegalAccessException {

        java.lang.reflect.Field[] fields = EntityClass.getDeclaredFields();
        if (operator.equals(SQL_INSERT)) {

            Object obj[] = new Object[fields.length];
            for (int i = 0; obj != null && i < fields.length; i++) {
                fields[i].setAccessible(true);
                obj[i] = fields[i].get(entity);
            }
            return obj;

        } else if (operator.equals(SQL_UPDATE)) {

            Object Tempobj[] = new Object[fields.length];
            for (int i = 0; Tempobj != null && i < fields.length; i++) {
                fields[i].setAccessible(true);
                Tempobj[i] = fields[i].get(entity);
            }

            Object obj[] = new Object[fields.length];
            System.arraycopy(Tempobj, 1, obj, 0, Tempobj.length - 1);
            obj[obj.length - 1] = Tempobj[0];
            return obj;

        } else if (operator.equals(SQL_DELETE)) {

            Object obj[] = new Object[1];
            fields[0].setAccessible(true);
            obj[0] = fields[0].get(entity);
            return obj;
        } else if (operator.equals(SQL_SELECT)) {

            Object obj[] = new Object[1];
            fields[0].setAccessible(true);
            obj[0] = fields[0].get(entity);
            return obj;
        }
        return null;
    }
}
