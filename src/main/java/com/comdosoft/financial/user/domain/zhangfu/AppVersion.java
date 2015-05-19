package com.comdosoft.financial.user.domain.zhangfu;

public class AppVersion {
	private Integer id;
	/**
	 * 1 ios用户手机，
	 * 2 ios代理商手机，
	 * 3 Android 用户手机，
	 * 4 android代理商手机，
	 * 5 用户ipad，
	 * 6 代理商ipad，
	 * 7 Android 用户pad,
	 *  8 Android 代理商pad
	 */
	private Integer types;
	private String versions;
	private String down_url;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
 
 
	/**
	 * @return the versions
	 */
	public String getVersions() {
		return versions;
	}
	/**
	 * @param versions the versions to set
	 */
	public void setVersions(String versions) {
		this.versions = versions;
	}
	/**
	 * @return the down_url
	 */
	public String getDown_url() {
		return down_url;
	}
	/**
	 * @param down_url the down_url to set
	 */
	public void setDown_url(String down_url) {
		this.down_url = down_url;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppVersion [id=" + id + ", types=" + types + ", versions="
				+ versions + ", down_url=" + down_url + "]";
	}
	 
	public AppVersion(Integer id, Integer types, String versions, String down_url) {
		super();
		this.id = id;
		this.types = types;
		this.versions = versions;
		this.down_url = down_url;
	}
	/**
	 * @return the types
	 */
	public Integer getTypes() {
		return types;
	}
	/**
	 * @param types the types to set
	 */
	public void setTypes(Integer types) {
		this.types = types;
	}
	public AppVersion() {
		super();
	}
	
}
