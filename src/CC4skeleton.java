
public class CC4skeleton {
	public static void main(String[] args) {
		QueryExecutor qe = new QueryExecutor();

		System.out.println("////////////////////////////////");
		System.out.println("//////////// Question 7.9E  ////");
		System.out.println("////////////////////////////////");
		String qu7dot9E = ""
				+ "SELECT\n"
				+ "    s.SUPNAME,\n"
				+ "    s.SUPNR\n"
				+ "FROM\n"
				+ "    supplier s\n"
				+ "WHERE NOT\n"
				+ "    EXISTS(\n"
				+ "    SELECT\n"
				+ "        *\n"
				+ "    FROM\n"
				+ "        purchase_order p\n"
				+ "    WHERE\n"
				+ "        s.SUPNR = p.SUPNR\n"
				+ ")\n"
				+ "";
		qe.executeQuery(qu7dot9E);

		
		
		System.out.println("////////////////////////////////");
		System.out.println("//////////// Question 7.11E ////");
		System.out.println("////////////////////////////////");
        String qu7dot11E = ""
				+ "SELECT\n"
				+ "    SUM(available_quantity) AS TOTAL_QUANTITY\n"
				+ "FROM\n"
				+ "    product\n"
				+ "WHERE\n"
				+ "    prodtype = 'sparkling'";
        qe.executeQuery(qu7dot11E);
		

		
		
		System.out.println("////////////////////////////////");
		System.out.println("//////////// Question 7.12E ////");
		System.out.println("////////////////////////////////");
        String qu7dot12E = ""
				+ "SELECT\n"
                + "    s.supnr,"
                + "    s.supname,"
				+ "    COUNT(po.ponr) AS total_num_of_outstanding_orders\n"
				+ "FROM\n"
				+ "    supplier s\n"
				+ "LEFT JOIN\n"
                + "    purchase_order po\n"
                + "ON\n"
                + "    s.supnr = po.supnr\n"
                + "GROUP BY\n"
				+ "    s.supnr, s.supname\n"
                + "ORDER BY\n"
                + "    s.supnr";
        qe.executeQuery(qu7dot12E);		

		
		
		
		System.out.println("////////////////////////////////");
		System.out.println("//////////// Question 7.13E ////");
		System.out.println("////////////////////////////////");
        String qu7dot13E= ""
				+ "SELECT\n"
				+ "    s.supnr,\n"
                + "    COUNT(s.prodnr) AS num_of_product\n"
				+ "FROM\n"
				+ "    supplies s\n"
                + "GROUP BY\n"
				+ "    s.supnr\n"
				+ "HAVING\n"
				+ "    COUNT(s.prodnr) > 5\n";                
        qe.executeQuery(qu7dot13E);				
		
		
		
		
		System.out.println("////////////////////////////////");
		System.out.println("//////////// Question 7.14E ////");
		System.out.println("////////////////////////////////");
        String qu7dot14E= ""
				+ "SELECT\n"
                + "    s.supnr,\n"
				+ "    s.supname,\n"
                + "    AVG(sp.deliv_period) AS average_delivery_time\n"
				+ "FROM\n"
				+ "    supplier s\n"
                + "JOIN\n"
				+ "    supplies sp\n" 
                + "ON\n" 
                + "     s.supnr = sp.supnr\n"
                + "GROUP BY\n"
				+ "    s.supnr, s.supname\n"
                + "ORDER BY\n"
				+ "    s.supnr";            
        qe.executeQuery(qu7dot14E);				

		
		
		
		
		System.out.println("////////////////////////////////");
		System.out.println("//////////// Question 7.15E ////");
		System.out.println("////////////////////////////////");
	        String qu7dot15E= ""
				+ "SELECT DISTINCT\n"
                + "    ponr\n"
				+ "FROM\n"
				+ "    po_line\n"
                + "WHERE\n"
				+ "    prodnr IN (\n"
				+ "        SELECT p.prodnr\n"        
				+ "        FROM product p\n"
				+ "        WHERE prodtype = 'sparkling' OR prodtype = 'red'\n" 
				+ ")";             
        qe.executeQuery(qu7dot15E);		
		
		
		
		System.out.println("////////////////////////////////");
		System.out.println("//////////// Question 7.16E ////");
		System.out.println("////////////////////////////////");
        String qu7dot16E= ""
				+ "SELECT\n"
                + "    p1.prodnr\n"
				+ "FROM\n"
				+ "    product p1\n"
                + "WHERE\n"
				+ "    3 >= (\n"
				+ "        SELECT COUNT(p2.prodnr)\n"        
				+ "        FROM product p2\n"
				+ "        WHERE p2.prodnr <= p1.prodnr\n" 
				+ "  )\n"
                + "ORDER BY\n"
                + "    p1.prodnr ASC";
        qe.executeQuery(qu7dot16E);
		
		
		
		System.out.println("////////////////////////////////");
		System.out.println("//////////// Question 7.17E ////");
		System.out.println("////////////////////////////////");
        String qu7dot17E= ""
				+ "SELECT\n"
                + "    prodname\n"
				+ "FROM\n"
				+ "    product\n"
                + "WHERE\n"
				+ "    available_quantity >= ALL (\n"
				+ "        SELECT available_quantity\n"        
				+ "        FROM product\n"
                + "        WHERE available_quantity IS NOT NULL"
				+ ")";
        qe.executeQuery(qu7dot17E);		
		
		
		System.out.println("////////////////////////////////");
		System.out.println("//////////// Question 7.18E ////");
		System.out.println("////////////////////////////////");
        String qu7dot18E= ""
				+ "SELECT\n"
                + "    s1.supname,\n"
                + "    s1.supnr\n"
				+ "FROM\n"
				+ "    supplier s1\n"
                + "WHERE NOT EXISTS(\n"
				+ "     SELECT *\n"        
				+ "     FROM supplier s2\n"
                + "     WHERE s2.supnr < s1.supnr"
				+ ")";
        qe.executeQuery(qu7dot18E);				

		
		
		
		

		
		
		
		
		
		
		
	}

}