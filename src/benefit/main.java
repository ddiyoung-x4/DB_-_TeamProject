package benefit;

import java.sql.*;
import java.util.*;

public class main {

	public static void main(String[] args) throws SQLException {
		try {
			String url = "jdbc:postgresql://localhost:5432/";
			String user = "postgres";
			String passwrd = "yong0127";
			String[] facility = new String[5];
			Connection conn = DriverManager.getConnection(url, user, passwrd);
			ResultSet rs= null;
			Statement stmt =null;
			String sql;
			int cID, query_num;
			float c_locX, c_locY; //���� ���̵�, ���� x,y ��ǥ
			Scanner scan = new Scanner(System.in);
			System.out.printf("Do you have your ID ? ");
			if(scan.nextLine().equals("Y")) {
				//���̵� ����
				System.out.printf("ID : ");
				cID = scan.nextInt();
				scan.nextLine();
				
			}
			else {
				//���̵� ����
				while(true) {
					System.out.printf("ID you are going to use : ");
					cID = scan.nextInt();
					scan.nextLine();
					//cID�� client table�� ���� �ؼ� ������ �ٽ� �Է�
					if(false)
						System.out.println("Duplicate ID");
					else break;//������ break
				}
				System.out.printf("your loc X : ");
				c_locX = scan.nextFloat();
				scan.nextLine();
				System.out.printf("your loc y : ");
				c_locY = scan.nextFloat();
				scan.nextLine();
				//client table�� cID, c_locX, c_locY�� insert;
				sql = String.format("insert into Client values(%d, %f, %f);", cID, c_locX, c_locY);
				//stmt.execute(sql);
			}
			System.out.println("1 : ������ ���� �˾ƺ���\r\n"
					+ "2 : ��Ÿ �Ⱓ���� ���� �˾ƺ���\r\n"
					+ "3 : �Ҽ� �δ뺰 ����� ���� �˾ƺ���\r\n"
					+ "4 : �� ��ó ���� �˾ƺ���");
			System.out.printf("query number ? ");
			query_num = scan.nextInt();
			if(query_num == 1) {
				
			}
			else if(query_num == 2) {
				
			}
			else if(query_num == 3) {
				System.out.printf("�δ���� �Է����ּ��� : ");
				String division = scan.nextLine();
				sql = String.format("select facility, typeOfSales, startDate, finishDate, digit, url, saleDetail\n"
						+ "from Benefit natural join Benefit_Provider\n"
						+ "where location in (select dLocation from Division where dName = '%s');", division);
				rs = stmt.executeQuery(sql);
				//��¹�
			}
			else if(query_num == 4) {
				sql = String.format("select facility, (Client.logX-Benefit_provider.logx)^2+(Client.logY-Benefit_provider.logY)^2 as dist\n"
						+ "from Client, Benefit_provider\n"
						+ "Where Client.cID = %d order by dist;", cID);
				rs = stmt.executeQuery(sql);
				for(int i = 0; i < 5; i++) {
					rs.next();
					facility[i] = rs.getString("sName");
				}
				for(int i = 0; i < 5; i++) {
					sql = String.format("select facility, typeOfSales, startDate, finishDate, digit, url, saleDetail\n"
							+ "from Benefit natural join Benefit_Provider\n"
							+ "where facility = '%s'", facility[i]);
					rs = stmt.executeQuery(sql);
					while(rs.next()) {
						String facility_name = rs.getString(1);
						String ToS = rs.getString(2);
						String startDate = rs.getString(3);
						String finishDate = rs.getString(4);
						String digit = rs.getString(5);
						String page_url = rs.getString(6);
						String saleDetail = rs.getString(7);
						System.out.println();
					}
				}
			}
		}catch (SQLException ex){
			throw ex;
		}
		
	}
}
