**Purchase Service**

This service is related to purchase it uses the purchase_service database which contains two tables

Purchase table: This table would store information about the purchases made by customers, which would be used to calculate and update the loyalty points.

Loyalty Points table: This would store information about the customer's loyalty points and details information about the purchase that he earned during the purchase. Which is used to calculate top customers who earned the highest number of loyalty points.

This API is used to get the top 3 leaderboard customers who earned the highest loyalty points and get each customer loyalty point by month.
Swagger URL: GetLeaderboardData

http://localhost:8081/swagger-ui/index.html#/leaderboard-controller/getLeaderboardData

We need to pass two values customerId and yearAndMonth it will fetch customer loyalty points by month

Sample request:
{
"customerId": 1,
"yearAndMonth": "202304"
}

If we want to fetch the top 3 customers we need to pass yearAndMonth it will get the top 3 highest earned loyalty points customers with details.

Sample request:
{
"yearAndMonth": "202304"
}

This API is used to purchase from a partner store.

Swagger URL: Purchase
http://localhost:8081/swagger-ui/index.html#/purchase-controller/purchase
