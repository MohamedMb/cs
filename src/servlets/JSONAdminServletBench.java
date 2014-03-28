package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import benchmark.*;

public class JSONAdminServletBench extends HttpServlet {

	private static final long serialVersionUID = 6935251785798019829L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			doProcess(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			doProcess(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doProcess(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String json = null;
		int nbReq = Integer.parseInt(req.getParameter("nbReq"));
		boolean useThread = new Boolean(req.getParameter("useThread"))
				.booleanValue();
		String typeReq = req.getParameter("typeReq");
		String type = req.getParameter("typeBench");
		
		if (type.equals("pool")) {
			PoolConnexionTest poolConnexion = new PoolConnexionTest(useThread,
					nbReq, typeReq);
			poolConnexion.startBenchmark();

			System.out.println(poolConnexion);
			json = poolConnexion.toString();
		} else if (type.equals("simple")) {
			MySQLConnexionTest connexionSimple = new MySQLConnexionTest(
					useThread, nbReq, typeReq);
			connexionSimple.startBenchmark();
			System.out.println(connexionSimple);
			json = connexionSimple.toString();
		}
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(json);
	}
}
