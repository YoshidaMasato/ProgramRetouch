package ec;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;
import dao.BuyDetailDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {

			//リクエストスコープからbuy_idを入手
			int buy_id = Integer.parseInt(request.getParameter("buy_id"));

			//buy_idを元に購入情報を取得
			BuyDataBeans bdb = BuyDAO.getBuyDataBeansByBuyId(buy_id);

			//buy_idを元に購入詳細情報を取得
			ArrayList<ItemDataBeans> idbList = BuyDetailDAO.getItemDataBeansListByBuyId(buy_id);

			request.setAttribute("bdb", bdb);
			request.setAttribute("idbList", idbList);
			request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);

		}catch(SQLException e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}


	}
}
