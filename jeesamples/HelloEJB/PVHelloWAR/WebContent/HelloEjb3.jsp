<%@ page import="ejb3.bean.*"%>

<%@ page import="javax.naming.InitialContext"%>

<%@ page import="javax.naming.Context"%>

<html>
<head>
<title>Hello EJB 3 !</title>
</head>
<body>
	<%!HelloEjbRemote helloEjb;%>
	<%
		try {
			Context context = new InitialContext();
			helloEjb = (HelloEjbRemote) context.lookup("HelloEjb#ejb3.bean.HelloEjbRemote");
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
	<p>
		Object HelloEjb Directly :
		<%=helloEjb%><br> Print Hello :
		<!--  %=helloEjb.printHello("Ujang")% -->
	<p>
		Object HelloEjb from Servlet :
		<%=request.getAttribute("helloEjb")%><br> Print Hello :
		<%=request.getAttribute("printHello")%>
</body>
</html>