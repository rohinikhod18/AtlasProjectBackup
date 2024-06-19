
package com.currenciesdirect.gtg.compliance.customcheck.authport;


//@Provider
public class RequestInterceptor {/*implements javax.ws.rs.container.ContainerRequestFilter {
	// private static final String AUTHORIZATION_PROPERTY = "Authorization";

	private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401,
			new Headers<Object>());

	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403,
			new Headers<Object>());

	@Override
	public void filter(ContainerRequestContext requestContext) {
		
		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext
				.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
		Method method = methodInvoker.getMethod();

		String line = "";
		String result = "";

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(requestContext.getEntityStream()));

			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		final MultivaluedMap<String, String> headers = requestContext.getHeaders();

		final List<String> userName = headers.get("username");

		final List<String> passWord = headers.get("password");

		final List<String> userId = headers.get("correlationID");

		AuthRequest authRequest = new AuthRequest();
		authRequest.setUserName(userName.get(0));
		authRequest.setPassWord(passWord.get(0));
		requestContext.setProperty("correlation_ID", userId.get(0));

		if (!method.isAnnotationPresent(PermitAll.class)) {
			if (method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(ACCESS_FORBIDDEN);
				return;
			}
		}

		if (method.isAnnotationPresent(RolesAllowed.class)) {
			RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
			Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

			if (!isUserAllowed(userName.get(0), passWord.get(0), rolesSet, authRequest)) {
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}
		}
    
		AuditLog auditLogMain = new AuditLog();
		String source = "source";
		String response = "Response";

		auditLogMain.insertQuery(Integer.parseInt(userId.get(0)), source, result, response);

	}

	private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet,
			AuthRequest authRequest) {
		boolean isAllowed = false;
		User user = null;
		user = RestRequestHandler.getInstance().sendRequest(
				getProperty("validation.url"), //"http://172.31.4.4:8080/es-restfulinterface/rest/validationadapter/authenticateUser"
				"POST", authRequest, User.class);

		// Step 1. Fetch password from database and match with password in
		// argument
		// If both match then get the defined role for user from database and
		// continue; else return isAllowed [false]
		// Access the database and do this part yourself
		String userRole = user.getRole();

		// Step 2. Verify user role
		if (rolesSet.contains(userRole)) {
			isAllowed = true;
		}
		return isAllowed;
	}
	
	private String getProperty(String propertyName) {
		try {
			return System.getProperty(propertyName).trim();
		} catch (Exception e) {
			System.out.println("	Error For [key:" + propertyName + "]	Message:"
					+ e.getMessage());
			return null;
		}
	} */
}
