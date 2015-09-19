package org.eu.fr.placard.chacunsapartsdk.conf;

public interface BackendConf {
	public static final String QUERY_PARAMS = "params";
	public static final String QUERY_ACTION = "action";
	public static final String QUERY_COOKIE = "auth_cookie";
	
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_MYGROUPS = "my_groups";
	public static final String ACTION_GET_EXPENSES = "get_expenses";

	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_ACCOUNT_ID = "account_id";
	public static final String FIELD_GROUP_ID = "group_id";
}
