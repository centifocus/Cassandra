package wangliqiu.reflect;

import wangliqiu.reflect.RenderMethod.MethodType;
import wangliqiu.reflect.RenderParameter.ParameterType;
import wangliqiu.reflect.RenderParameter.ScopeType;

public class MyJob {

	@RenderMethod(methodType = MethodType.INQUIRE, parameters = {
			@RenderParameter(name = "logined", type = ParameterType.BOOL, scope = ScopeType.SESSION),
			@RenderParameter(name = "loginedUser", scope = ScopeType.SESSION) })
	public void inquire(boolean logined, String loginedUser) {
		if (logined) {
			System.out.println(loginedUser + " is logined.");
		} else {
			System.out.println(loginedUser + " is not logined.");
		}
	}

}
