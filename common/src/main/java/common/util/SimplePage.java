package common.util;

/**
 * 简单分页类
 */
public class SimplePage implements Paginable {
	private static final long serialVersionUID = 1L;
	public static final int DEF_COUNT = 20;

	/**
	 * 检查页码 checkPageNo
	 * 
	 * @param pageNo
	 * @return if pageNo==null or pageNo<1 then return 1 else return pageNo
	 */
	public static int cpn(Integer pageNo) {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}

	public SimplePage() {
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
	public SimplePage(int pageNo, int pageSize, int totalCount, String url) {
		setTotalCount(totalCount);
		setTotalCountStr(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		adjustPageNo();
		setShowPage();
		setUrl(url);
	}

	/**
	 * 调整页码，使不超过最大页数
	 */
	public void adjustPageNo() {
		if (pageNo == 1) {
			return;
		}
		int tp = getTotalPage();
		if (pageNo > tp) {
			pageNo = tp;
		}
	}

	/**
	 * 获得页码
	 */
	@Override
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 每页几条数据
	 */
	@Override
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 总共几条数据
	 */
	@Override
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 总共几页
	 */
	@Override
	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		return totalPage;
	}

	/**
	 * 是否第一页
	 */
	@Override
	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	/**
	 * 是否最后一页
	 */
	@Override
	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	/**
	 * 下一页页码
	 */
	@Override
	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	/**
	 * 上一页页码
	 */
	@Override
	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	protected int totalCount = 0;
	protected int pageSize = 10;
	protected int pageNo = 1;
	protected int showPage = 10;
	protected int startPage = 1;
	protected int endPage = 10;
	protected String url;
	protected String totalCountStr;
	
	/**
	 * if totalCount<0 then totalCount=0
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		if (totalCount < 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
	}

	/**
	 * if pageSize< 1 then pageSize=DEF_COUNT
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			this.pageSize = DEF_COUNT;
		} else {
			this.pageSize = pageSize;
		}
	}

	/**
	 * if pageNo < 1 then pageNo=1
	 * 
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}
	
	public int getShowPage(){
		return showPage;
	}
	
	public void setShowPage(){
		int totalPage = getTotalPage();
		
		if(totalPage < 11){
			this.startPage = 1;
			this.endPage = totalPage;
		}else{
			if(this.pageNo <= 6){
				this.startPage = 1;
				this.endPage = 10;
				if(this.endPage >= totalPage){
					this.endPage = totalPage;
				}
			}else{
				this.startPage = this.pageNo - 5;
				this.endPage = this.pageNo + 4;	
				if(this.endPage >= totalPage){
					this.endPage = totalPage;
					if(totalPage < 10) {
						this.startPage = 1;
					}/*else{
						this.startPage = this.endPage - 9;
					}*/
				}
			}
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}

	public String getTotalCountStr() {
		return totalCountStr;
	}

	public void setTotalCountStr(int totalCount) {
		this.totalCountStr = CommonUtils.getResultStr(totalCount+"");
	}
	
	
	
}