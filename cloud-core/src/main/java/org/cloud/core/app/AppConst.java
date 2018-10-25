package org.cloud.core.app;

/**
 * Created by sam on 2017/7/12.
 */
public class AppConst {

	public final static String 	PRJ_PRE="paas_";
    // 会话key
    public final static String SHIRO_SESSION_ID = PRJ_PRE+"shiro-session-id";
    // 全局会话key
    public final static String SERVER_SESSION_ID = PRJ_PRE+"server-session-id";
    // 全局会话列表key
    public final static String SERVER_SESSION_IDS = PRJ_PRE+"server-session-ids";
    // code key
    public final static String SERVER_CODE = PRJ_PRE+"server-code";
    // 局部会话key
    public final static String CLIENT_SESSION_ID = PRJ_PRE+"client-session-id";
    // 单点同一个code所有局部会话key
    public final static String CLIENT_SESSION_IDS = PRJ_PRE+"client-session-ids";

    public final static String SSO_TYPE="sso_type";
    
	public static final String CON_SESSION_VALID_CODE=PRJ_PRE+"SESSION_VALID_CODE";
	
	public static final String CON_SESSION_USER_ROLE=PRJ_PRE+"SESSION_USER_ROLE";
	
	public static final String CON_SESSION_USER_NAME=PRJ_PRE+"SESSION_USER_NAME";

	public static final String CON_SESSION_SYSTEM=PRJ_PRE+"SESSION_SYSTEM";
}
