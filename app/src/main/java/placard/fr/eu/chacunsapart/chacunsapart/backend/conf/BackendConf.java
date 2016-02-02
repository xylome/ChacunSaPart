package placard.fr.eu.chacunsapart.chacunsapart.backend.conf;

public interface BackendConf {
	public static final String QUERY_PARAMS = "params";
	public static final String QUERY_ACTION = "action";
	public static final String QUERY_COOKIE = "auth_cookie";
    public static final String QUERY_ABSOLUTE_PARTS = "absolute_parts";
	
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_MYGROUPS = "my_groups";
	public static final String ACTION_GET_EXPENSES = "get_expenses";
	public static final String ACTION_GET_BALANCES = "get_balances";
	public static final String ACTION_CREATE_GROUP = "create_group";
	public static final String ACTION_GET_MY_FRIENDS = "my_friends";
    public static final String ACTION_UPDATE_PARTICIPATION = "update_participation";
	public static final String ACTION_DELETE_PARTICIPATION = "delete_participation";
	public static final String ACTION_CREATE_EXPENSE = "create_expense";

	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_ACCOUNT_ID = "account_id";
	public static final String FIELD_GROUP_ID = "group_id";
    public static final String FIELD_GROUP_NAME = "group_name";
    public static final String FIELD_CREATOR_ACTOR_ID = "creator_actor_id";
    public static final String FIELD_GROUP_FRACTION = "group_fraction";
	public static final String FIELD_EXPENSE_ID = "expense_id";
    public static final String FIELD_PARTS = "parts";
	public static final String FIELD_PARTS_NB = "nb_parts";
    public static final String FIELD_GUEST_ACTOR_ID = "guest_actor_id";
	public static final String FIELD_PARTICIPATION_ID = "participation_id";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_AMOUNT = "amount";
	public static final String FIELD_PAYER_ACTOR_ID = "payer_actor_id";
	public static final String FIELD_GUESTS = "guests";
    public static final String FIELD_NEW_GUEST_NICK = "new_guest_nick";


	public static final String CACHE_MYGROUPS = "cache_mygroups";
    public static final String CACHE_EXPENSES = "cache_expenses";
    public static final String CACHE_BALANCES = "cache_balances";
	public static final String CACHE_FRIENDS = "cache_friends";
}
