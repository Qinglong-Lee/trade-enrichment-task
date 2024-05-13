### How to run the service.
#### Python Script for big csv generation
- src/main/resources/genProductCsv.py
- src/test/resources/genTradeCsv.py
- install Python first
- install Pandas berfore run the script
  - pip install pandas
- apply the loop count of yourself in script
- cmd
  - python genProductCsv.py
#### IDE
- Run as application on `TradeEnrichmentServiceApplication.java`.
#### jar
- jar trade-enrichment-service-0.0.1-SNAPSHOT.jar
### How to use the API.
#### postman
1. Import src/test/resources/tradeEnrich.postman_collection.json into Postman
2. Upload src/test/resources/millionsTrades.csv to request body file field
3. You can save response by the button [save response to file] on the right of the response panel
#### CURL
```
curl --location 'localhost:8080/api/v1/enrich' \
  --header 'Content-Type: text/csv' \
  --header 'Accept: text/csv' \
  --form 'file=@"/src/test/resources/millionsTrades.csv"'
``` 
### Performace Test Result
- Condition:
  - one million trade data with size of 20MB
  - more than 5000 product data with size of 115KB
- performance: response within 1.5s
### Any limitations of the code.
- The solution is just an initial version, the scalability and performance can be improved with more efforts.
### Any discussion/comment on the design.
- Divide and Process: divide the big data into batches, each batch run in a Thread. Enhance the performance a lot.
- Thread Pool: Config a global thread pool for multiple threads switching to save resources.
- Java NIO instead of OpenCSV: OpenCSV may be more convenient, but more expensive either. Java NIO would save the valuable memory.
- String Opetion instead of Object Construction: Convert trade data to Object may be more convenient, but more expensive either. String Opetion for each line would save the valuable memory.
### Any ideas for improvement if there were more time available.
- Validations and logs could be more specific.
- More Unit tests should cover.
- MD5 or Hash signature could be used for Tamper resistance.
- capacity of HashMap for product.csv could be computed in advance for better performance.
- Stress test for different scenarios could be applied for more appropriate configuration of Thread pool.
### More to discuss
- The solution is just for standalone, for more complicated scenario such as distributed deployment or high concurrency, there should be more detail to take into consideration.
- http is not an appropriate way to transfer large sets of file data. Instead, MQ could be more efficient.
- Sync response would block the client when the server is overloaded. Instead, the server can return a processing status first and then save the processed data to local, as well as manage a status. Then user can check the status through specific API, and get the processed data through another API.
- Scalability would always be an issue for a robust system. Configuration Center like Nacos could be applied and Builder Design Pattern could also be applied to the product.csv loader and process logic of trade.csv.
- Apply to a distributed Job Scheduler such as Quartz is a way for Scalability improvement.
- Distances between regions would also be an issue in distributed scenario. Latency could be more apparent when large set of data apply. CDN could be applied for this case.