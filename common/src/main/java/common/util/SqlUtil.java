package common.util;

/**
 * Created by Administrator on 2018/1/19.
 */
public class SqlUtil {
    private static final String regex="^([A-Za-z][\\w]*[ ]*(ASC|asc|desc|DESC)?)*$";
    /**
     * 判断是否是合法的order by参数
     * @param value
     * @return
     */
    public static boolean isOrderByIllegal(String value){
        if(value!=null){
            if(value.trim().equals("")){
                return true;
            }
            String values[]=value.split(",");
            for(String v:values){
                if(!v.trim().matches(regex)){
                    return false;
                }
            }
            return true;
        }
        return true;
    }
}
