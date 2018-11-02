package statics;

public class SESSION {

	private static long user_cod;
	private static String user_name;
	private static String user_last_name;
	private static String user_email;

	
	public static void init_session(long cod,String name,String last_name,String email) { 
		SESSION.user_cod= cod;
		SESSION.user_name = name;
		SESSION.user_last_name=last_name;
		SESSION.user_email = email;
	}
	public static void init_session(long cod,String name,String email) { 
		SESSION.user_cod= cod;
		SESSION.user_name = name;
		SESSION.user_email = email;
	}
	
	public static long getUser_cod() {
		return user_cod;
	}
	public static String getUser_name() {
		return user_name;
	}
	public static String getUser_last_name() {
		return user_last_name;
	}
	public static String getUser_email() {
		return user_email;
	}
}
