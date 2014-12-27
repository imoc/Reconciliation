package com.compass.bean;



/**
 * @classDescription CodeBeen
 * @date 2014年7月3日 上午11:59:46
 * @author 李小伟
 */
public class CodeBean implements Comparable<CodeBean>{
	/* {"MESSAGE":"信息保存操作成功!","RESULT":true} */
	private String code;

	public CodeBean() {
		super();
		this.code = "";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "CodeBeen [code=" + code + "]";
	}

	
	@Override
	public int hashCode() {
		int code = 0;
		code = (31 * (this.code.hashCode())) >> 2;
		return code;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (o instanceof CodeBean) {
			CodeBean item = (CodeBean) o;
			if (item.code.equals(this.code))
				return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(CodeBean another) {
		// TODO Auto-generated method stub
		return (int) (another.code.compareToIgnoreCase(this.code));
	}
}
