<h1>List of Products will be displayed here</h1>
<#if productList?? && productList?has_content>
<table class="table table-bordered">
    <thead>
        <tr>
            <th>ProductId</th>
            <th>ProductName</th>
            <th>ProductTypeID</th>
            <th>ProductFeatureId</th>
            <th>ProductCategoryId</th>
            <th>Price</th>
        </tr>
    </thead>
    <tbody>
        <#list productList as product>
            <tr>
                <td>${product.productId}</td>
                <td>${product.productName}</td>
                <td>${product.productTypeId}</td>
                <td>${product.productFeatureId}</td>
                <td>${product.productCategoryId}</td>
                <td>${product.price}</td>
            </tr>
        </#list>
    </tbody>
</table>
<#else>
    <p>No products found.</p>
</#if>
