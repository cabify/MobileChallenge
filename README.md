# Cabify Mobile Challenge

Besides providing exceptional transportation services, Cabify also runs a physical store which sells Products.

Our list of products looks like this:

``` 
Code         | Name                |  Price
-------------------------------------------------
VOUCHER      | Cabify Voucher      |   5.00€
TSHIRT       | Cabify T-Shirt      |  20.00€
MUG          | Cabify Coffee Mug   |   7.50€
```

Various departments have insisted on the following discounts:

 * The marketing department believes in 2-for-1 promotions (buy two of the same product, get one free), and would like to have a 2-for-1 special on `VOUCHER` items.

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
- Implement an app where a user can pick products from a list and checkout them to get the resulting price. No need to implement any real payment system, but we do need a nice user experience where our customers can understand what items are they purchasing, the price and the discount we are applying.
- You should fetch the list of products from [here](https://gist.githubusercontent.com/palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json).

# Considerations
- Be aware that the discounts are going to change frequently. So the code should support that.
- There is no need for a user login screen.
- Be written as production-ready code. We would like you to build it in the same way as if you were going to publish to the store.
- Consider explaning the solution and why certain things are included and others are left out.
- Be written either in Swift or Kotlin, preferably in the latest stable version of the language.
- If possible, use the latest stable version (no Betas) of Xcode or Android Studio.



# Technologies Uses:
  - Kotlin 
  - Jetpack Compose
  - Material Theme 3
  - Koin
  - Retrofit
  - Moshi 

# Patterns
  - MVVM + Clean architecture 
  - Repository (Used for Product)
  - Builder Pattern (Used for AlertDialog)
  - Dependency Injection (Koin)

# Extra info
  - In config files can be setted the URL BASE of the API. Variable name "BASE_URL"
  - In Product.kt file the method amountUnit() contains the price and the discount pattern of each product
