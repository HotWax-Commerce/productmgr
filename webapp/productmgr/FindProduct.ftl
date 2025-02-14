<html>
    <body>
        <form method="get" action="<@ofbizUrl>findProductById</@ofbizUrl>" name="findProductMethod" class="form-horizontal">
            <label for="productId">ProductId</label>
            <input type="text" id="productId" name="productId" required>

            <label for="productName">ProductName</label>
            <input type="text" id="productName" name="productName" required>

            <label for="price">Price</label>
            <input type="text" id="price" name="price" required>

            <label for="productFeatureId">ProductFeatureId</label>
            <input type="text" id="productFeatureId" name="productFeatureId" required>

            <button type="submit" class="btn">Submit</button>
        </form>
    </body>
</html>