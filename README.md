# Cabify Mobile Challenge

Besides providing exceptional transportation services, Cabify also runs a physical store which sells Products. During the year, we apply discounts to them to make it more attractive to clients.

Our products are like this:

``` 
Code         | Name                |  Price
-------------------------------------------------
VOUCHER      | Cabify Voucher      |   5.00€
TSHIRT       | Cabify T-Shirt      |  20.00€
MUG          | Cabify Coffee Mug   |   7.50€
```

Various departments have insisted on the following discounts:

 * The marketing department believes in 2-for-1 promotions (buy two of the same product, get one free), and would like for there to be a 2-for-1 special on `VOUCHER` items.

 * The CFO insists that the best way to increase sales is with discounts on bulk purchases (buying x or more of a product, the price of that product is reduced), and demands that if you buy 3 or more `TSHIRT` items, the price per unit should be 19.00€.

Cabify's checkout process allows for items to be scanned in any order, and should return the total amount to be paid.

Examples:

    Items: VOUCHER, TSHIRT, MUG
    Total: 32.50€

    Items: VOUCHER, TSHIRT, VOUCHER
    Total: 25.00€

    Items: TSHIRT, TSHIRT, TSHIRT, VOUCHER, TSHIRT
    Total: 81.00€

    Items: VOUCHER, TSHIRT, VOUCHER, VOUCHER, MUG, TSHIRT, TSHIRT
    Total: 74.50€


# To do
Implement an app where a user can pick products from a list and checkout them to get the resulting price, and see what discounts are applied. You should get the list of products from [here](https://api.myjson.com/bins/4bwec). In terms of
functionality, we want you to build an app we could put in the store tomorrow.

In Cabify we use Swift 4 to build our iOS app and Kotlin to build our Android app, but we want you to feel comfortable during the exercise, so feel free to pick the best platform for you: Swift/Objective-C or Kotlin/Java

**The code should:**
- Be written as production-ready code. You will write production code. 
- Be easy to grow and easy to add new functionality.
- Have notes attached, explaning the solution and why certain things are included and others are left out.


# Done:
- Code written in RxViper because it is an implementation of a clean architecture. I could use MVVM but I thing that Viper is a good solution to apply SOLID principles. I am not very familiar with other new arquitectures like redux or flux, but I am studying them right now.

- As you can see there is no comments in code because I think like Uncle Bob Martin: "A comment is a failure to express yourself in code.  If you fail, then write a comment; but try not to fail."

- There are some unit tests to test the specifications of the project.

- Developers are not usually good designers as you know, therefore I haven't waste a lot of time in the design of the app and I have focus my work on the code.

- If you have some questions, please, feel free to reach me out.
