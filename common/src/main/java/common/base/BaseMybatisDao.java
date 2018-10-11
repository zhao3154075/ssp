package common.base;

import cn.org.rapid_framework.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import javax.management.Query;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author badqiu
 * @version 1.0
 */
public abstract class BaseMybatisDao<E,PK extends Serializable> extends SqlSessionDaoSupport implements EntityDao<E,PK> {
    protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){

		super.setSqlSessionFactory(sqlSessionFactory);
	}


	public E getById(PK primaryKey) {
		return (E)getSqlSession().selectOne(getFindByPrimaryKeyStatement(), primaryKey);
    }
    
	public void deleteById(PK id) {
		int affectCount = getSqlSession().delete(getDeleteStatement(), id);
	}
	
    public void save(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		int affectCount = getSqlSession().insert(getInsertStatement(), entity);    	
    }
    
	public void update(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		int affectCount = getSqlSession().update(getUpdateStatement(), entity);
	}
	
	/**
	 * 用于子类覆盖,在insert,update之前调用
	 * @param o
	 */
    protected void prepareObjectForSaveOrUpdate(E o) {
    }

    public String getMybatisMapperNamesapce() {
        throw new RuntimeException("not yet implement");
    }
    
    public String getFindByPrimaryKeyStatement() {
        return getMybatisMapperNamesapce()+".getById";
    }

    public String getInsertStatement() {
        return getMybatisMapperNamesapce()+".insert";
    }

    public String getUpdateStatement() {
    	return getMybatisMapperNamesapce()+".update";
    }

    public String getDeleteStatement() {
    	return getMybatisMapperNamesapce()+".delete";
    }

	public String getFindAllStatement() {
		return getMybatisMapperNamesapce()+".findAll";
	}

	public String getCountStatement(){return getMybatisMapperNamesapce()+".findPage.count";}

	public List findAll() {
		return getSqlSession().selectList(getFindAllStatement());
	}

	public boolean isUnique(E entity, String uniquePropertyNames) {
		throw new UnsupportedOperationException();
	}
	
	public void flush() {
		//ignore
	}

	public <T> T getByPropertys(String names,Object...values){
		return getSqlSession().selectOne(getFindAllStatement(),attachQueryVariable(names,values));
	}

	public List<E> findByPropertys(String sqlId,String names,Object...values){
		return getSqlSession().selectList(sqlId,attachQueryVariable(names,values));
	}

	public <E>List<E> selectList(String sqlId,String names,Object...values){
		return getSqlSession().selectList(sqlId,attachQueryVariable(names,values));
	}

	public <E>List<E> selectList(String sqlId, Query query){
		return getSqlSession().selectList(sqlId,attachQueryVariable(query));
	}

	public List<E> findAllByPropertys(String names,Object...values){
		return getSqlSession().selectList(getFindAllStatement(),attachQueryVariable(names,values));
	}

	public Long count(String names,Object...values){
		return getSqlSession().selectOne(getCountStatement(),attachQueryVariable(names, values));
	}

	public <T>T selectOne(String sqlId,String names,Object...values){
		return getSqlSession().selectOne(sqlId,attachQueryVariable(names, values));
	}

	private Map attachQueryVariable(String names,Object...values){
		Map map=new HashMap();
		String queryNames[]=names.split(",");
		for(int i=0;i<queryNames.length;i++){
			map.put(queryNames[i],values[i]);
		}
		return map;
	}

	private Map attachQueryVariable(Object parameter){
		if(parameter instanceof Map) {
			return (Map)parameter;
		}else {
			try {
				return PropertyUtils.describe(parameter);
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
				return null;
			}
		}
	}
	
}
