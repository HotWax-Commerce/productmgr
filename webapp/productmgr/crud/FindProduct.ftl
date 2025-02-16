<html>
    <body>
        <form method="get" action="<@ofbizUrl>findProductById</@ofbizUrl>" name="findProductMethod" class="form-horizontal">
            <label for="productId">ProductId</label>
            <input type="text" id="productId" name="productId">

            <label for="productName">ProductName</label>
            <input type="text" id="productName" name="productName" >

            <label for="price">Price</label>
            <input type="text" id="price" name="price" >

            <label for="productFeatureId">ProductFeatureId</label>
            <input type="text" id="productFeatureId" name="productFeatureId">

            <button type="submit" class="btn">Submit</button>
        </form>
    </body>
</html>