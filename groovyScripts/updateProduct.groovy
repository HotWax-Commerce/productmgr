import java.sql.Timestamp
import org.apache.ofbiz.entity.util.*;
import org.apache.ofbiz.base.util.UtilDateTime
import org.apache.ofbiz.entity.GenericValue
import java.sql.Timestamp
import org.apache.ofbiz.entity.GenericValue
import org.apache.ofbiz.entity.Delegator


def updateProduct() {
    Timestamp currentTimeStamp = UtilDateTime.nowTimestamp()
    Delegator delegator = (Delegator) context.delegator
    logInfo("---Update Service Called---")
    GenericValue productDetails = delegator.findOne("Product", ["productId": context.productId], false)
    if (!productDetails) {
        logInfo("Product not found for ID: ${context.productId}")
        return error("Product not found")
    }
    logInfo("---Existing Product Details---${productDetails}----")
    List<GenericValue> existingPriceList = from("ProductPrice").where("productId", context.productId).queryList()
    GenericValue productPrice = EntityUtil.getFirst(EntityUtil.filterByDate(existingPriceList))
    logInfo("---Existing Valid Product Price Details---${productPrice}----")

    if (context.productName) {
        logInfo("---ProdunctName changed from ${productDetails.productName} to ${context.productName}----")
        productDetails.set("productName", context.productName)
        delegator.store(productDetails)
    }

    if (productPrice) {
        productPrice.set("thruDate", currentTimeStamp)
        delegator.store(productPrice)
    }
    Map result = success()

    if (context.price) {
        GenericValue newProductPrice = delegator.makeValue("ProductPrice", [
                "productId"             : context.productId,
                "productPriceTypeId"    : "DEFAULT_PRICE",
                "productPricePurposeId" : "PURCHASE",
                "currencyUomId"         : "USD",
                "productStoreGroupId"   : "_NA_",
                "price"                 : new BigDecimal(context.price ?: 0),
                "fromDate"              : currentTimeStamp
        ])
        result.price = newProductPrice.price
        logInfo("---New Product Price Record Created ${productPrice}----")
        delegator.create(newProductPrice)
    }
    result.productId = productDetails.productId
}

