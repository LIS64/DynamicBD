package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import main.DBConfigXML.Database;
import main.DBConfigXML.ParsingDB;



/**
 * Servlet implementation class processingDB
 */
@WebServlet("/processingDB")
public class ServletDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	//Настройка БД в ручную
	//private final static String urlDataBase = "jdbc:oracle:thin:@localhost:1521:XE";
	/*private static Connection getConnection() throws SQLException, ClassNotFoundException{
		Connection conn = null;
        Locale.setDefault(Locale.ENGLISH);
    	Properties connectionParams = new Properties();
    	connectionParams.put("user", "system");
		connectionParams.put("password", "040792");
	    Class.forName("oracle.jdbc.driver.OracleDriver");
		conn=DriverManager.getConnection(urlDataBase, connectionParams);
		return conn;
	}*/
	
	//Настройка БД из XML файла
	private static Connection getConnection() throws SAXException, IOException, SQLException, ParserConfigurationException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Database database;
		Connection conn=null;
		ParsingDB DB = new ParsingDB();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse("configBD.xml", DB);	
		database = DB.getDatabase();
		if (database!=null){
	         Locale.setDefault(Locale.ENGLISH);
		     Class.forName(database.getJDBCDriver());
		     conn = DriverManager.getConnection(database.getUrlDatabase(), database.getLoginDatabase(), database.getPasswordDatabase());
		} 
		return conn;
	}

	private static ArrayList<String> getColumns(ResultSet res) throws SQLException{
		ArrayList<String> result = new ArrayList<>();
		ResultSetMetaData meta;
		meta = res.getMetaData();
		for (int i=1; i<=meta.getColumnCount(); i++){
		 result.add(meta.getColumnLabel(i));
		}
		return result;
	}
	
	private static ArrayList<String> getRows(ResultSet res, ArrayList<String> columns) throws SQLException{
		ArrayList<String> result = new ArrayList<>();
		String row = "";
		while (res.next()){
			row="";
			for (int i=0; i<columns.size(); i++){
				row+=res.getString(columns.get(i))+"'";
			}
			result.add(row);
		}
		return result;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			Connection conn = null;
			Statement stat = null;
			ResultSet res = null;
			ArrayList<String> rows = new ArrayList<>();
			ArrayList<String> columns = new ArrayList<>();
			String nameTable = request.getParameter("tableName");
			  try {
				conn = getConnection();
				stat = conn.createStatement();
				res = stat.executeQuery("select * from "+nameTable);			
				columns = getColumns(res);				
				rows = getRows(res, columns);
				request.setAttribute("columns", columns);
				request.setAttribute("rows", rows);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
				
			} catch (SQLException | ClassNotFoundException | SAXException | ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				request.setAttribute("error", e1.getMessage());
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			} finally {
				if (conn!=null){
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}

}
