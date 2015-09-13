package org.daniel.jsp.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.daniel.jsp.beans.LiveSourceBean;
import org.daniel.jsp.db.DatabaseHelper;

public class LiveSourceManager {
	private static LiveSourceManager INSTANCE = new LiveSourceManager();

	private LiveSourceManager() {
	}

	public static LiveSourceManager getInstance() {
		return INSTANCE;
	}

	public void add(List<LiveSourceBean> list) throws SQLException {
		Connection conn = DatabaseHelper.getInstance().openConnection();
		Statement statement = conn.createStatement();
		for (LiveSourceBean bean : list) {
			try {
				statement
						.execute("insert into VideoQuality (id, url,lasttime) values("
								+ bean.getUrl().hashCode()
								+ ", '"
								+ bean.getUrl() + "', " + bean.getTime() + ")");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		statement.close();
		conn.close();
	}

	public void update(LiveSourceBean bean) throws SQLException {
		Connection conn = DatabaseHelper.getInstance().openConnection();
		Statement stt = conn.createStatement();
		stt.execute("update VideoQuality set quality=" + bean.getQuality()
				+ ", lasttime=" + bean.getTime() + " where id=" + bean.getId());

		stt.close();
		conn.close();
	}

	public LiveSourceBean get() throws SQLException {
		LiveSourceBean bean = null;

		Connection conn = DatabaseHelper.getInstance().openConnection();
		Statement stt = conn.createStatement();
		ResultSet result = stt
				.executeQuery("select * from VideoQuality order by lasttime limit 0,1");
		if (result.next()) {
			bean = getBeanFromSource(result);
		} else {
			bean = new LiveSourceBean();
		}

		result.close();
		stt.close();
		conn.close();

		return bean;
	}

	private LiveSourceBean getBeanFromSource(ResultSet result)
			throws SQLException {
		LiveSourceBean bean = new LiveSourceBean();
		bean.setId(result.getLong("id"));
		bean.setUrl(result.getString("url"));
		bean.setTime(result.getLong("lasttime"));
		bean.setQuality(result.getInt("quality"));
		return bean;
	}

	public int getCount() throws SQLException {
		Connection conn = DatabaseHelper.getInstance().openConnection();
		Statement stt = conn.createStatement();
		ResultSet result = stt
				.executeQuery("select count(*) count from VideoQuality");
		int count = 0;
		if (result.next()) {
			count = result.getInt(1);
		}

		result.close();
		stt.close();
		conn.close();

		return count;
	}

	public List<LiveSourceBean> getAll() throws SQLException {
		List<LiveSourceBean> list = new ArrayList<LiveSourceBean>();
		Connection conn = DatabaseHelper.getInstance().openConnection();
		Statement stt = conn.createStatement();
		ResultSet result = stt
				.executeQuery("select * from VideoQuality order by lasttime");
		while (result.next()) {
			list.add(getBeanFromSource(result));
		}

		result.close();
		stt.close();
		conn.close();

		return list;
	}
}
