package org.cloud.db.shop.enumType;

/**
 * 订单状态
 * @author 下午2:00:52 by sam
 *
 */
public enum OrderStatusType {

	cancel(-1, "过期取消"),
	nopay(0, "未支付"), 
	pay(1, "支付完成"),
	packonline(2, "已发货"), 
	packarrive(3, "签收"),
	reqback(4, "退货/退款"), 
	backdone(5, "退货完成"),
	backcancel(6,"退货取消"),
	confirmdeal(7,"确认收货"),
	dealdone(8,"交易完成");
	
	private int _value;
	private String _name;

	private OrderStatusType(int value, String name) {
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
