package com.hld.library.frame.db;

import java.util.List;

import android.content.Context;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.db.sqlite.DbModel;

/**
 * 数据库操作，执行数据库增删改查，出口类：DBmanager<br/>
 * DbUtils db = DbUtils.create(this,dbName);<br/>
 * User user = new User(); //这里需要注意的是User对象必须有id属性，或者有通过@ID注解的属性<br/>
 * user.setEmail("mail@tsz.net");<br/>
 * user.setName("michael yang");<br/>
 * db.save(user);<br/>
 * 
 * 支持懒汉模式:<br/>
 * 在定义的时候加上@OneToMany<br/>
 * public class Parent{<br/>
 *     private int id;<br/>
 *     @OneToMany(manyColumn = "parentId")<br/>
 *     private OneToManyLazyLoader<Parent ,Child> children;<br/>
 * }<br/>
 * public class Child{<br/>
 *     private int id;<br/>
 *     private String text;<br/>
 *     @ManyToOne(column = "parentId")<br/>
 *     private  Parent  parent;<br/>
 * }<br/>
 * 
 * 
 * @author hld
 */
public class DbUtils{
	private FinalDb db;
	private static DbUtils du;
	private DbUtils() {}
	protected static DbUtils create(Context context, String dbName) {
		if(du==null){
			du=new DbUtils();
		}
		if(du.db==null){
			du.db=FinalDb.create(context, dbName);
		}
		return du;
	}
	public void save(Object entity) {
		db.save(entity);
	}
	public boolean saveBindId(Object entity) {
		return db.saveBindId(entity);
	}
	public void update(Object entity) {
		db.update(entity);
	}
	public void update(Object entity, String strWhere) {
		db.update(entity, strWhere);
	}
	public void delete(Object entity) {
		db.delete(entity);
	}
	public void deleteById(Class<?> clazz, Object id) {
		db.deleteById(clazz, id);
	}
	public void deleteByWhere(Class<?> clazz, String strWhere) {
		db.deleteByWhere(clazz, strWhere);
	}
	public void deleteAll(Class<?> clazz) {
		db.deleteAll(clazz);
	}
	public void dropTable(Class<?> clazz) {
		db.dropTable(clazz);
	}
	public void dropDb() {
		db.dropDb();
	}
	public <T> T findById(Object id, Class<T> clazz) {
		return db.findById(id, clazz);
	}
	public <T> T findWithManyToOneById(Object id, Class<T> clazz) {
		return db.findWithManyToOneById(id, clazz);
	}
	public <T> T findWithManyToOneById(Object id, Class<T> clazz,
			Class<?>... findClass) {
		return db.findWithManyToOneById(id, clazz, findClass);
	}
	public <T> T loadManyToOne(DbModel dbModel, T entity, Class<T> clazz,
			Class<?>... findClass) {
		return db.loadManyToOne(dbModel, entity, clazz, findClass);
	}
	public <T> T findWithOneToManyById(Object id, Class<T> clazz) {
		return db.findWithOneToManyById(id, clazz);
	}
	public <T> T findWithOneToManyById(Object id, Class<T> clazz,
			Class<?>... findClass) {
		return db.findWithOneToManyById(id, clazz, findClass);
	}
	public <T> T loadOneToMany(T entity, Class<T> clazz, Class<?>... findClass) {
		return db.loadOneToMany(entity, clazz, findClass);
	}
	public <T> List<T> findAll(Class<T> clazz) {
		return db.findAll(clazz);
	}
	public <T> List<T> findAll(Class<T> clazz, String orderBy) {
		return db.findAll(clazz, orderBy);
	}
	public <T> List<T> findAllByWhere(Class<T> clazz, String strWhere) {
		return db.findAllByWhere(clazz, strWhere);
	}
	public <T> List<T> findAllByWhere(Class<T> clazz, String strWhere,
			String orderBy) {
		return db.findAllByWhere(clazz, strWhere, orderBy);
	}
	public DbModel findDbModelBySQL(String strSQL) {
		return db.findDbModelBySQL(strSQL);
	}
	public List<DbModel> findDbModelListBySQL(String strSQL) {
		return db.findDbModelListBySQL(strSQL);
	}
}