//package ddd;
//
//import org.hibernate.tool.hbm2ddl.SchemaExport;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.util.Assert;
//
//public class SchemaExporterComponent {
//	
//	@Autowired
//	LocalSessionFactoryBean lsfb;
//	
//	String sqlFileName;
//	
//	public SchemaExporterComponent(String sqlFileName)
//	{
//		Assert.hasText(sqlFileName, "you must define sqlFileName. ie. my-schema.sql");
//		this.sqlFileName = sqlFileName;
//	}
//	
//	public void execute()
//	{
//		SchemaExport schemaExport = new SchemaExport(lsfb.getConfiguration());
//		schemaExport.setOutputFile(sqlFileName);
//		schemaExport.setDelimiter(";");
//		schemaExport.execute(true, false, false, true);
//	}
//}