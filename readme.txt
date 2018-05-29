To generate a transaction report, please run ReportGenerator. By default, it reads input.txt from src/resources and then save output.csv
to c:\report\output.csv and log file can also be found under c:\report.

User can change the report style in report_config.xml which is under src/resources.
For instance, if user want to have Product_Information but is composed of CURRENCY CODE and 
MOVEMENT CODE. User just needs to simply change report_config.xml without code change.

To simplified this application, it just uses 4 libraries which can be found under lib folder:
1.junit.jar
2.log4j-api.2.11.0.jar
3.log4j-core.2.11.0.jar
4.org.hamcrest.core_1.3.0.v201303031735.jar

Some assumptions:
1. When Value sing like QUANTITY LONG sign is greater than 1, it means that it's negative value. 
If it's zero or empty, which means it's positive value
2. The fixed width is 176 for a transaction, we can change it 
3. Total_Transaction_Amount is 0 if QUANTITY LONG  is invalid integer e.g: 12345a.