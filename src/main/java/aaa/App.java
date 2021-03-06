package aaa;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            List<String> sqls = new ArrayList<String>();
            sqls.add("SELECT * FROM DUAL");
            sqls.add("SELECT * FROM TEST_TABLE T WHERE T.ID IN ('1', '2')");
            sqls.add("SELECT * FROM dogs INNER JOIN owners ON dogs.owner_id = owners.id;");
            sqls.add("SELECT * FROM USERS U WHERE EXISTS (SELECT '1' FROM DELETED_USERS D WHERE U.ID = D.ID)");
            sqls.add("UPDATE USERS SET COMMENT = 'TEST'");
            /** https://docs.microsoft.com/ja-jp/sql/t-sql/queries/select-examples-transact-sql?view=sql-server-ver15 */
            sqls.add("SELECT * FROM Production.Product ORDER BY Name ASC;");
            sqls.add("SELECT Name, ProductNumber, ListPrice AS Price FROM Production.Product ");
            sqls.add("SELECT 'Total income is', ((OrderQty * UnitPrice) * (1.0 - UnitPriceDiscount)), ' for ', p.Name AS ProductName FROM Production.Product AS p INNER JOIN Sales.SalesOrderDetail AS sod ON p.ProductID = sod.ProductID ORDER BY ProductName ASC;"); 
            sqls.add("DELETE FROM Bicycles");
            sqls.add("SELECT * INTO Bicycles FROM AdventureWorks2012.Production.Product WHERE ProductNumber LIKE 'BK%';");
            sqls.add("SELECT DISTINCT Name FROM Production.Product AS p WHERE EXISTS (SELECT * FROM Production.ProductModel AS pm WHERE p.ProductModelID = pm.ProductModelID AND pm.Name LIKE 'Long-Sleeve Logo Jersey%')");
            for(String sql : sqls) {
                Statement stmt = CCJSqlParserUtil.parse(sql);
                TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
                List<String> tableList = tablesNamesFinder.getTableList(stmt);
                List<String> tables = new ArrayList<String>();
                for(String table: tableList) {
                    tables.add(table);
                }
                System.out.println(sql + ": " + tables.stream().collect(Collectors.joining(", ")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
