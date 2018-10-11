package common.util;

import java.util.List;

/**
 * 列表分页。包含list属性。
 */
@SuppressWarnings("serial")
public class Page extends SimplePage implements java.io.Serializable,
        Paginable {

    public Page() {
    }

    /**
     * 构造器
     *
     * @param pageNo
     *            页码
     * @param pageSize
     *            每页几条数据
     * @param totalCount
     *            总共几条数据
     */
    public Page(int pageNo, int pageSize, int totalCount, String url) {
        super(pageNo, pageSize, totalCount, url);
    }

    /**
     * 构造器
     *
     * @param pageNo
     *            页码
     * @param pageSize
     *            每页几条数据
     * @param totalCount
     *            总共几条数据
     * @param list
     *            分页内容
     */
    public Page(int pageNo, int pageSize, int totalCount, List<?> list, String url) {
        super(pageNo, pageSize, totalCount, url);
        this.list = list;
    }

    /**
     * 第一条数据位置
     *
     * @return
     */
    public int getFirstResult() {
        return (pageNo - 1) * pageSize;
    }

    /**
     * 当前页的数据
     */
    private List<?> list;

    /**
     * 获得分页内容
     *
     * @return
     */
    public List<?> getList() {
        return list;
    }

    /**
     * 设置分页内容
     *
     * @param list
     */
    public void setList(List list) {
        this.list = list;
    }
}


