package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Expense;

public class ExpenseDAO {

	public static final String SELECT_ALL_QUERY="select EX.APPLICATION_ID,EX.APPLICATION_DATE,EX.UPDATE_DATE,EX.APPLICANT,EX.TITLE,EX.AMOUNT,EX.STATUS"
	                       +" from EXPENSE EX";
	private static final String SELECT_BY_ID_QUERY = SELECT_ALL_QUERY + " WHERE EX.APPLICATION_ID = ?";



	public List<Expense> findAll() {
		List<Expense> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (Statement statement = connection.createStatement();) {
			ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);

			while (rs.next()) {
				result.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}


	private Expense processRow(ResultSet rs) throws SQLException {
		Expense result = new Expense();

		// Expense本体の再現
		result.setId(rs.getString("APPLICATION_ID"));
		//result.setAppDate(rs.getString("APPLICATION_DATE"));
		//result.setUpdateDate(rs.getString("UPDATE_DATE"));
		result.setApplicant(rs.getString("APPLICANT"));
		result.setTitle(rs.getString("TITLE"));
		result.setAmount(rs.getInt("AMOUNT"));
		result.setStatus(rs.getString("STATUS"));

		Date appDate = rs.getDate("APPLICATION_DATE");
		if (appDate != null) {
			result.setAppDate(appDate.toString());
		}
		Date updateDate = rs.getDate("UPDATE_DATE");
		if (updateDate != null) {
			result.setUpdateDate(updateDate.toString());
		}

		// 入れ子のオブジェクトの再現
		//Post post = new Post();
		//post.setId(rs.getInt("POSTID"));
		//post.setName(rs.getString("POST_NAME"));
		//result.setPost(post);

		return result;
	}


}
