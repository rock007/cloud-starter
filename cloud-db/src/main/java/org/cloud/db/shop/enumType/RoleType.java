package org.cloud.db.shop.enumType;

public enum RoleType {

	empty(0, "未设置"),
	normal(1, "普通员工"), 
	salesman(2, "门店销售"), 
	salesmanager(3, "门店店长"), 
	telephonist(4, "人工座席"),
	installers(5, "装维部门"), 
	admin(6, "管理员"),
	forum(7,"论坛管理员");
	
	private int _value;
	private String _name;

	private RoleType(int value, String name) {
		_value = value;
		_name = name;
	}

	public int value() {
		return _value;
	}

	public String getName() {
		return _name;
	}
}
