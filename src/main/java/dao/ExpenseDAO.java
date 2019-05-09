package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Expense;

public class ExpenseDAO {

	public static final String SELECT_ALL_QUERY="select EX.APPLICATION_ID \n" +
			", EX.APPLICATION_DATE \n" +
			", EX.UPDATE_DATE \n" +
			", EX.APPLICANT \n" +
			", EX.TITLE \n" +
			", EX.AMOUNT \n" +
			", EX.STATUS \n" +
			", EX.CHANGER \n" +
			", EX.PAYEE \n" +
			"from  \n" +
			"EXPENSE EX \n"
//			"ORDER BY EX.APPLICATION_ID \n"
			;
	private static final String SELECT_BY_ID_QUERY = SELECT_ALL_QUERY + " WHERE EX.APPLICATION_ID = ?";
	private static final String DELETE_QUERY = "DELETE FROM EXPENSE WHERE APPLICATION_ID = ?";
	private static final String INSERT_QUERY = "INSERT INTO "
			+"EXPENSE(APPLICATION_ID,APPLICATION_DATE,UPDATE_DATE,APPLICANT,TITLE,PAYEE,AMOUNT,STATUS,CHANGER) "
			+"VALUES(?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_QUERY = "UPDATE EXPENSE "
			+"SET APPLICATION_ID=?,APPLICATION_DATE=?,UPDATE_DATE=?,APPLICANT=?,TITLE=?,PAYEE=?,AMOUNT=?,"
			+"STATUS=?,CHANGER=? WHERE APPLICATION_ID = ?";



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

	public boolean create_or_update (String id){
		List<Expense> result = findAll();
		for(  Expense exp : result ){
			if(exp.getId().equals(id)){
				return true;
			}


		}
		return false;
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
		result.setPayee(rs.getString("PAYEE"));
		result.setChanger(rs.getString("CHANGER"));

		Date appDate = rs.getDate("APPLICATION_DATE");
		if (appDate != null) {
			result.setAppDate(appDate.toString());
		}
		Date updateDate = rs.getDate("UPDATE_DATE");
		if (updateDate != null) {
			result.setUpdateDate(updateDate.toString());
		}



		return result;
	}

	public Expense findById(int id) {
		Expense result = null;

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				result = processRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

	public boolean remove(int id) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return false;
		}

		int count = 0;
		try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
			// DELETE実行
			statement.setInt(1, id);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}
		return count == 1;
	}

	private void setParameter(PreparedStatement statement, Expense expense, boolean forUpdate) throws SQLException {
		int count = 1;

		statement.setString(count++, expense.getId());
		statement.setDate(count++,  Date.valueOf( expense.getAppDate()) );
		statement.setDate(count++, Date.valueOf(expense.getUpdateDate()) );
		statement.setString(count++, expense.getApplicant());
		statement.setString(count++, expense.getTitle());
		statement.setString(count++, expense.getPayee());
		statement.setInt(count++, expense.getAmount());
		statement.setString(count++, expense.getStatus());
		statement.setString(count++, expense.getChanger());

		if (forUpdate) {
			statement.setString(count++, expense.getId());
		}

	}


	public Expense create(Expense expense) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return expense;
		}

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY/**, new String[] { "ID" }**/);) {
			// INSERT実行
			setParameter(statement, expense, false);
			statement.executeUpdate();

			// INSERTできたらKEYを取得
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			String id = rs.getString(1);
			expense.setId(id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return expense;
	}



	public Expense update(Expense expense) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return expense;
		}

		try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
			setParameter(statement, expense, true);
			statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return expense;
	}



}
